/**
 * Created on 16/01/2007 01:12:29 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.lang.iterators.production;

import java.util.Iterator;

import net.sf.kfgodel.dgarcia.lang.closures.Expression;


/**
 * Esta clase representa un iterador de elementos en el que cada elemento es el resultado de una
 * transformacion del anterior. Se utiliza una expresion que devuelve el elemento siguiente a iterar
 * o null, si no quedan mas.
 * 
 * @version 1.0
 * @since 16/01/2007
 * @author D. Garcia
 * @param <E>
 *            Tipo de los elementos iterados
 */
public class ChainedTransformationIterator<E> implements Iterator<E> {

	/**
	 * Proximo elemento a devolver
	 */
	private E nextElement;

	/**
	 * Expresion que produce los nuevos elementos
	 */
	private Expression<? super E, ? extends E> transformation;

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return nextElement != null;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public E next() {
		if (nextElement == null) {
			throw new IllegalStateException("There're no elements to iterate");
		}
		E returned = nextElement;
		nextElement = this.transformation.evaluateOn(nextElement);
		return returned;
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException("Elements cannot be removed");
	}

	/**
	 * Crea un iterador de los elementos basado en la operacion para producir elementos
	 * 
	 * @param <E>
	 *            Tipo de los elementos a iterar
	 * @param firstElement
	 *            Elemento del que se parte en la iteracion
	 * @param productionRule
	 *            Expresion que permite obtener el proximo elemento de la iteracion. Esta expresion
	 *            devolvera null cuando no quede ningun elemento mas
	 * @return El iterador
	 */
	public static <E> ChainedTransformationIterator<E> createFrom(E firstElement,
			Expression<? super E, ? extends E> productionRule) {
		ChainedTransformationIterator<E> iterator = new ChainedTransformationIterator<E>();
		iterator.nextElement = firstElement;
		iterator.transformation = productionRule;
		return iterator;
	}

}
