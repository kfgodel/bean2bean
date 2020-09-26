/**
 *<a rel="license" href="http://creativecommons.org/licenses/by/2.5/ar/"><img
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
package net.sf.kfgodel.dgarcia.lang.reflection.iterators;

import net.sf.kfgodel.dgarcia.lang.iterators.tree.NodeExploder;
import net.sf.kfgodel.dgarcia.lang.iterators.tree.TreeIterator;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Esta clase permite obtener los supertipos de una clase.<br>
 * Normalmente esta clase se utiliza junto con el {@link TreeIterator} para recorrer las superclases
 * de una clase
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 */
public final class SuperTypeExploder implements NodeExploder<Type> {
	/**
	 * @param node
	 *            Clase desde la que se piden los supertipos inmediatos
	 * @return El conjunto de los supertipos del nivel superior
	 * @see net.sf.kfgodel.dgarcia.lang.iterators.tree.NodeExploder#evaluateOn(java.lang.Object)
	 */
	public Iterator<Type> evaluateOn(Type node) {
		ArrayList<Type> superTypes = new ArrayList<Type>();
		Class<?> degenerified;
		if (!(node instanceof Class<?>)) {
			degenerified = ReflectionUtils.degenerify(node);
			superTypes.add(degenerified);
		}
		else {
			degenerified = (Class<?>) node;
		}

		Class<?> superclass = degenerified.getSuperclass();
		if (superclass != null) {
			superTypes.add(superclass);
		}

		Class<?>[] superInterfaces = degenerified.getInterfaces();
		for (Class<?> superInterface : superInterfaces) {
			superTypes.add(superInterface);
		}
		return superTypes.iterator();
	}
}