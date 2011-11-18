/**
 * Created on 24/12/2007 18:30:07 Copyright (C) 2007 Dario L. Garcia
 * 
 * <a rel="license" href="http://creativecommons.org/licenses/by/2.5/ar/"><img
 * alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/by/2.5/ar/88x31.png" /></a><br />
 * <span xmlns:dc="http://purl.org/dc/elements/1.1/"
 * href="http://purl.org/dc/dcmitype/InteractiveResource" property="dc:title"
 * rel="dc:type">Bean2Bean</span> by <a xmlns:cc="http://creativecommons.org/ns#"
 * href="https://sourceforge.net/projects/bean2bean/" property="cc:attributionName"
 * rel="cc:attributionURL">Dar&#237;o Garc&#237;a</a> is licensed under a <a rel="license"
 * href="http://creativecommons.org/licenses/by/2.5/ar/">Creative Commons Attribution 2.5 Argentina
 * License</a>.<br />
 * Based on a work at <a xmlns:dc="http://purl.org/dc/elements/1.1/"
 * href="https://bean2bean.svn.sourceforge.net/svnroot/bean2bean"
 * rel="dc:source">bean2bean.svn.sourceforge.net</a>
 */
package net.sf.kfgodel.bean2bean.exceptions;

import java.lang.reflect.Type;

/**
 * Esta excepcion es producida por un intento de conversion que no pudo ser realizada por no tener
 * un conversor para hacerlo
 * 
 * @version 1.0
 * @since 24/12/2007
 * @author D. Garcia
 */
public class CannotConvertException extends RuntimeException {
	private static final long serialVersionUID = 5387253448233098465L;
	private Type expectedType;
	private Object value;
	private Object errorDescription;

	/**
	 * Crea una excepcion indicando el valor que no se pudo convertir y el tipo esperado de la
	 * conversion
	 * 
	 * @param value
	 *            Valor no convertido
	 * @param genericType
	 *            Tipo esperado
	 */
	public CannotConvertException(Object value, Type genericType) {
		this("Can not convert[" + value + "] to [" + genericType + "]", value, genericType);
	}

	/**
	 * Crea una excepcion indicando el valor no convertido y el tipo esperado, adem�s de utilizar
	 * una cadena como descripcion del tipo de error
	 * 
	 * @param message
	 *            Descripcion del error producido
	 * @param sourceObject
	 *            Objeto no convertido
	 * @param expectedType
	 *            tipo esperado
	 */
	public CannotConvertException(String message, Object sourceObject, Type expectedType) {
		super(message);
		this.value = sourceObject;
		this.expectedType = expectedType;
		this.errorDescription = message;
	}

	/**
	 * Crea una excepcion indicando un mensaje de error, y la excepcion original que impidio un paso
	 * de la conversion
	 * 
	 * @param message
	 *            Mensaje descriptivo de error
	 * @param sourceObject
	 *            Objeto no convertido
	 * @param expectedType
	 *            Tipo esperado
	 * @param e
	 *            Error que impidio la conversion
	 */
	public CannotConvertException(String message, Object sourceObject, Type expectedType, Throwable e) {
		super(message, e);
		this.errorDescription = e;
	}

	/**
	 * Crea la excepcion con un objeto adicional que permite describir el error ocurrido
	 * 
	 * @param message
	 *            Mensaje descriptivo del error
	 * @param sourceObject
	 *            Objeto no convertido
	 * @param expectedType
	 *            Tipo esperado
	 * @param errorDescription
	 *            Descripci�n compleja del error
	 */
	public CannotConvertException(String message, Object sourceObject, Type expectedType, Object errorDescription) {
		super(message);
		this.errorDescription = errorDescription;
	}

	public Type getExpectedType() {
		return expectedType;
	}

	public Object getValue() {
		return value;
	}

	public Object getErrorDescription() {
		return errorDescription;
	}

}
