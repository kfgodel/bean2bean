/**
 * 13/02/2012 21:08:33 Copyright (C) 2011 Darío L. García
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
package ar.dgarcia.encryptor.api;

/**
 * Esta excepción representa un problema en el encriptado o desencriptado de los textos
 * 
 * @author D. García
 */
public class FailedCryptException extends RuntimeException {
	private static final long serialVersionUID = 2231424133403533297L;

	public FailedCryptException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FailedCryptException(final String message) {
		super(message);
	}

	public FailedCryptException(final Throwable cause) {
		super(cause);
	}

}
