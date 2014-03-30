/**
 * Oct 17, 2006 - 5:47:33 PM
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

import net.sf.kfgodel.dgarcia.lang.iterators.basic.OneElementIterator;
import net.sf.kfgodel.dgarcia.lang.iterators.tree.treeorder.GraphOrder;
import net.sf.kfgodel.dgarcia.lang.iterators.tree.treeorder.TreeOrder;


/**
 * Esta clase representa un iterador de los nodos de un arbol que permite recorrerlo con distintos
 * ordenes. Mediante este iterador tambien se pueden recorrer grafos, pero se debe especificar el
 * orden {@link GraphOrder}
 * 
 * @author dgarcia
 * @param <N>
 *            Tipo de los nodos iterados
 */
public class TreeIterator<N> implements Iterator<N> {

	/**
	 * Crea un iterador del arbol a partir de su nodo inicial
	 * 
	 * @param <N>
	 *            Tipo de los nodos recorridos
	 * @param root
	 *            Raiz del arbol
	 * @param nodeExplorer
	 *            Objeto que permite conocer los subnodos de un nodo
	 * @param order
	 *            Orden de recorrido de los nodos
	 * @return El iterador creado
	 */
	public static <N> TreeIterator<N> createFromRoot(N root, NodeExploder<N> nodeExplorer, TreeOrder<N> order) {
		TreeIterator<N> iterador = new TreeIterator<N>();
		iterador.initialize(root, nodeExplorer, order);
		return iterador;
	}

	/**
	 * Inicializa el estado de este iterador asegurandose que sea consistente
	 * 
	 * @param root
	 *            Nodo raiz del que se comenzara a iterar
	 * @param nodeExplorer
	 *            Codigo que permite obtener los subnodos a partir de un nodo
	 * @param assignedOrder
	 *            Orden de iteracion de los nodos
	 */
	private void initialize(N root, NodeExploder<N> nodeExplorer, TreeOrder<N> assignedOrder) {
		OneElementIterator<N> rootIterator = OneElementIterator.createFrom(root);
		assignedOrder.addNodes(rootIterator);
		this.setOrder(assignedOrder);
		this.setNodeExploder(nodeExplorer);
	}

	/**
	 * Objeto que define lo subnodos de un nodo
	 */
	private NodeExploder<N> nodeExploder;

	/**
	 * Orden de recorrido del arbol
	 */
	private TreeOrder<N> order;

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return this.order.getCurrentNodes() != null;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public N next() {
		Iterator<N> currentNodes = this.order.getCurrentNodes();
		if (currentNodes == null) {
			throw new IllegalStateException("hasNext() wasn't called first");
		}
		if (!currentNodes.hasNext()) {
			throw new IllegalStateException("hasNext() wasn't called first");
		}
		N currentNode = currentNodes.next();
		Iterator<N> subNodes = this.nodeExploder.evaluateOn(currentNode);
		if (subNodes != null) {
			order.addNodes(subNodes);
		}
		return currentNode;
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	private void setNodeExploder(NodeExploder<N> nodeExploder) {
		this.nodeExploder = nodeExploder;
	}

	private void setOrder(TreeOrder<N> order) {
		this.order = order;
	}

}
