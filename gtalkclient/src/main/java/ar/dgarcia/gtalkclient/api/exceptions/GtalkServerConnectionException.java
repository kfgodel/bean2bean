/**
 * 31/12/2011 02:47:41 Copyright (C) 2011 Darío L. García
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
package ar.dgarcia.gtalkclient.api.exceptions;

import ar.dgarcia.gtalkclient.api.GtalkClientException;

/**
 * Esta clase representa una excepción en la conexión al servidor de gtalk. Lo más normal sería un
 * error en la red
 * 
 * @author D. García
 */
public class GtalkServerConnectionException extends GtalkClientException {
	private static final long serialVersionUID = -4070863383634079936L;

	public GtalkServerConnectionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public GtalkServerConnectionException(final String message) {
		super(message);
	}

	public GtalkServerConnectionException(final Throwable cause) {
		super(cause);
	}

}
