/**
 * 25/06/2011 01:54:48 Copyright (C) 2011 Darío L. García
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
package ar.dgarcia.http.simple.api;

import com.google.common.base.Objects;

/**
 * Esta clase representa una respuesta HTTP donde sólo importa el contenido de la misma como String.<br>
 * Adicionalmente se incluye el código de status para validaciones adicionales en la deteccion de
 * error
 * 
 * @author D. García
 */
public class StringResponse {

	private int status;
	public static final String status_FIELD = "status";
	private String content;
	public static final String content_FIELD = "content";

	public int getStatus() {
		return status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public static StringResponse create(final int status, final String responseContent) {
		final StringResponse response = new StringResponse();
		response.status = status;
		response.content = responseContent;
		return response;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add(status_FIELD, status).add(content_FIELD, content).toString();
	}
}
