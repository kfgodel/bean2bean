/**
 * 28/11/2005 20:17:14 Copyright (C) 2006 Dario L. Garcia
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

import net.sf.kfgodel.dgarcia.lang.iterators.PreSizedIterator;
import net.sf.kfgodel.dgarcia.lang.iterators.ResetableIterator;

import java.util.Iterator;


/**
 * Esta clase es la base de todo decorador de iterator. Para implementar el comportamiento, este
 * decorator delega en la instancia decorada. Las subclases modificaran este comportamiento en los
 * metodos que corresponda.
 * 
 * Los metodos que extienden el comportamiento de {@link Iterator} dependeran de la instancia
 * decorada. Su ejecucion se determinara en runtime, por lo que no es seguro chequear solo la
 * interfaz implementada por las sublcases de esta.
 * 
 * @param <T>
 *            Tipo de los objetos iterados
 * @version 1.0
 * @since 2006-03-23
 * @author D. Garcia
 */
public abstract class AbstractIteratorDecorator<T> implements PreSizedIterator<T>, ResetableIterator<T> {

	/**
	 * Iterador decorado
	 */
	private Iterator<? extends T> decorated;

	/**
	 * Inicializa el estado de esta instancia para que sea consistente
	 * 
	 * @param decoratedIterator
	 *            Iterador decorado por esta instancia
	 */
	protected void initialize(Iterator<? extends T> decoratedIterator) {
		this.decorated = decoratedIterator;
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return this.decorated.hasNext();
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public T next() {
		return this.decorated.next();
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		this.decorated.remove();
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.iterators.PreSizedIterator#size()
	 */
	public int size() throws UnsupportedOperationException {
		return sizeBehavior(this.decorated);
	}

	/**
	 * Comportamiento "heredable" hacia otras jerarquias. Este metodo define la manera de calcular
	 * el size() en forma general a partir de una instancia de Iterator. Se define en forma estatica
	 * para que pueda ser utilizado por otras clases que no pertenecen a esta jerarquia, pero que
	 * por razones del lenguaje no pueden utilizar herencia multiple
	 * 
	 * @param <T>
	 *            Tipo de los elementos iterados
	 * @param baseIterator
	 *            Iterador del que se obtendra el size o una excepcion
	 * @return La cantidad de elementos a iterar
	 * @throws UnsupportedOperationException
	 *             Si el iterador pasado no soporta la operacion
	 */
	public static <T> int sizeBehavior(Iterator<T> baseIterator) throws UnsupportedOperationException {
		if (baseIterator instanceof PreSizedIterator<?>) {
			PreSizedIterator<T> decoratedEx = (PreSizedIterator<T>) baseIterator;
			return decoratedEx.size();
		}
		throw new UnsupportedOperationException("Base instance is not an instance of " + PreSizedIterator.class);
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.iterators.ResetableIterator#reset()
	 */
	public void reset() {
		resetBehavior(this.decorated);
	}

	/**
	 * Comportamiento "heredable" hacia otras jerarquias. Este metodo define la manera de realizar
	 * el reset() en forma general a partir de una instancia de Iterator. Se define en forma
	 * estatica para que pueda ser utilizado por otras clases que no pertenecen a esta jerarquia,
	 * pero que por razones del lenguaje no pueden utilizar herencia multiple
	 * 
	 * @param <T>
	 *            Tipo de los elementos iterados
	 * @param baseIterator
	 *            Iterador del que se obtendra el size o una excepcion
	 * @throws UnsupportedOperationException
	 *             Si el iterador pasado no soporta la operacion
	 */
	public static <T> void resetBehavior(Iterator<T> baseIterator) throws UnsupportedOperationException {
		if (baseIterator instanceof ResetableIterator<?>) {
			ResetableIterator<T> resetable = (ResetableIterator<T>) baseIterator;
			resetable.reset();
			return;
		}
		throw new UnsupportedOperationException("Base instance is not an instance of " + ResetableIterator.class);
	}
}
