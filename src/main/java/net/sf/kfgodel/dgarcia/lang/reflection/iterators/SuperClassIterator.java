/**
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
 * rel="dc:source">bean2bean.svn.sourceforge.net</a> *
 */
package net.sf.kfgodel.dgarcia.lang.reflection.iterators;

import net.sf.kfgodel.dgarcia.lang.closures.Expression;
import net.sf.kfgodel.dgarcia.lang.iterators.production.ChainedTransformationIterator;
import net.sf.kfgodel.dgarcia.lang.reflection.expressions.ObtainSuperclass;

import java.util.Iterator;


/**
 * Esta clase permite iterar la jerarquia de una clase hasta llegar a la clase Object
 * 
 * @version 1.0
 * @since 18/01/2007
 * @author D. Garcia
 */
public class SuperClassIterator {
	/**
	 * Crea un iterador de las superclases de una clase
	 * 
	 * @param <T>
	 *            Tipo base de la clase a iterar
	 * @param clazz
	 *            Clase de la que se obtendran las superclases, incluyendo a la misma como primera
	 * @return El iterador de la jerarquia
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<Class<? super T>> createFrom(Class<T> clazz) {
		return ChainedTransformationIterator.<Class<? super T>> createFrom(clazz, (Expression) ObtainSuperclass
				.create());
	}
}