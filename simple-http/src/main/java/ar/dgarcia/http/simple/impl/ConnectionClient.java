package ar.dgarcia.http.simple.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import ar.dgarcia.http.simple.impl.ssl.AnyDomainSSLSocketFactory;

/**
 * Esta clase representa el cliente que realiza comunicación http para sincronizar mensajes con el
 * servidor.<br>
 * Es una implementación basada en la clase {@link DefaultHttpClient} provista por Android y realiza
 * la configuración de bajo nivel relacionado al cliente.<br>
 * Este cliente permite realizar conexiones https con dominios que no se poseen un certificado
 * confiable.
 * 
 * @author D. García
 * 
 */
public class ConnectionClient {

	/**
	 * Puerto normalmente usado para HTTPS
	 */
	private static final int HTTPS_PORT = 443;

	/**
	 * Determina el tiempo en milisegundos antes de cancelar una conexión.
	 */
	private static final int CONNECTION_TIMEOUT_MS = 10 * 1000;

	/**
	 * Cliente real de las conexiones
	 */
	private final DefaultHttpClient httpClient;

	/**
	 * Constructor para una conexión estándar de tipo http
	 * 
	 * @throws ConnectionClientException
	 *             Si no se puede configurar correctamente la conexión
	 */
	public ConnectionClient() throws ConnectionClientException {
		httpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), CONNECTION_TIMEOUT_MS);
		// Permitimos cualquier certificado en HTTPS
		registerTrustAllScheme(HTTPS_PORT);
	}

	/**
	 * Ejecuta el comando indicado devolviendo su respuesta si el resultado fue OK
	 * 
	 * @param httpCommand
	 *            Comando http a ejecutar
	 * @return La respuesta positiva
	 * @throws ConnectionClientException
	 *             Si existe un error en la comunicación
	 */
	public HttpResponse executeHttpCommand(final HttpUriRequest httpCommand) throws ConnectionClientException {
		try {
			final HttpResponse response = httpClient.execute(httpCommand);
			return response;
		} catch (final UnsupportedEncodingException e) {
			throw new ConnectionClientException(e);
		} catch (final ClientProtocolException e) {
			throw new ConnectionClientException(e);
		} catch (final UnknownHostException e) {
			throw new ConnectionClientException("No se pudo determinar la IP del host pasado", e);
		} catch (final IOException e) {
			throw new ConnectionClientException(e);
		}
	}

	/**
	 * Configura el cliente para permitir conexiones no confiables.
	 */
	public void registerTrustAllScheme(final int port) throws ConnectionClientException {
		try {
			final AnyDomainSSLSocketFactory socketFactory = new AnyDomainSSLSocketFactory();
			final Scheme sch = new Scheme("https", port, socketFactory);
			httpClient.getConnectionManager().getSchemeRegistry().register(sch);
		} catch (final KeyManagementException e) {
			throw new ConnectionClientException(e);
		} catch (final NoSuchAlgorithmException e) {
			throw new ConnectionClientException(e);
		} catch (final KeyStoreException e) {
			throw new ConnectionClientException(e);
		} catch (final UnrecoverableKeyException e) {
			throw new ConnectionClientException(e);
		}

	}

}