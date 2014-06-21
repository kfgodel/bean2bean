/**
 * 16/10/2005 00:13:20
 * 
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
package net.sf.kfgodel.dgarcia.colecciones_space.maps.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.kfgodel.dgarcia.colecciones_space.maps.MultiValueMap;

/**
 * @author D. Garcia
 * 
 *         Esta es un implementacion del
 *         {@link net.sf.kfgodel.dgarcia.colecciones_space.maps.MultiValueMap} usando un HashMap. Se
 *         debe indicar un creador de coleccion para almacenar internamente los values de cada key
 * @param <K>
 *            Tipo de los objetos usados como key
 * @param <V>
 *            Tipo de los objetos usados como values
 */
@SuppressWarnings("unused")
public class MultiValueHashMap<K, V> extends HashMap<K, Collection<V>> implements MultiValueMap<K, V> {

	/**
	 * @author D. Garcia Implementacion de creator que crea un ArrayList
	 * @param <V>
	 *            tipo de los objetos usados como value
	 */
	public static class ArrayListCreator<V> implements CollectionCreator<V> {
		/**
		 * @see net.sf.kfgodel.dgarcia.colecciones_space.maps.impl.MultiValueHashMap.CollectionCreator#createCollection()
		 */
		public Collection<V> createCollection() {
			return new ArrayList<V>();
		}
	}

	/**
	 * @author D. Garcia
	 * 
	 *         Esta interfaz define el creador de colecciones que se debe implementar para que el
	 *         mapa almacene los values de cada key
	 * @param <V>
	 *            Tipo de los objetos usados como value
	 */
	public interface CollectionCreator<V> {
		/**
		 * @return Crea una nueva coleccion para almacenar los values de una key
		 */
		public Collection<V> createCollection();
	}

	/**
	 * @author D. Garcia
	 * 
	 *         Implementacion de creator que crea un LinkedHashSet
	 * @param <V>
	 *            Tipo de los objetos usados como values
	 */
	public static class LinkedSetCreator<V> implements CollectionCreator<V> {
		/**
		 * @see net.sf.kfgodel.dgarcia.colecciones_space.maps.impl.MultiValueHashMap.CollectionCreator#createCollection()
		 */
		public Collection<V> createCollection() {
			return new LinkedHashSet<V>();
		}
	}

	/**
	 * Otorgado por la VM
	 */
	private static final long serialVersionUID = 6400627122514824305L;

	/**
	 * Creador de colecciones usadas para almacenar los multiples values de cada key
	 */
	private final CollectionCreator<V> collectionCreator;

	/**
	 * Constructor por defecto que crea colleciones Arraylist para los values
	 */
	public MultiValueHashMap() {
		this(new ArrayListCreator<V>());
	}

	/**
	 * Constructor basado en un collection creator para almacenar los values de cada key
	 * 
	 * @param collectionCreator
	 *            creador de colleciones
	 */
	public MultiValueHashMap(final CollectionCreator<V> collectionCreator) {
		this.collectionCreator = collectionCreator;
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.colecciones_space.maps.MultiValueMap#allValues()
	 */
	public Collection<V> allValues() {
		final ArrayList<V> todos = new ArrayList<V>();
		final Collection<Collection<V>> valores = this.values();
		for (final Collection<V> list : valores) {
			todos.addAll(list);
		}
		return todos;
	}

	/**
	 * @see java.util.HashMap#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(final Object value) {
		final Collection<Collection<V>> valores = this.values();
		for (final Collection<V> list : valores) {
			if (list.contains(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Agrega todos los objetos del mapa pasado bajo las key correspondientes
	 * 
	 * @param mapa
	 *            Mapa contenedor de las asociaciones
	 */
	public void putAllValues(final Map<? extends K, ? extends V> mapa) {
		for (final Entry<? extends K, ? extends V> entry : mapa.entrySet()) {
			this.putValue(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * @param key
	 *            Objeto bajo el que se guardara el value
	 * @param value
	 *            Objeto a guardar
	 * @return La lista de values de la key indicada
	 * @see java.util.Map
	 * @see net.sf.kfgodel.dgarcia.colecciones_space.maps.MultiValueMap#putValue(Object, Object)
	 */
	public Collection<V> putValue(final K key, final V value) {
		Collection<V> coleccion = this.get(key);
		if (coleccion == null) {
			coleccion = this.collectionCreator.createCollection();
			this.put(key, coleccion);
		}
		coleccion.add(value);
		return coleccion;
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.colecciones_space.maps.MultiValueMap#removeValue(Object, Object)
	 */
	public V removeValue(final K key, final V value) {
		final Collection<V> coleccion = this.get(key);
		if (coleccion == null) {
			return null;
		}
		if (!coleccion.remove(value)) {
			return null;
		}
		if (coleccion.size() == 0) {
			this.remove(key);
		}
		return value;
	}

	/**
	 * Indica la cantidad de values en este map
	 * 
	 * @return La cantidad de values
	 * @see java.util.HashMap#size()
	 */
	public int sizeValues() {
		int size = 0;
		for (final Collection<V> list : this.values()) {
			size += list.size();
		}
		return size;
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.colecciones_space.maps.MultiValueMap#getKeysOf(java.lang.Object)
	 */
	public Collection<K> getKeysOf(final V value) {
		final ArrayList<K> keys = new ArrayList<K>();
		final Iterator<Entry<K, Collection<V>>> entryIterator = this.entrySet().iterator();
		while (entryIterator.hasNext()) {
			final Entry<K, Collection<V>> entry = entryIterator.next();
			final Collection<V> valuesForKey = entry.getValue();
			if (valuesForKey.contains(value)) {
				keys.add(entry.getKey());
			}
		}
		return keys;
	}

}
