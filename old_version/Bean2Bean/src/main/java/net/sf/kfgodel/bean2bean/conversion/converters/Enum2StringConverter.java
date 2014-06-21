/**
 * Created on 29/12/2007 12:19:52 Copyright (C) 2007 Dario L. Garcia
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


/**
 * Esta clase sabe como convertir de enums a strings
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 */
@SuppressWarnings("unchecked")
public class Enum2StringConverter implements SpecializedTypeConverter<Enum, String> {

	/**
	 * @param expectedType
	 *            Tipo esperado que no sera tomado en cuenta
	 * @param sourceObject
	 *            Enum a convertir
	 * @param contextAnnotations
	 *            No usado
	 * @return El name del enum
	 * @see net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter#convertTo(java.lang.reflect.Type,
	 *      java.lang.Object, java.lang.annotation.Annotation[])
	 */
	public String convertTo(Type expectedType, Enum sourceObject, Annotation[] contextAnnotations) {
		return sourceObject.name();
	}

	public static Enum2StringConverter create() {
		return new Enum2StringConverter();
	}

}
