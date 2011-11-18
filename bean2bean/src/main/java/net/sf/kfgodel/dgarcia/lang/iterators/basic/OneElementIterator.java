/**
 * 16/04/2006 15:56:06 Copyright (C) 2006 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.lang.iterators.basic;

import java.util.NoSuchElementException;

import net.sf.kfgodel.dgarcia.lang.iterators.PreSizedIterator;
import net.sf.kfgodel.dgarcia.lang.iterators.ResetableIterator;


/**
 * Este iterador permite iterar sobre un unico elemento
 * 
 * @param <T>
 *            Tipo del elemento iterado
 * @author D. Garcia
 */
public class OneElementIterator<T> implements PreSizedIterator<T>, ResetableIterator<T> {

	/**
	 * Unico elemento iterado
	 */
	private T element;

	/**
	 * Flag que indica si el elemento ya fue devuelto
	 */
	private boolean alreadyReturned = false;

	/**
	 * Crea un iterador de un unico elemento para el objeto pasado
	 * 
	 * @param <T>
	 *            Tipo del elemento a iterar
	 * @param element
	 *            Elemento a iterar
	 * @return El iterador del elemento
	 */
	public static <T> OneElementIterator<T> createFrom(T element) {
		OneElementIterator<T> iterator = new OneElementIterator<T>();
		iterator.element = element;
		return iterator;
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return !this.isAlreadyReturned();
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public T next() {
		if (this.isAlreadyReturned()) {
			throw new NoSuchElementException();
		}
		this.setAlreadyReturned(true);
		return this.element;
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Indica si el elemento ya fue devuelto y este iterador lleg� a su fin
	 * 
	 * @return true si ya se devolvio el elemento
	 */
	private boolean isAlreadyReturned() {
		return alreadyReturned;
	}

	/**
	 * Establece si se llegó al final de la iteraci�n y ya se devovi� el elemento
	 * 
	 * @param alreadyReturned
	 *            true si se lelg� al final
	 */
	private void setAlreadyReturned(boolean alreadyReturned) {
		this.alreadyReturned = alreadyReturned;
	}

	/**
	 * Resetea el iterador permitiendo reutilizarlo
	 */
	public void reset() {
		this.setAlreadyReturned(false);
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.iterators.PreSizedIterator#size()
	 */
	public int size() throws UnsupportedOperationException {
		return 1;
	}
}
