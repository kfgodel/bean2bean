/**
 * Oct 17, 2006 - 5:52:10 PM
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

/**
 * Esta interfaz representa un orden de iteracion de los nodos de un arbol
 * 
 * @author D. Garcia
 * @param <N>
 *            Tipo de los nodos
 */
public interface TreeOrder<N> {
	/**
	 * Incluye los nodos pasados como un conjunto para que sean ordenados por esta instancia al
	 * iterar
	 * 
	 * @param nodes
	 *            Conjunto de nodos a agregar
	 */
	public void addNodes(Iterator<N> nodes);

	/**
	 * Devuelve el conjunto de nodos que debe recorrerse
	 * 
	 * @return El iterador actual
	 */
	public Iterator<N> getCurrentNodes();
}
