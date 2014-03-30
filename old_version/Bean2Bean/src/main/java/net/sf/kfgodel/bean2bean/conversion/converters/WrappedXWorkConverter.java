/**
 * Created on 29/12/2007 21:11:55 Copyright (C) 2007 Dario L. Garcia
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;


import com.opensymphony.xwork2.conversion.TypeConverter;
import com.opensymphony.xwork2.conversion.impl.XWorkBasicConverter;

/**
 * Esta clase utiliza el conversor interno de Xwork para convertir entre tipos basicos y Strings
 * (tipos manejados por XWork).<br>
 * Este conversor debe utilizarse como segunda opcion para todas las demas conversiones ya que no
 * realiza conversion de elementos al convertir entre arrays.<br>
 * Basicamente debe utilizarse para tipos primitivos, strings y quizas fechas, y nada mas. Esta
 * clase utiliza el {@link XWorkBasicConverter}
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 */
public class WrappedXWorkConverter implements GeneralTypeConverter<Object, Object> {
	private XWorkBasicConverter converter;

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertFrom(java.lang.Object,
	 *      java.lang.reflect.Type, java.lang.annotation.Annotation[])
	 */
	public Object convertFrom(Object value, Type expectedType, Annotation[] contextAnnotations) {
		return makeXworkConversion(value, expectedType);
	}

	/**
	 * Makes the basic conversion using Xwork basic converter
	 * 
	 * @param value
	 *            Value to convert
	 * @param expectedType
	 *            Expected type for the converted value
	 * @return The converted value
	 */
	private Object makeXworkConversion(Object value, Type expectedType) {
		if (expectedType == null) {
			throw new CannotConvertException("Cannot make conversion. Expected type was not defined", value,
					expectedType);
		}
		Class<?> expectedClass = ReflectionUtils.degenerify(expectedType);
		Object convertedValue = this.getConverter().convertValue(value, expectedClass);
		if (convertedValue == TypeConverter.NO_CONVERSION_POSSIBLE) {
			throw new CannotConvertException(value, expectedType);
		}
		if (value != null && convertedValue == null) {
			throw new CannotConvertException("Xwork couldn't convert value[" + value + "] to expectedType["
					+ expectedType + "], returned null instead", value, expectedType);
			// logger.debug("Potentially unwanted conversion from[" + value +
			// "] to null. ExpectedType was ["
			// + expectedType + "]");
		}
		return convertedValue;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertTo(java.lang.reflect.Type,
	 *      java.lang.Object, java.lang.annotation.Annotation[])
	 */
	public Object convertTo(Type expectedType, Object value, Annotation[] contextAnnotations) {
		return makeXworkConversion(value, expectedType);
	}

	public XWorkBasicConverter getConverter() {
		if (converter == null) {
			converter = new XWorkBasicConverter();
		}
		return converter;
	}

	/**
	 * Indica si la clase pasada puede ser manejada por el conversor de xwork
	 * 
	 * @param checkedType
	 *            Tipo a comprobar
	 * @return true si el conversor de Xwork se puede utilizar
	 */
	private boolean isXworkManageableType(Class<?> checkedType) {
		final Class<?>[] xworkTypes = new Class[] {
				// Manejado por xwork basic
				String.class, int[].class, long[].class, double[].class, boolean[].class, Date.class, String[].class,
				boolean.class, Boolean.class, Calendar.class, Collection.class, Character.class, char.class,
				Number.class, Class.class,
				Object[].class,
				// Manejado por xwork default
				Integer.class, Integer.TYPE, Double.class, Double.TYPE, Boolean.class, Boolean.TYPE, Byte.class,
				Byte.TYPE, Character.class, Character.TYPE, Short.class, Short.TYPE, Long.class, Long.TYPE,
				Float.class, Float.TYPE, BigInteger.class, BigDecimal.class, Enum.class };
		for (Class<?> xworkType : xworkTypes) {
			if (xworkType.equals(checkedType)) {
				return true;
			}
		}
		if (checkedType.isPrimitive()) {
			return true;
		}
		if (checkedType.isArray()) {
			return true;
		}
		return false;
	}

	public static WrappedXWorkConverter create() {
		WrappedXWorkConverter wrapped = new WrappedXWorkConverter();
		return wrapped;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionFrom(java.lang.Class,
	 *      java.lang.reflect.Type, java.lang.Object)
	 */
	public boolean acceptsConversionFrom(Class<?> sourceType, Type expectedType, Object sourceObject) {
		return acceptsConversionBetween(sourceType, expectedType);
	}

	/**
	 * Indica si Xwork acepta la conversion pedida verificando varias condiciones más alla de las
	 * declaradas por xwork
	 * 
	 * @param sourceType
	 *            Tipo del objeto a convertir
	 * @param expectedType
	 *            Tipo esperado de la conversion
	 * @return false si la conversion no debe ser hecha por este conversor
	 */
	private boolean acceptsConversionBetween(Class<?> sourceType, Type expectedType) {
		if (!isXworkManageableType(sourceType)) {
			return false;
		}
		Class<?> expectedClass = ReflectionUtils.degenerify(expectedType);
		if (!isXworkManageableType(expectedClass)) {
			return false;
		}
		if (isBlacklistedConversion(sourceType, expectedType)) {
			return false;
		}
		return true;
	}

	/**
	 * Indica si la conversion entre los dos tipos corresponde a tipos tratables por Xwork, pero que
	 * la conversion de un tipo a otro no es manejable (o sea que termina convirtiendo un valor
	 * inválido)
	 * 
	 * @param sourceType
	 *            Tipo origen
	 * @param expectedType
	 *            Tipo esperado
	 * @return true si la conversion no debe ser hecha por este conversor
	 */
	private boolean isBlacklistedConversion(Class<?> sourceType, Type expectedType) {
		if (String.class.equals(sourceType)) {
			Class<?> expectedClass = ReflectionUtils.degenerify(expectedType);
			if (expectedClass.isArray()) {
				// Xwork no convierte bien de String a Arrays
				return true;
			}
		}
		return false;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionTo(java.lang.reflect.Type,
	 *      java.lang.Class, java.lang.Object)
	 */
	public boolean acceptsConversionTo(Type expectedType, Class<?> sourceType, Object sourceObject) {
		return acceptsConversionBetween(sourceType, expectedType);
	}
}
