/**
 * 18/01/2009 21:11:59 Copyright (C) 2006 Dario L. Garcia
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

/**
 * Esta clase reune algunos métodos útiles para implementar nuevos conversores
 * 
 * @author D. Garcia
 */
public class ConverterUtils {

	/**
	 * Devuelve la annotation indicada del conjunto pasado como contexto en la convesion
	 * 
	 * @param <T>
	 *            Tipo de la anotacion esperada
	 * 
	 * @param expectedAnnotation
	 *            Tipo de annotacion buscada
	 * @param contextAnnotations
	 *            Conjunto de anotaciones pasadas como contexto
	 * @return null si la anotacion esperada no se encuentra dentro del conjunto
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T getContextAnnotationLike(Class<T> expectedAnnotation,
			Annotation[] contextAnnotations) {
		for (Annotation annotation : contextAnnotations) {
			if (annotation == null) {
				continue;
			}
			if (expectedAnnotation.isAssignableFrom(annotation.getClass())) {
				return (T) annotation;
			}
		}
		return null;
	}

}
