/**
 * 26/02/2006 00:12:10 Copyright (C) 2006 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.lang.iterators.adapters;

import net.sf.kfgodel.dgarcia.lang.iterators.PreSizedIterator;
import net.sf.kfgodel.dgarcia.lang.iterators.ResetableIterator;
import net.sf.kfgodel.dgarcia.lang.iterators.typed.IntegerRangeIterator;

/**
 * Esta clase permite iterar los elementos de un array. La iteracion puede realizarse en forma
 * parcial y también en reverso. A diferencia del ArrayIterator de apache, este iterador agrega
 * algunos metodos para interactuar con el array mientras se lo itera, y también permite iterar en
 * sentido inverso con la misma interfaz que en sentido "normal". Sin embargo este iterador no
 * permite iterar arrays primitivos
 * 
 * @param <E>
 *            Tipo de los elementos del array
 * @version 1.0
 * @since 2006-03-23
 * @author D. Garcia
 */
public class ArrayIterator<E> implements PreSizedIterator<E>, ResetableIterator<E> {

	/**
	 * Valor con el que se indica que no se em
	 */
	private static final int UNASSIGNED_VALUE = -1;

	/**
	 * Crea un iterador del array indicado que recorrera todos los elementos.
	 * 
	 * @param <E>
	 *            Tipo de los elementos a iterar
	 * @param array
	 *            Array a recorrer
	 * @return El iterador del array creado
	 */
	public static <E> ArrayIterator<E> create(final E[] array) {
		return create(array, 0, array.length);
	}

	/**
	 * Crea un nuevo iterador del array indicado, que recorrera los elementos del rango especificado
	 * 
	 * @param <E>
	 *            Tipo de los elementos a iterar
	 * @param array
	 *            Array a recorrer
	 * @param firstIndex
	 *            Indice del primer elemento recorrido (inclusivo)
	 * @param lastIndex
	 *            Indice en el que debe detenerse la iteracion (exclusivo). Si este valor es menor
	 *            que el primero, se recorrera el array en sentido inverso
	 * @return El iterador del array
	 * @throws IllegalArgumentException
	 *             Si los parametros no respetan el contrato
	 */
	public static <E> ArrayIterator<E> create(final E[] array, final int firstIndex, final int lastIndex)
			throws IllegalArgumentException {
		if (array == null) {
			throw new IllegalArgumentException("Array cannot be null");
		}
		if (firstIndex < 0) {
			throw new IllegalArgumentException("First index cannot be less than 0");
		}
		if (firstIndex > array.length) {
			throw new IllegalArgumentException("First index cannot be lesser or bigger than array length");
		}
		if (lastIndex < -1) {
			throw new IllegalArgumentException("Second index cannot be less than -1");
		}
		if (lastIndex > array.length) {
			throw new IllegalArgumentException("Second index cannot be bigger than array length");
		}
		final ArrayIterator<E> iterador = new ArrayIterator<E>();
		iterador.array = array;
		iterador.integerIterator = IntegerRangeIterator.create(firstIndex, lastIndex);
		return iterador;
	}

	/**
	 * Array con los elementos
	 */
	private E[] array;

	/**
	 * Indice actual sobre el array
	 */
	private int currentIndex = UNASSIGNED_VALUE;

	/**
	 * Iterador de los numeros que indica el indice del elemento a devolver
	 */
	private IntegerRangeIterator integerIterator;

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return this.integerIterator.hasNext();
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public E next() {
		currentIndex = this.integerIterator.nextValue();
		return this.array[currentIndex];
	}

	/**
	 * Elimina el elemento actual del array reemplazandolo por null
	 * 
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		if (currentIndex == UNASSIGNED_VALUE) {
			throw new IllegalStateException("Cannot erase a value that wasn't asked for first");
		}
		this.array[currentIndex] = null;
	}

	/**
	 * Este metodo permite modificar el contenido actual de la posicion del array, reemplazandolo
	 * por otro valor. El último valor devuelto por next() será reemplazado.
	 * 
	 * @param elemento
	 *            Elemento a guardar
	 * @return El elemento anterior
	 */
	public E replace(final E elemento) {
		if (currentIndex == UNASSIGNED_VALUE) {
			throw new IllegalStateException("Cannot replace a value that wasn't asked for first");
		}
		final E anterior = this.array[currentIndex];
		this.array[currentIndex] = elemento;
		return anterior;
	}

	/**
	 * Revierte el estado de este iterador al principio de la iteraci�n, como si se recien hubiera
	 * obtenido
	 */
	public void reset() {
		this.integerIterator.reset();
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.iterators.PreSizedIterator#size()
	 */
	public int size() throws UnsupportedOperationException {
		return this.integerIterator.size();
	}

}
