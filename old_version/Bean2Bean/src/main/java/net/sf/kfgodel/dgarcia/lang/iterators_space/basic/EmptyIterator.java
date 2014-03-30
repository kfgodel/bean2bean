/**
 * 15/04/2006 17:10:50 Copyright (C) 2006 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.lang.iterators_space.basic;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.kfgodel.dgarcia.lang.iterators.PreSizedIterator;
import net.sf.kfgodel.dgarcia.lang.iterators.ResetableIterator;


/**
 * Esta clase representa un iterador de una coleccion vacia por lo que no se puede iterar sobre
 * ningun elemento
 * 
 * @author D. Garcia
 */
public class EmptyIterator implements ResetableIterator<Object>, PreSizedIterator<Object> {

	/**
	 * Singleton
	 */
	private static final EmptyIterator instance = new EmptyIterator();

	/**
	 * Getter de la instancia que permite obtener este iterador sin tipo, asociado a un tipo
	 * particulars
	 * 
	 * @param <T>
	 *            Tipo de iterador necesitado
	 * @return La unica instancia de esta clase
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<T> getInstance() {
		return (Iterator<T>) instance;
	}

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return false;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public Object next() {
		throw new NoSuchElementException();
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new IllegalStateException();
	}

	public void reset() {
		// Listo!
	}

	public int size() throws UnsupportedOperationException {
		return 0;
	}
}
