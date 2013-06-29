/**
 * Created on 29/12/2007 20:59:36 Copyright (C) 2007 Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;


/**
 * Esta clase sabe como convertir valores cuando el tipo destino es polimorfico con el origen, de
 * manera que son asignables entre si.<br>
 * Este conversor deberia ser uno de los ultimos utilizados, ya que en realidad no realiza ninguna
 * conversion, solo se basa en el polimorfismo de clases para asignar tipos compatibles.<br>
 * Este conversor no toma en cuenta los parametros de los tipos a convertir, por lo que se debe
 * utilizar el {@link Collection2CollectionConverter} antes que este conversor si se va a tratar con
 * colecciones
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 */
public class PolymorphismConverter implements GeneralTypeConverter<Object, Object> {

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertFrom(java.lang.Object,
	 *      java.lang.reflect.Type, java.lang.annotation.Annotation[])
	 */
	public Object convertFrom(Object value, Type expectedType, Annotation[] contextAnnotations) {
		return value;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertTo(java.lang.reflect.Type,
	 *      java.lang.Object, java.lang.annotation.Annotation[])
	 */
	public Object convertTo(Type expectedType, Object value, Annotation[] contextAnnotations) {
		return value;
	}

	public static PolymorphismConverter create() {
		return new PolymorphismConverter();
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionFrom(java.lang.Class,
	 *      java.lang.reflect.Type, java.lang.Object)
	 */
	public boolean acceptsConversionFrom(Class<?> sourceType, Type expectedType, Object sourceObject) {
		Class<?> expectedClass = ReflectionUtils.degenerify(expectedType);
		return expectedClass.isAssignableFrom(sourceType);
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionTo(java.lang.reflect.Type,
	 *      java.lang.Class, java.lang.Object)
	 */
	public boolean acceptsConversionTo(Type expectedType, Class<?> sourceType, Object sourceObject) {
		Class<?> expectedClass = ReflectionUtils.degenerify(expectedType);
		return expectedClass.isAssignableFrom(sourceType);
	}

}
