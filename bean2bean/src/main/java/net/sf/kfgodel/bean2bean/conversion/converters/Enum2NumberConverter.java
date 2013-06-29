/**
 * Created on 29/12/2007 12:20:09 Copyright (C) 2007 Dario L. Garcia
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
import java.lang.reflect.Type;

import net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;


import com.opensymphony.xwork2.conversion.impl.XWorkBasicConverter;

/**
 * Esta clase sabe como convertir de enum a integer
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 */
@SuppressWarnings("unchecked")
public class Enum2NumberConverter implements SpecializedTypeConverter<Enum, Number> {

	private XWorkBasicConverter basicConverter;

	/**
	 * @param expectedType
	 *            El tipo esperado
	 * @param sourceObject
	 *            El enum a convertir
	 * @param contextAnnotations
	 *            No usado
	 * @return La representacion numerica del enum
	 * @throws CannotConvertException
	 *             Si no se puedo convertir
	 * @see net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter#convertTo(java.lang.reflect.Type,
	 *      java.lang.Object, java.lang.annotation.Annotation[])
	 */
	public Number convertTo(Type expectedType, Enum sourceObject, Annotation[] contextAnnotations)
			throws CannotConvertException {
		if (expectedType == null) {
			throw new CannotConvertException("Cannot make conversion. Expected type was not defined", sourceObject,
					expectedType);
		}
		int ordinal = sourceObject.ordinal();
		Class<?> expectedClass = ReflectionUtils.degenerify(expectedType);
		if (expectedClass == null) {
			throw new CannotConvertException("Expected type can not be treated as a number[" + expectedType + "]",
					sourceObject, expectedType);
		}
		Number converted = (Number) getBasicConverter().convertValue(ordinal, expectedClass);
		return converted;
	}

	public static Enum2NumberConverter create() {
		Enum2NumberConverter converter = new Enum2NumberConverter();
		return converter;
	}

	private XWorkBasicConverter getBasicConverter() {
		if (basicConverter == null) {
			basicConverter = new XWorkBasicConverter();
		}
		return basicConverter;
	}

}
