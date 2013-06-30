/**
 * Created on 31/12/2007 03:07:03 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.conversion.converters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;

/**
 * Esta clase permite convertir de una cadena a un Number.<br>
 * Aparentemente esta clase es necesaria por que jsontools v1.6, no determina el tipo numerico mas
 * cercano a la cadena que representa un numero.
 * 
 * @version 1.0
 * @since 31/12/2007
 * @author D. Garcia
 */
public class String2NumberConverter implements SpecializedTypeConverter<String, Number> {

	/**
	 * Busca el tipo de dato que mejor represente la cadena pasada en formato numerico
	 * 
	 * @param sourceObject
	 *            Cadena que representa un numero
	 * @param expectedType
	 *            Tipo numerico esperado
	 * @return El objeto que mejor represente la cadena
	 * @throws CannotConvertException
	 *             Si ningun tipo se adapta a la cadena
	 */
	private Number bestFitConversionFrom(final String sourceObject, final Type expectedType)
			throws CannotConvertException {
		try {
			final Integer integer = Integer.valueOf(sourceObject);
			if (integer.toString().equals(sourceObject)) {
				return integer;
			}
		} catch (final NumberFormatException e) {
			// No es un entero
		}
		try {
			final Long longValue = new Long(sourceObject);
			if (longValue.toString().equals(sourceObject)) {
				return longValue;
			}
		} catch (final NumberFormatException e) {
			// No es un long
		}
		try {
			final Float floatValue = new Float(sourceObject);
			if (floatValue.toString().equals(sourceObject)) {
				return floatValue;
			}
		} catch (final NumberFormatException e) {
			// No es un float
		}
		try {
			final Double doubleValue = new Double(sourceObject);
			if (doubleValue.toString().equals(sourceObject)) {
				return doubleValue;
			}
		} catch (final NumberFormatException e) {
			// No es un double
		}
		throw new CannotConvertException("An appropriate numeric type was not found for [" + sourceObject
				+ "], expected type was [" + expectedType + "]", sourceObject, expectedType);
	}

	/**
	 * @param expectedType
	 *            Tipo numerico esperado de la conversion
	 * @param sourceObject
	 *            Cadena con el valor a convertir
	 * @param contextAnnotations
	 *            No usado
	 * @return El valor convertido
	 * @throws CannotConvertException
	 *             Si se produjo un error durante la conversion o la cadena no representa un numero
	 * @see net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter#convertTo(java.lang.reflect.Type,
	 *      java.lang.Object, java.lang.annotation.Annotation[])
	 */
	public Number convertTo(final Type expectedType, final String sourceObject, final Annotation[] contextAnnotations)
			throws CannotConvertException {
		if (sourceObject.trim().length() == 0) {
			// Es la cadena vacía, se considera null
			return null;
		}
		if (expectedType == null) {
			throw new CannotConvertException("Cannot make conversion. Expected type was not defined", sourceObject,
					expectedType);
		}
		final Number converted = convertUsingConstructor(expectedType, sourceObject);
		if (converted != null) {
			return converted;
		}
		return bestFitConversionFrom(sourceObject, expectedType);
	}

	/**
	 * Intenta convertir la cadena usando el contructor de la clase que recibe la cadena como
	 * parametro
	 * 
	 * @param expectedType
	 *            Tipo esperado
	 * @param sourceObject
	 *            Cadena que representa el numero
	 * @return El numero covnertido o null si no existe el contructor
	 */
	private Number convertUsingConstructor(final Type expectedType, final String sourceObject) {
		try {
			final Class<?> expectedClass = ReflectionUtils.degenerify(expectedType);
			if (expectedClass == null) {
				return null;
			}
			@SuppressWarnings("unchecked")
			final Constructor<Number> constructor = (Constructor<Number>) expectedClass.getConstructor(String.class);
			final Number newInstance = constructor.newInstance(sourceObject);
			return newInstance;
		} catch (final SecurityException e) {
			throw new CannotConvertException("Cannot use constructor because a security constraint", sourceObject,
					expectedType, e);
		} catch (final NumberFormatException e) {
			throw new CannotConvertException(
					"Can not convert[" + sourceObject + "] to a number [" + expectedType + "]", sourceObject,
					expectedType, e);
		} catch (final IllegalArgumentException e) {
			throw new CannotConvertException("This exception should not rise", sourceObject, expectedType, e);
		} catch (final NoSuchMethodException e) {
			return null;
		} catch (final InstantiationException e) {
			return null;
		} catch (final IllegalAccessException e) {
			throw new CannotConvertException("Constructor is not public[" + expectedType + "]", sourceObject,
					expectedType, e);
		} catch (final InvocationTargetException e) {
			throw new CannotConvertException("An inner exception rised within constructor[" + expectedType + "]",
					sourceObject, expectedType, e);
		}
	}

	public static String2NumberConverter create() {
		return new String2NumberConverter();
	}

}
