/**
 * Created on 13/01/2007 23:29:26 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.lang.iterators.typed;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.kfgodel.dgarcia.lang.iterators.PreSizedIterator;
import net.sf.kfgodel.dgarcia.lang.iterators.ResetableIterator;

/**
 * Esta clase representa un iterador de un rango de numeros enteros. Mediante este iterador se puede
 * recorrer un segmento de la recta de los numeros enteros.
 * 
 * Esta clase ofrece dos interfaces para iterar los numeros. Una utiliza la del {@link Iterator} de
 * java, en la cual será necesario crear objetos {@link Integer} por cada numero devuelto. Esta
 * interfaz se utiliza para unificar interfaces, pero no es la más performante. <br>
 * Si la performance es importante (y es posible) utilizar la interfaz que devuelve int en vez de
 * {@link Integer};
 * 
 * @version 1.0
 * @since 13/01/2007
 * @author D. Garcia
 */
public class IntegerRangeIterator implements PreSizedIterator<Integer>, ResetableIterator<Integer> {

	/**
	 * Primer numero de la iteracion
	 */
	private int firstNumber;
	/**
	 * Ultimo numero de la iteracion
	 */
	private int lastNumber;
	/**
	 * Incremento con el que se va modificando el valor actual
	 */
	private int delta;
	/**
	 * Valor actual del iterador
	 */
	private int current;

	/**
	 * Indica si ya se devolvio el primer valor
	 */
	private boolean alreadyReturnedFirstValue = false;

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.iterators.PreSizedIterator#size()
	 */
	public int size() throws UnsupportedOperationException {
		final int absoluteRange = Math.abs(lastNumber - firstNumber);
		final int stepSize = Math.abs(delta);
		final int phase = (stepSize - 1);
		final int relativeRange = absoluteRange + phase;
		final int steps = relativeRange / stepSize;
		return steps;
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		int nextValue = current;
		if (alreadyReturnedFirstValue) {
			nextValue += delta;
		}
		// Si se recorre hacia atras
		if (delta < 0) {
			return nextValue > lastNumber;
		}
		return nextValue < lastNumber;
	}

	/**
	 * Devuelve el proximo valor entero que corresponde a la iteracion. Este metodo es más
	 * performante que {@link #next()} ya que no realiza el boxing a {@link Integer}
	 * 
	 * @return El valor entero de la iteracion
	 */
	public int nextValue() {
		if (!this.hasNext()) {
			throw new NoSuchElementException("hasNext() has to be called first");
		}
		if (alreadyReturnedFirstValue) {
			current += delta;
		}
		alreadyReturnedFirstValue = true;
		return current;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public Integer next() {
		// Auto-boxing
		return nextValue();
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException("Cannot remove elements with this iterator: "
				+ this.getClass().getSimpleName());
	}

	/**
	 * Crea un iterador de un rango numerico. El iterador recorrera del primero al segundo numero
	 * utilizando un delta para llegar. Si el ultimo numero es menor que el primero, entoces se
	 * recorrera en sentido decreciente
	 * 
	 * @param firstNumber
	 *            Primer numero (inclusivo) devuelto por el iterador
	 * @param lastNumber
	 *            Limite de la iteracion no incluido en ella
	 * @param delta
	 *            Tama�o del paso para llegar desde uno a otro numero (siempre un numero positivo)
	 * @return El iterador de los numeros
	 */
	public static IntegerRangeIterator create(final int firstNumber, final int lastNumber, int delta)
			throws IllegalArgumentException {
		if (delta <= 0) {
			throw new IllegalArgumentException("Delta cannot be less or equals to 0: " + delta);
		}
		if (firstNumber > lastNumber) {
			delta *= -1;
		}

		final IntegerRangeIterator iterator = new IntegerRangeIterator();
		iterator.firstNumber = firstNumber;
		iterator.current = firstNumber;
		iterator.delta = delta;
		iterator.lastNumber = lastNumber;
		return iterator;
	}

	/**
	 * Crea un iterador de un rango numerico. El iterador recorrera del primero al segundo numero,
	 * uno por uno. Si el ultimo numero es menor que el primero, entoces se recorrera en sentido
	 * decreciente
	 * 
	 * @param firstNumber
	 *            Primer numero (inclusivo) devuelto por el iterador
	 * @param lastNumber
	 *            Limite de la iteracion no incluido en ella
	 * @return El iterador de los numeros
	 */
	public static IntegerRangeIterator create(final int firstNumber, final int lastNumber) {
		return create(firstNumber, lastNumber, 1);
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.iterators.ResetableIterator#reset()
	 */
	public void reset() {
		current = firstNumber;
		alreadyReturnedFirstValue = false;
	}

}
