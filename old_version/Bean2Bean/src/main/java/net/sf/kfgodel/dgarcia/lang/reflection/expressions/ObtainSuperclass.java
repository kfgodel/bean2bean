/**
 * Created on 16/01/2007 01:22:09 Copyright (C) 2007 Dario L. Garcia
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
 * Based on a work at <a xmlns:dc="http://purl.org/dc/import net.sf.kfgodel.dgarcia.lang.closures.Expression;
orge.net/svnroot/bean2bean"
 * rel="dc:source">bean2bean.svn.sourceforge.net</a>
 */
package net.sf.kfgodel.dgarcia.lang.reflection.expressions;

import net.sf.kfgodel.dgarcia.lang.closures.Expression;

/**
 * Esta clase representa la expresion que permite obtener la superclase de una clase dada.
 * 
 * @version 1.0
 * @since 16/01/2007
 * @author D. Garcia
 */
public class ObtainSuperclass implements Expression<Class<?>, Class<?>> {

	/**
	 * Referencia suave para optimizar
	 */
	private static ObtainSuperclass instance;

	/**
	 * Devuelve la suprclase de la clase pasada
	 * 
	 * @param baseClass
	 *            Clase de la que se obtendrá la superclase
	 * @return La superclase de la pasada
	 * @see net.sf.kfgodel.dgarcia.lang.closures.Expression#evaluateOn(java.lang.Object)
	 */
	public Class<?> evaluateOn(Class<?> baseClass) {
		return baseClass.getSuperclass();
	}

	/**
	 * @return Devuelve una instancia de esta clase
	 */
	public static ObtainSuperclass create() {
		if (instance == null) {
			instance = new ObtainSuperclass();
		}
		return instance;
	}
}
