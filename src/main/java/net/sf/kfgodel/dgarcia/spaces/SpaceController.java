/**
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

import java.util.Iterator;

/**
 * 28/12/2006 22:44:19 Copyright (C) 2006 Dario L. Garcia
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

/**
 * Esta interfaz representa un controlador de espacio que registra las relaciones espaciales entre
 * los objetos. Es decir, que objeto se encuentra espacialmente contenido dentro de otro.
 * 
 * Este objeto es necesario ya que la relaciones espaciales no son internas ni del espacio
 * contenedor, ni del objeto contenido. Son emergentes de la relacion entre ambos. Por tal motivo no
 * pueden ser adjudicadas a ninguno de los dos.
 * 
 * 
 * @author D. Garcia
 */
public interface SpaceController {

	/**
	 * Devuelve el objeto que actua como contenedor del contenido pasado
	 * 
	 * @param contained
	 *            Objeto contenido del que quiere obtenerse su contenedor
	 * @return El Objeto que actua como contenedor del objeto contenido o null si no tiene
	 *         contenedor
	 */
	public Object getContainerOf(Object contained);

	/**
	 * Devuelve el conjunto de objetos que esta contenido en el objeto contenedor pasado
	 * 
	 * @param space
	 *            Objeto que actua como contenedor de los otros
	 * @return El conjunto de objetos contenidos (sin elementos si el espacio pasado no tiene
	 *         elementos contenidos)
	 */
	public Iterator<Object> getContainedIn(Object space);

	/**
	 * Establece una relacion de espacio (contenci�n) entre el objeto contenido, indicado, y el
	 * objeto contenedor que representa el espacio que lo contiene
	 * 
	 * @param contained
	 *            Objeto contenido en el otro
	 * @param container
	 *            Objeto contenedor
	 */
	public void setContainerOf(Object contained, Object container);

	/**
	 * Rompe la relacion de contencion entre las instancias
	 * 
	 * @param container
	 * @param contained
	 */
	public void removeContainedFrom(Object container, Object contained);
}
