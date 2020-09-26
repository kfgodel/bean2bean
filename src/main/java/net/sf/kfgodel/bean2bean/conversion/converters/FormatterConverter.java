/**
 * 18/01/2009 20:36:51 Copyright (C) 2006 Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.annotations.FormatterPattern;
import net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Formatter;


/**
 * Esta clase permite convertir desde cualquier objeto a String utilizando un {@link Formatter}.
 * Para la conversión se debe indicar este conversor para ser usado, junto con un annotation que
 * especifique el formato a utilizar {@link FormatterPattern}
 * 
 * @author D. Garcia
 */
public class FormatterConverter implements GeneralTypeConverter<Object, String> {

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionFrom(java.lang.Class,
	 *      java.lang.reflect.Type, java.lang.Object)
	 */
	public boolean acceptsConversionFrom(Class<?> sourceType, Type expectedType, Object sourceObject) {
		// This converter cannot be used directly
		return false;
		// return isObjectToString(sourceType, expectedType);
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionTo(java.lang.reflect.Type,
	 *      java.lang.Class, java.lang.Object)
	 */
	public boolean acceptsConversionTo(Type expectedType, Class<?> sourceType, Object sourceObject) {
		// This converter cannot be used directly
		return false;
		// return isObjectToString(sourceType, expectedType);
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertFrom(java.lang.Object,
	 *      java.lang.reflect.Type, java.lang.annotation.Annotation[])
	 */
	public String convertFrom(Object value, Type expectedType, Annotation[] contextAnnotations) {
		return formatValue(value, expectedType, contextAnnotations);
	}

	/**
	 * Realiza el formateo del valor tomando el formato del las anotaciones auxiliares
	 * 
	 * @param value
	 *            Valor a formatear
	 * @param expectedType
	 *            Tipo esperado de la conversion
	 * @param contextAnnotations
	 *            Anotaciones auxiliares
	 * @return La representacion
	 */
	private String formatValue(Object value, Type expectedType, Annotation[] contextAnnotations) {
		if (contextAnnotations == null) {
			throw new CannotConvertException("FormatterConverter must be used with annotation["
					+ FormatterPattern.class + "] to explicit format on a field", value, expectedType);
		}
		FormatterPattern pattern = ConverterUtils.getContextAnnotationLike(FormatterPattern.class, contextAnnotations);
		if (pattern == null) {
			throw new CannotConvertException("FormatterConverter must be used with annotation["
					+ FormatterPattern.class + "] to explicit format on a field", value, expectedType);
		}
		String formatPattern = pattern.value();
		Formatter formatter = new Formatter();
		formatter.format(formatPattern, value);
		String formatted = formatter.toString();
		return formatted;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertTo(java.lang.reflect.Type,
	 *      java.lang.Object, java.lang.annotation.Annotation[])
	 */
	public Object convertTo(Type expectedType, String value, Annotation[] contextAnnotations) {
		return formatValue(value, expectedType, contextAnnotations);
	}

	public static FormatterConverter create() {
		FormatterConverter converter = new FormatterConverter();
		return converter;
	}
}
