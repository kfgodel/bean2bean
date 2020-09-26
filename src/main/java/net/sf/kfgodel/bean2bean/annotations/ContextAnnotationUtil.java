/**
 * Created on 06/01/2008 22:30:58 Copyright (C) 2008 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.annotations;

import java.lang.annotation.Annotation;

/**
 * Esta clase tiene algunos metodos de utilidad para trabajar con las los annotations de contexto
 * 
 * @version 1.0
 * @since 06/01/2008
 * @author D. Garcia
 */
public class ContextAnnotationUtil {

	/**
	 * A partir del array de annotations pasado a un conversor, devuelve el annotation del tipo
	 * indicado.
	 * 
	 * @param <T>
	 *            Tipo del annotation esperado
	 * @param contextAnnotations
	 *            Annotations pasadas al conversor
	 * @param annotationType
	 *            Tipo del annotation a devolver
	 * @return El annotation en el array o null si no existia ninguno
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T getAnnotationFrom(Annotation[] contextAnnotations, Class<T> annotationType) {
		if (contextAnnotations == null) {
			return null;
		}
		for (Annotation annotation : contextAnnotations) {
			if (annotation.annotationType().equals(annotationType)) {
				return (T) annotation;
			}
		}
		return null;
	}

}
