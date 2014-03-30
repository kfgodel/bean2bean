/**
 * 25/03/2006 21:39:33 Copyright (C) 2006 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.lang.reflection.conditions;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import net.sf.kfgodel.dgarcia.lang.closures.Condition;


/**
 * Esta clase permite comprobar si un elemento del lenguaje contiene cualquiera de las anotaciones
 * indicadas.
 * 
 * @version 1.0
 * @since 18/01/2007
 * @author D. Garcia
 */
public class AnnotatedCondition implements Condition<AnnotatedElement> {

	/**
	 * Tipo de las annotations buscadas
	 */
	private Class<? extends Annotation>[] annotationTypes;

	/**
	 * Verifica el elemento pasado contenga alguna de las annotations indicadas
	 * 
	 * @param element
	 *            Elemento del lenguaje que puede tener las annotations indicadas
	 * @return false si el elemento no contiene ninguna de las annotaciones
	 * @see net.sf.kfgodel.dgarcia.lang.closures.Condition#isMetBy(Object)
	 */
	public boolean isMetBy(AnnotatedElement element) {
		for (Class<? extends Annotation> annotationType : this.annotationTypes) {
			if (element.isAnnotationPresent(annotationType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Crea una nueva condicion que verificara la existencia de los annotations indicados en el
	 * elemento pasado
	 * 
	 * @param annotations
	 *            Conjunto de clases de annotations que la condicion creada buscara en los elementos
	 *            pasados. Cualquiera de estos annotations presentes en el elemento pasado hara que
	 *            esta condicion se cumpla
	 * @return La nueva condicion creada
	 */
	public static AnnotatedCondition create(Class<? extends Annotation>... annotations) {
		AnnotatedCondition annotatedCondition = new AnnotatedCondition();
		annotatedCondition.annotationTypes = annotations;
		return annotatedCondition;
	}
}
