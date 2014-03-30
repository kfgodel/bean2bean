/**
 * Created on 29/12/2007 21:33:10 Copyright (C) 2007 Dario L. Garcia
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
import java.lang.reflect.Array;
import java.lang.reflect.Type;

import net.sf.kfgodel.bean2bean.conversion.DefaultTypeConverter;
import net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter;
import net.sf.kfgodel.bean2bean.conversion.TypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;


/**
 * Esta clase sabe como convertir de array a array de objetos, realizando la conversion de los
 * elementos si es necesaria.<br>
 * Esta clase es necesaria antes del conversor de Xwrok para poder convertir los elementos de un
 * array utilizando el conversor {@link DefaultTypeConverter}
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 */
public class ArrayArrayConverter implements GeneralTypeConverter<Object, Object> {

	/**
	 * Conversor al que se le delegaran las conversiones basicas
	 */
	private TypeConverter baseConverter;

	public TypeConverter getBaseConverter() {
		return baseConverter;
	}

	public void setBaseConverter(TypeConverter baseConverter) {
		this.baseConverter = baseConverter;
	}

	/**
	 * @param value
	 *            Array desde el que se tomaran los objetos
	 * @param expectedType
	 *            Tipo de array esperado
	 * @return El array convertido o el mismo array pasado si no es necesaria conversion
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertFrom(java.lang.Object,
	 *      java.lang.reflect.Type, java.lang.annotation.Annotation[])
	 */
	public Object convertFrom(Object value, Type expectedType, Annotation[] contextAnnotations) {
		if (expectedType == null) {
			throw new CannotConvertException("Cannot make conversion. Expected type was not defined", value,
					expectedType);
		}
		Class<?> expectedClass = ReflectionUtils.degenerify(expectedType);
		if (expectedClass == null) {
			throw new CannotConvertException("Cannot determine class for expected type", value, expectedClass);
		}
		Class<?> elementType = expectedClass.getComponentType();
		if (elementType == null) {
			throw new CannotConvertException("Expected type is not an array[" + expectedClass + "]", value,
					expectedType);
		}
		int arrayLength = Array.getLength(value);
		Object createdArray = Array.newInstance(elementType, arrayLength);

		boolean conversionWasNeeded = false;
		for (int i = 0; i < arrayLength; i++) {
			Object sourceObject = Array.get(value, i);
			Object elementToAdd = this.getBaseConverter().convertValue(sourceObject, elementType);
			if (elementToAdd != sourceObject) {
				conversionWasNeeded = true;
			}
			Array.set(createdArray, i, elementToAdd);
		}
		if (!conversionWasNeeded) {
			return value;
		}
		return createdArray;
	}

	/**
	 * @param expectedType
	 *            Tipo del array esperado
	 * @param value
	 *            Array a convertir
	 * @return Array convertido
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertTo(java.lang.reflect.Type,
	 *      java.lang.Object, java.lang.annotation.Annotation[])
	 */
	public Object convertTo(Type expectedType, Object value, Annotation[] contextAnnotations) {
		return convertFrom(value, expectedType, contextAnnotations);
	}

	public static ArrayArrayConverter create(TypeConverter baseConverter) {
		ArrayArrayConverter converter = new ArrayArrayConverter();
		converter.setBaseConverter(baseConverter);
		return converter;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionFrom(java.lang.Class,
	 *      java.lang.reflect.Type, java.lang.Object)
	 */
	public boolean acceptsConversionFrom(Class<?> sourceType, Type expectedType, Object sourceObject) {
		if (!sourceType.isArray()) {
			return false;
		}
		Class<?> expectedClass = ReflectionUtils.degenerify(expectedType);
		if (!expectedClass.isArray()) {
			return false;
		}
		return true;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionTo(java.lang.reflect.Type,
	 *      java.lang.Class, java.lang.Object)
	 */
	public boolean acceptsConversionTo(Type expectedType, Class<?> sourceType, Object sourceObject) {
		return acceptsConversionFrom(sourceType, expectedType, sourceObject);
	}

}
