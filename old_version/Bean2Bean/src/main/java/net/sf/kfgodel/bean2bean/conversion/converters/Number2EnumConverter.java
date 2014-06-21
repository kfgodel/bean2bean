/**
 * Created on 29/12/2007 12:20:18 Copyright (C) 2007 Dario L. Garcia
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


/**
 * Esta clase sabe como convertir de cualquier numero a un enum
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 */
@SuppressWarnings("unchecked")
public class Number2EnumConverter implements SpecializedTypeConverter<Number, Enum> {

	/**
	 * @param expectedType
	 *            Tipo esperado para el enum
	 * @param sourceObject
	 *            Valor numerico que representa un ordinal del enum
	 * @param contextAnnotations
	 *            No usado
	 * @return El enum correspondiente al ordinal
	 * @throws CannotConvertException
	 *             Si no se pudo realizar la conversion
	 * @see net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter#convertTo(java.lang.reflect.Type,
	 *      java.lang.Object, java.lang.annotation.Annotation[])
	 */
	public Enum convertTo(Type expectedType, Number sourceObject, Annotation[] contextAnnotations)
			throws CannotConvertException {
		if (expectedType == null) {
			throw new CannotConvertException("Cannot make conversion. Expected type was not defined", sourceObject,
					expectedType);
		}
		Class<Enum> enumClass = (Class<Enum>) ReflectionUtils.degenerify(expectedType);
		if (enumClass == null) {
			throw new CannotConvertException("Expected type is not an Enum[" + expectedType + "]", sourceObject,
					expectedType);
		}
		Enum[] enumConstants = enumClass.getEnumConstants();
		if (enumConstants == null) {
			throw new CannotConvertException("Expected type doesn't have enum constants[" + expectedType + "]",
					sourceObject, expectedType);
		}
		int ordinalValue = sourceObject.intValue();
		if ((ordinalValue < 0) || (ordinalValue >= enumConstants.length)) {
			throw new CannotConvertException("Value[" + sourceObject + "] is not a valid ordinal for the enum["
					+ expectedType + "]", sourceObject, expectedType);
		}
		Enum enumValue = enumConstants[ordinalValue];
		return enumValue;
	}

	public static Number2EnumConverter create() {
		return new Number2EnumConverter();
	}

}
