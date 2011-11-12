/**
 * 28/11/2005 20:44:56 Copyright (C) 2006 Dario L. Garcia
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

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.kfgodel.dgarcia.lang.closures.Condition;


/**
 * Iterador que recorre los elementos de otro iterador salteando aquellos que no cumplen con la
 * condicion pasada
 * 
 * @param <T>
 *            Tipo de los objetos iterados
 * @version 1.0
 * @since 2006-03-23
 * @author D. Garcia
 */
public class ConditionalIterator<T> extends AbstractIteratorDecorator<T> {

	/**
	 * Condicion que deben cumplir los elementos devueltos
	 */
	private Condition<? super T> condition;

	/**
	 * El ultimo objeto valido iterado que sera devuelto en next()
	 */
	private T encontrado;

	/**
	 * Crea un iterador condicional sobre el iterador indicado
	 * 
	 * @param <T>
	 *            Tipo de los elementos a iterar
	 * @param <I>
	 *            Tipo de iterador base
	 * @param <C>
	 *            Tipo de condicion
	 * @param condition
	 *            Condicion que deben cumplir los elementos iterados
	 * @param decorated
	 *            Iterador decorado que permite iterar todos los elementos
	 * @return El iterador parcial
	 */
	public static <T, I extends Iterator<? extends T>, C extends Condition<? super T>> ConditionalIterator<T> createFrom(
			C condition, I decorated) {
		ConditionalIterator<T> iterator = new ConditionalIterator<T>();
		iterator.initialize(decorated);
		iterator.condition = condition;
		iterator.encontrado = null;
		return iterator;
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		if (this.encontrado != null) {
			return true;
		}
		while (super.hasNext()) {
			T elemento = super.next();
			if (this.condition.isMetBy(elemento)) {
				this.encontrado = elemento;
				break;
			}
		}
		return this.encontrado != null;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	@Override
	public T next() {
		if (this.encontrado == null) {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
		}
		T devuelto = this.encontrado;
		this.encontrado = null;
		return devuelto;
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		if (this.encontrado != null) {
			throw new IllegalStateException();
		}
		super.remove();
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.iterators.basic.AbstractIteratorDecorator#size()
	 */
	@Override
	public int size() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This iterator cannot foretell the element size");
	}

}
