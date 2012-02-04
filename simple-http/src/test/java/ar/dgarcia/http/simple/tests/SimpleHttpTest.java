/**
 * 04/02/2012 14:56:27 Copyright (C) 2011 Darío L. García
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
package ar.dgarcia.http.simple.tests;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ar.dgarcia.http.simple.api.HttpProtocol;
import ar.dgarcia.http.simple.api.HttpResponseProvider;
import ar.dgarcia.http.simple.api.StringRequest;
import ar.dgarcia.http.simple.api.StringResponse;
import ar.dgarcia.http.simple.impl.ApacheResponseProvider;

/**
 * Esta clase prueba el conector http
 * 
 * @author D. García
 */
public class SimpleHttpTest {

	private HttpResponseProvider httpProvider;

	@Before
	public void prepararConector() {
		httpProvider = ApacheResponseProvider.create();
	}

	@Test
	public void deberiaDevolver200AlConsultarGoogle() {
		final StringRequest googleRequest = StringRequest.create("http://www.google.com");
		final StringResponse googleResponse = httpProvider.sendRequest(googleRequest);
		Assert.assertEquals(HttpProtocol.HTTP_OK_STATUS, googleResponse.getStatus());
		System.out.println(googleResponse);
	}
}
