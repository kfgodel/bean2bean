/**
 * 27/06/2011 00:14:21 Copyright (C) 2011 Darío L. García
 * 
 * <a rel="license" href="http://creativecommons.org/licenses/by/3.0/"><img
 * alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/by/3.0/88x31.png" /></a><br />
 * <span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/Text"
 * property="dct:title" rel="dct:type">Software</span> by <span
 * xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Darío García</span> is
 * licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/3.0/">Creative
 * Commons Attribution 3.0 Unported License</a>.
 */
package ar.dgarcia.http.simple.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import ar.dgarcia.http.simple.api.ConnectionProblemException;
import ar.dgarcia.http.simple.api.HttpResponseProvider;
import ar.dgarcia.http.simple.api.StringRequest;
import ar.dgarcia.http.simple.api.StringResponse;

/**
 * Esta clase implementa el proveedor de respuestas para requests simples utilizando el cliente Http
 * de apache
 * 
 * @author D. García
 */
public class ApacheResponseProvider implements HttpResponseProvider {

	private ConnectionClient client;

	/**
	 * @see net.sf.kfgodel.buxfer_api.conn.HttpResponseProvider#sendRequest(net.ar.dgarcia.http.simple.api.StringRequest)
	 */
	@Override
	public StringResponse sendRequest(final StringRequest request) throws ConnectionProblemException {
		final HttpUriRequest apacheRequest = translateRequestFor(request);
		HttpResponse apacheResponse;
		try {
			apacheResponse = client.executeHttpCommand(apacheRequest);
		} catch (final ConnectionClientException e) {
			throw new ConnectionProblemException("Se produjo un error enviando el request", e);
		}
		return translateResponse(apacheResponse);
	}

	/**
	 * Traduce la respuesta de apache a una versión simplificada
	 * 
	 * @param apacheResponse
	 *            La respuesta de apache
	 * @return La respuesta simplificada
	 */
	private StringResponse translateResponse(final HttpResponse apacheResponse) {
		final int statusCode = apacheResponse.getStatusLine().getStatusCode();
		String responseContent = null;
		final HttpEntity entity = apacheResponse.getEntity();
		if (entity != null) {
			try {
				responseContent = IOUtils.toString(entity.getContent());
			} catch (final IllegalStateException e) {
				throw new ConnectionProblemException("Error de estado accediendo el contenido de la respuesta", e);
			} catch (final IOException e) {
				throw new ConnectionProblemException("Se produjo un error de IO accediento la respuesta", e);
			}
		}
		final StringResponse basicResponse = StringResponse.create(statusCode, responseContent);
		return basicResponse;
	}

	/**
	 * Traduce el request en formato simple a un request de apache
	 * 
	 * @param request
	 *            El request simple
	 * @return La versión completa para poder procesarlo
	 */
	private HttpUriRequest translateRequestFor(final StringRequest request) {
		HttpUriRequest apacheRequest;
		if (request.isPostRequest()) {
			final HttpPost httpPost = new HttpPost(request.getCompleteGetUrl());
			final List<? extends NameValuePair> postParameters = createPostParametersFrom(request.getPostParameters());
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(postParameters, HTTP.UTF_8));
			} catch (final UnsupportedEncodingException e) {
				throw new ConnectionProblemException("Falló seteo de encoding del request POST", e);
			}
			apacheRequest = httpPost;
		} else {
			apacheRequest = new HttpGet(request.getCompleteGetUrl());
		}
		return apacheRequest;
	}

	/**
	 * Crea una versión apache de los parámetros del post
	 * 
	 * @param postParameters
	 *            Los parámetros a agregar en el cuerpo del post
	 * @return La lista de parámetros
	 */
	private List<? extends NameValuePair> createPostParametersFrom(final Map<String, String> postParameters) {
		final ArrayList<NameValuePair> parameters = new ArrayList<NameValuePair>();
		final Set<Entry<String, String>> parameterSet = postParameters.entrySet();
		for (final Entry<String, String> entry : parameterSet) {
			final String paramName = entry.getKey();
			final String paramValue = entry.getValue();
			final BasicNameValuePair parameterPair = new BasicNameValuePair(paramName, paramValue);
			parameters.add(parameterPair);
		}
		return parameters;
	}

	public static ApacheResponseProvider create() {
		final ApacheResponseProvider provider = new ApacheResponseProvider();
		provider.client = new ConnectionClient();
		return provider;
	}
}
