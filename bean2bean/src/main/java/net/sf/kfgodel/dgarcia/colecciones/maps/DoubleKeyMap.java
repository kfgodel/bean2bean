/**
 * 30/10/2005 00:25:30
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
package net.sf.kfgodel.dgarcia.colecciones.maps;

import java.util.Map;

/**
 * @author D. Garcia
 * 
 *         Esta interfaz define los metodos necesarios para tener un map indizado por dos keys. Para
 *         obtener un valor se debe especificar un par de keys que sera utilizado para acceder al
 *         primer y segundo map donde se encuentran almacenados los valores buscados.
 * @param <K>
 *            Tipo de los objetos utilizados como primera key
 * @param <S>
 *            Tipo de los objetos utilizados como segunda key
 * @param <V>
 *            Tipo de los objetos utilizados como value
 * 
 *            TODO exteder esta interfaz con los metodos faltantes
 */
public interface DoubleKeyMap<K, S, V> extends Map<K, Map<S, V>> {

	/**
	 * Obtiene el elemento guardado en la key, subKey indicados devuelve null si no hay ninguno
	 * guardado
	 * 
	 * @param key
	 *            Key primaria para guardar los objetos
	 * @param subKey
	 *            Key secundaria
	 * @return EL objeto guardado, o null si no habia ninguno
	 */
	public V get(K key, S subKey);

	/**
	 * Guarda el objeto indicado en la key y subKey indicados
	 * 
	 * @param key
	 *            Key primaria
	 * @param subKey
	 *            Key secundaria
	 * @param value
	 *            Valor a almacenar
	 * @return El valor anterior o null si no habia ninguno
	 */
	public V put(K key, S subKey, V value);

	/**
	 * Elimina el valor del mapa indicado por la key/subkey
	 * 
	 * @param key
	 *            Key primaria
	 * @param subKey
	 *            Key secundaria
	 * @return el valor borrado o null si no habia ninguno
	 */
	public V remove(K key, S subKey);
}
