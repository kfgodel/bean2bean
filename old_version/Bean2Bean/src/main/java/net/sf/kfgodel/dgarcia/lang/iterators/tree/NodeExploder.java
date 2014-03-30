/**
 * Oct 17, 2006 - 3:52:17 PM
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
package net.sf.kfgodel.dgarcia.lang.iterators.tree;

import java.util.Iterator;

import net.sf.kfgodel.dgarcia.lang.closures.Expression;


/**
 * Esta interfaz representa un objeto que permite obtener a partir de un nodo un conjunto de
 * subnodos
 * 
 * @author dgarcia
 * 
 * @param <N>
 *            Tipo de los nodos expandidos
 */
public interface NodeExploder<N> extends Expression<N, Iterator<N>> {
	/**
	 * Devuelve un iterador de todos los sub nodos que surgen a partir del pasado
	 * 
	 * @param node
	 *            Nodo del que se obtendran los subnodos
	 * @return El iterador de los subnodos o null si no existen subnodos
	 * @see net.sf.kfgodel.dgarcia.lang.closures.Expression#evaluateOn(java.lang.Object)
	 */
	public Iterator<N> evaluateOn(N node);
}
