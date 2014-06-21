/**
 * 30/06/2013 15:13:31 Copyright (C) 2011 Darío L. García
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
 * This class represents an error produced by a bad or missing assumption
 * 
 * @author D. García
 */
public class FailedAssumptionException extends RuntimeException {
	private static final long serialVersionUID = -1574448473687040783L;

	public FailedAssumptionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FailedAssumptionException(final String message) {
		super(message);
	}

	public FailedAssumptionException(final Throwable cause) {
		super(cause);
	}

}
