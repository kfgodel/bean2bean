/**
 * 25/03/2006 20:34:01 Copyright (C) 2006 Dario L. Garcia
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

import java.lang.reflect.Member;
import java.util.Iterator;

import net.sf.kfgodel.dgarcia.lang.closures.Expression;
import net.sf.kfgodel.dgarcia.lang.iterators.adapters.ArrayIterator;


/**
 * Esta clase permite iterar todos los miembros de una clase, incluyendo tanto los privados como los
 * heredados. Se comienza a iterar desde la propia clase y se va subiendo por la jerarquia,
 * aplicando la expresion en cada clase
 * 
 * @param <T>
 *            Tipo de miembro iterado
 * 
 * @version 1.0
 * @since 18/01/2007
 * @author D. Garcia
 */
public class ClassMemberIterator<T extends Member> implements Iterator<T> {

	/**
	 * Iterador de las superclases que permite recorrer los metodos heredados
	 */
	private Iterator<Class<?>> classIterator;

	/**
	 * Extractor del miembro que permite obtener el array de miembros del tipo correspondiente desde
	 * la clase
	 */
	private Expression<? super Class<?>, ? extends T[]> extractor;

	/**
	 * Iterador del array de metodos que permite recorrer los metodos devueltos por la nueva clase
	 */
	private ArrayIterator<T> memberIterator;

	/**
	 * Constructor basado en la clase cuyos metodos seran iterados
	 * 
	 * @param clazz
	 *            Clase desde la que se recorreran todos los metodos
	 * @param extractor
	 *            Expresion que permite obtener el tipo de miembro deseado de cada clase iterada
	 */

	/**
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		// Mientras no hayan miembros que iterar
		while ((memberIterator == null) || !memberIterator.hasNext()) {
			// Si hay clase para iterar
			while (this.classIterator.hasNext()) {
				Class<?> clazz = this.classIterator.next();
				T[] members = this.extractor.evaluateOn(clazz);
				// Si la clase tiene miembros para iterar
				if (members.length > 0) {
					this.memberIterator = ArrayIterator.create(members);
					return true;
				}
			}
			return false;
		}
		// Si hay miembros, se continua normalmente
		return true;
	}

	/**
	 * @see java.util.Iterator#next()
	 */
	public T next() {
		if (this.memberIterator == null) {
			throw new IllegalStateException();
		}
		return this.memberIterator.next();
	}

	/**
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Crea un iterador de miembros de la clase pasada, que se obtendran a partir del extractor de
	 * miembros indicado.<br>
	 * Los miembros devueltos incluyen los privados y los heredados
	 * 
	 * @param <T>
	 *            Tipo de los miebros iterados
	 * @param <C>
	 *            Tipo de la clase a iterar
	 * @param clazz
	 *            Clase cuyos miembros seran iterados
	 * @param extractor
	 *            Extractor de miebros de la clase
	 * @return El iterador de los miembros de la clase
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Member, C> ClassMemberIterator<T> create(Class<C> clazz,
			Expression<? super Class<? super C>, ? extends T[]> extractor) {
		ClassMemberIterator<T> classMemberIterator = new ClassMemberIterator<T>();
		classMemberIterator.extractor = (Expression) extractor;
		classMemberIterator.classIterator = (Iterator) SuperClassIterator.createFrom(clazz);
		return classMemberIterator;
	}
}
