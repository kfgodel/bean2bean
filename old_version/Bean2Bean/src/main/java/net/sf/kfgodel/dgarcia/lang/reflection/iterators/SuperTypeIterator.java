/**
 * Created on 29/12/2007 02:34:24 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.lang.reflection.iterators;

import java.lang.reflect.Type;
import java.util.Iterator;

import net.sf.kfgodel.dgarcia.lang.iterators.tree.NodeExploder;
import net.sf.kfgodel.dgarcia.lang.iterators.tree.TreeIterator;
import net.sf.kfgodel.dgarcia.lang.iterators.tree.treeorder.BreadthFirstOrder;
import net.sf.kfgodel.dgarcia.lang.iterators.tree.treeorder.GraphOrder;


/**
 * Esta clase representa un iterador de supertipos, que a partir de una clase permite acceder a
 * todos sus supertipos, incluyendo superclases e interfaces implementadas.
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 */
public class SuperTypeIterator {
	/**
	 * Crea un iterador de la clase que permite recorrer los supertipos de la clase pasada,
	 * partiendo desde la misma clase pasada como primer elemento devuelto.<br>
	 * Al iterar los supertipos, primero se recorre nivel por nivel hacia arriba en la jerarquia,
	 * devolviendo la superclase primero y luego las interfaces implementadas.<br>
	 * Las clases devueltas no se repiten.
	 * 
	 * @param clazz
	 *            Clase desde la que se exploraran los supertipos
	 * @return El iterador de las clases que representan los supertipos incluyendo a Object.
	 */
	public static Iterator<Class<?>> createFor(Class<?> clazz) {
		@SuppressWarnings("unchecked")
		NodeExploder<Class<?>> superTypeExploder = (NodeExploder) new SuperTypeExploder();
		GraphOrder<Class<?>> graphOrder = GraphOrder.createFrom(BreadthFirstOrder.<Class<?>> create());
		Iterator<Class<?>> iterator = TreeIterator.createFromRoot(clazz, superTypeExploder, graphOrder);
		return iterator;
	}

	/**
	 * Crea un iterador de supertipos a partir del tipo generico pasado
	 * 
	 * @param expectedType
	 *            Tipo desde el que se obtendran los supertipos, Si es una instancia de un tipo
	 *            parametrizado, se devolvera el tipo parametrizado como primer valor, y luego el
	 *            tipo sin parametrizar. Luego el resto de los supertipos
	 * @return El iterador de los supertipos del tipo pasado
	 */
	public static Iterator<Type> createFor(Type expectedType) {
		NodeExploder<Type> superTypeExploder = new SuperTypeExploder();
		GraphOrder<Type> graphOrder = GraphOrder.createFrom(BreadthFirstOrder.<Type> create());
		Iterator<Type> iterator = TreeIterator.createFromRoot(expectedType, superTypeExploder, graphOrder);
		return iterator;
	}

}
