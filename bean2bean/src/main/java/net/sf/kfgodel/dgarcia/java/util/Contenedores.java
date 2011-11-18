/**
 * 30/07/2006 19:30:12 Copyright (C) 2006 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.java.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Esta clase presenta un conjunto de m�todos que facilitan el uso de las listas
 * 
 * @author D. Garcia
 */
public class Contenedores {

	/**
	 * Convierte un array de elementos en una lista con los mismos elementos en el mismo orden
	 * 
	 * @param <T>
	 *            Tipo de los elemetnos del array
	 * @param elements
	 *            Array de elementos
	 * @return Un ArrayList de elementos
	 */
	public static <T> ArrayList<T> toArrayList(T[] elements) {
		ArrayList<T> container = new ArrayList<T>(elements.length);
		for (T element : elements) {
			container.add(element);
		}
		return container;
	}

	/**
	 * Convierte un conjunto de elementos en una lista con el mismo orden que fueron agregados
	 * 
	 * @param <T>
	 *            Tipo de los elementos
	 * @param elements
	 *            Conjunto de elementos
	 * @return Un ArrayList con los elementos indicados
	 */
	public static <T> ArrayList<T> toArrayListWith(T... elements) {
		return toArrayList(elements);
	}

	/**
	 * Crea un lista que contenga los elementos del iterador pasado. Para crear la lista se iterara
	 * el iterador.<br>
	 * La implementacion de la lista es un {@link ArrayList}
	 * 
	 * @param <T>
	 *            Tipo de los elementos
	 * @param iterator
	 *            Iterador de los elementos de la lista
	 * @return La lista de los elementos
	 */
	public static <T> List<T> toList(Iterator<T> iterator) {
		ArrayList<T> lista = new ArrayList<T>();
		while (iterator.hasNext()) {
			lista.add(iterator.next());
		}
		return lista;
	}

	/**
	 * Genera una representacion String de los elementos pasados armando una cadena con el toString
	 * de cada uno, concatenando con un separador indicado
	 * 
	 * @param separator
	 *            Separador de ocurrencia toString de cada elemento
	 * @param elements
	 *            Elementos de los que se quiere obtener la representacion
	 * @return El string formado por la concatenacion de cada elemento y el separador entre ellos o
	 *         una cadena vacia si no hay elementos
	 */
	public static String toStringJoinedBy(String separator, Iterable<?> elements) {
		StringBuilder builder = new StringBuilder();
		Iterator<?> iterator = elements.iterator();
		while (iterator.hasNext()) {
			Object element = iterator.next();
			builder.append(String.valueOf(element));
			if (iterator.hasNext()) {
				builder.append(separator);
			}
		}
		return builder.toString();
	}
}
