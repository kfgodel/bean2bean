/**
 * 11/12/2006 17:47:35 Copyright (C) 2006 Dario L. Garcia
 * 
 * * <a rel="license" href="http://creativecommons.org/licenses/by/2.5/ar/"><img
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

import net.sf.kfgodel.dgarcia.lang.iterators.basic.ConditionalIterator;

import java.util.Iterator;


/**
 * Esta clase representa el orden de recorrido de nodos en un grafo. A diferencia de un arbol, en el
 * grafo se pueden encontrar caminos repetidos que deberan evitarse. Basado en alguno de los otros
 * dos ordenes ( {@link BreadthFirstOrder} o {@link DeepFirstOrder} ) este orden determina el orden
 * de los nodos a recorrer, filtrando los repetidos.
 * 
 * @author D. Garcia
 * @param <N>
 *            Tipo de los nodos iterados
 */
public class GraphOrder<N> implements TreeOrder<N> {

	/**
	 * Orden en el que se basa este recorrido de nodos
	 */
	private TreeOrder<N> ordenBase;

	/**
	 * Condicion que permite saber cuando se repitio un nodo iterado
	 */
	private final OnlyOnceCondition<N> filtroRepetidos = new OnlyOnceCondition<N>();

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.iterators.tree.treeorder.TreeOrder#addNodes(java.util.Iterator)
	 */
	public void addNodes(Iterator<N> nodes) {
		ConditionalIterator<N> iteradorFiltrado = ConditionalIterator.createFrom(filtroRepetidos, nodes);
		this.ordenBase.addNodes(iteradorFiltrado);
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.iterators.tree.treeorder.TreeOrder#getCurrentNodes()
	 */
	public Iterator<N> getCurrentNodes() {
		return this.ordenBase.getCurrentNodes();
	}

	/**
	 * Crea una instancia de un orden tipo grafo para recorrer uns estructura con nodos repetidos
	 * que deben ser filtrados. Como base para la construccion se utiliza otro orden que sera el que
	 * determine el verdadero orden de recorrido
	 * 
	 * @param <N>
	 *            Tipo de los nodos iterados
	 * @param ordenBase
	 *            Orden sobre el que se agregaran restricciones para iterar como si fuera un grafo
	 * @return El nuevo orden grafo que filtrara nodos repetidos
	 */
	public static <N> GraphOrder<N> createFrom(TreeOrder<N> ordenBase) {
		GraphOrder<N> graphOrder = new GraphOrder<N>();
		graphOrder.setOrdenBase(ordenBase);
		return graphOrder;
	}

	private void setOrdenBase(TreeOrder<N> ordenBase) {
		this.ordenBase = ordenBase;
	}

}
