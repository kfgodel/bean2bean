/**
 * 29/12/2006 22:22:28 Copyright (C) 2006 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.spaces;

import net.sf.kfgodel.dgarcia.colecciones_space.maps.MultiValueMap;
import net.sf.kfgodel.dgarcia.colecciones_space.maps.impl.MultiValueHashMap;
import net.sf.kfgodel.dgarcia.lang.iterators_space.basic.EmptyIterator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Esta clase es la implementacion de un controlador del espacio que tiene un doble mapa para
 * registrar las intancias relacionadas
 * 
 * @author D. Garcia
 */
public class DefaultSpaceController implements SpaceController {

	/**
	 * Mapa que permite obtener el objeto contenedor de otro a partir de un objeto contenido
	 */
	private final Map<Object, Object> containers = new HashMap<Object, Object>();

	/**
	 * Mapa que permite obtener los objetos contenidos dentro de otro a partir del objeto contenedor
	 */
	private final MultiValueMap<Object, Object> containedInstances = new MultiValueHashMap<Object, Object>();

	/**
	 * @see net.sf.kfgodel.dgarcia.spaces.SpaceController#getContainedIn(java.lang.Object)
	 */
	public Iterator<Object> getContainedIn(Object space) {
		Collection<Object> contained = this.containedInstances.get(space);
		if (contained == null) {
			return EmptyIterator.getInstance();
		}
		return contained.iterator();
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.spaces.SpaceController#getContainerOf(java.lang.Object)
	 */
	public Object getContainerOf(Object contained) {
		return this.containers.get(contained);
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.spaces.SpaceController#setContainerOf(java.lang.Object,
	 *      java.lang.Object)
	 */
	public void setContainerOf(Object contained, Object container) {
		this.containers.put(contained, container);
		this.containedInstances.putValue(container, contained);
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.spaces.SpaceController#removeContainedFrom(java.lang.Object,
	 *      java.lang.Object)
	 */
	public void removeContainedFrom(Object container, Object contained) {
		this.containedInstances.removeValue(container, contained);
		this.containers.remove(contained);
	}

}
