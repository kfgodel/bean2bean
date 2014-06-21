/**
 * 30/06/2013 15:04:46 Copyright (C) 2011 Darío L. García
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
package net.sf.kfgodel.bean2bean.exceptions;

/**
 * Esta clase representa le excepción producida al fallar la instanciación
 * 
 * @author D. García
 */
public class FailedInstantiationException extends RuntimeException {
	private static final long serialVersionUID = -165590995104583895L;

	public FailedInstantiationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FailedInstantiationException(final String message) {
		super(message);
	}

	public FailedInstantiationException(final Throwable cause) {
		super(cause);
	}

}
