/**
 * Oct 18, 2006 - 10:40:49 AM
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
package net.sf.kfgodel.dgarcia.lang.iterators.tree.treeorder;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Este orden recorre los nodos por nivel, recorriendo el arbol a lo ancho, antes de profundizar en
 * niveles
 * 
 * @author dgarcia
 */
public class BreadthFirstOrder implements TreeOrder<Object> {

	/**
	 * Crea una nueva instancia de este orden
	 * 
	 * @param <N>
	 *            Tipo de los nodos a recorrer
	 * @return La nueva instancia
	 */
	@SuppressWarnings("unchecked")
	public static <N> TreeOrder<N> create() {
		return (TreeOrder<N>) new BreadthFirstOrder();
	}

	/**
	 * Cola de iteradores que define implicitamente el orden a recorrer
	 */
	private final Queue<Iterator<Object>> cola = new LinkedList<Iterator<Object>>();

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.iterators.tree.treeorder.TreeOrder#addNodes(java.util.Iterator)
	 */
	public void addNodes(Iterator<Object> nodes) {
		cola.offer(nodes);
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.iterators.tree.treeorder.TreeOrder#getCurrentNodes()
	 */
	public Iterator<Object> getCurrentNodes() {
		while (!cola.isEmpty()) {
			Iterator<Object> currentNodes = cola.peek();
			if (currentNodes.hasNext()) {
				return currentNodes;
			}
			cola.poll();
		}
		return null;
	}

}
