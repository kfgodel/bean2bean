/**
 * Created on 31/12/2007 02:29:33 Copyright (C) 2007 Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.json.CannotTextSerializeException;
import net.sf.kfgodel.bean2bean.conversion.converters.json.CannotTextUnserializeException;
import net.sf.kfgodel.bean2bean.conversion.converters.json.JsonTextualizer;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Esta clase sabe como convertir un objeto a una representacion de cadena utilizando el formato
 * JSON, y viceversa, como convertir desde una representacion en String de cualquier objeto usando
 * JSON a un objeto Java.
 * 
 * @version 1.1
 * @since 31/12/2007
 * @author D. Garcia
 */
public class JsonStringObjectConverter implements GeneralTypeConverter<String, Object> {

	private JsonTextualizer textualizer;

	public JsonTextualizer getTextualizer() {
		if (textualizer == null) {
			textualizer = JsonTextualizer.createWithoutTypeMetadata();
		}
		return textualizer;
	}

	public void setTextualizer(final JsonTextualizer textualizer) {
		this.textualizer = textualizer;
	}

	/**
	 * @param value
	 *            Cadena en formato JSON
	 * @param expectedType
	 *            Tipo esperado de la conversion
	 * @param contextAnnotations
	 *            No usado
	 * @return El objeto convertido de la cadena
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertFrom(java.lang.Object,
	 *      java.lang.reflect.Type, java.lang.annotation.Annotation[])
	 */
	public Object convertFrom(final String value, final Type expectedType, final Annotation[] contextAnnotations) {
		if (expectedType == null) {
			throw new CannotConvertException("Cannot make conversion. Expected type was not defined", value,
					expectedType);
		}
		try {
			final Object convertedObject = getTextualizer().convertFromStringAsType(expectedType, value);
			return convertedObject;
		} catch (final CannotTextUnserializeException e) {
			throw new CannotConvertException("Error when creating object from JSON text[" + value + "]", value,
					expectedType, e);
		}
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertTo(java.lang.reflect.Type,
	 *      java.lang.Object, java.lang.annotation.Annotation[])
	 */
	public String convertTo(final Type expectedType, final Object value, final Annotation[] contextAnnotations) {
		try {
			final String representation = getTextualizer().convertToString(value);
			return representation;
		} catch (final CannotTextSerializeException e) {
			throw new CannotConvertException("Error mapping to JSON[" + value + "]", value, expectedType, e);
		}
	}

	public static JsonStringObjectConverter create() {
		final JsonStringObjectConverter converter = new JsonStringObjectConverter();
		// Forzamos la inicializacion para que tire error en este momento
		converter.getTextualizer();
		return converter;
	}

	/**
	 * @param sourceType
	 * @param expectedType
	 * @param sourceObject
	 * @return empty
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionFrom(java.lang.Class,
	 *      java.lang.reflect.Type, java.lang.Object)
	 */
	public boolean acceptsConversionFrom(final Class<?> sourceType, final Type expectedType, final Object sourceObject) {
		return sourceType.equals(String.class);
	}

	/**
	 * @param expectedType
	 * @param sourceType
	 * @param sourceObject
	 * @return empty
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionTo(java.lang.reflect.Type,
	 *      java.lang.Class, java.lang.Object)
	 */
	public boolean acceptsConversionTo(final Type expectedType, final Class<?> sourceType, final Object sourceObject) {
		final Class<?> destinationType = ReflectionUtils.degenerify(expectedType);
		return destinationType.equals(String.class);
	}

}
