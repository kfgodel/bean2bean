/**
 * Created on 13/01/2007 21:51:39 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.lang.iterators;

import java.util.Iterator;

/**
 * Esta interfaz representa un iterador de elementos que ha sido extendido agregando metodos que
 * permiten tener una funcionalidad más completa en un iterador.
 * 
 * @version 1.0
 * @since 13/01/2007
 * @author D. Garcia
 * @param <T>
 *            Tipo de los elementos a iterar
 */
public interface PreSizedIterator<T> extends Iterator<T> {
	/**
	 * Indica la cantidad de elementos iterables por este iterador. Mediante este metodo, los
	 * iteradores que lo implementen, pueden ofrecer una vision de la cantidad de elementos que se
	 * iteraran, antes de ser iterados.
	 * 
	 * @return La cantidad de elementos iterables
	 * @throws UnsupportedOperationException
	 *             Si este iterador no permite saber la cantidad de elementos a iterar (dependera de
	 *             la subclase de iterador)
	 */
	public int size() throws UnsupportedOperationException;
}
