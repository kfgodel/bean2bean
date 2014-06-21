/**
 * 30/06/2013 14:17:12 Copyright (C) 2011 Darío L. García
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
 * This class represents the exception produced when the expected type cannot be defined
 * 
 * @author D. García
 */
public class TypeExtractionFailedException extends RuntimeException {
	private static final long serialVersionUID = 8940516623655786417L;

	public TypeExtractionFailedException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TypeExtractionFailedException(final String message) {
		super(message);
	}

	public TypeExtractionFailedException(final Throwable cause) {
		super(cause);
	}

}
