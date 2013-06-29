/**
 * 11/12/2006 00:33:35 Copyright (C) 2006 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.lang.iterators.tree.treeorder;

import java.util.HashSet;

import net.sf.kfgodel.dgarcia.lang.closures.Condition;


/**
 * Esta clase representa una condicion que permite saber si una instancia ha sido repetida m�s de
 * una vez desde el momento de instanciacion de esta condicion. Esta condicion va registrando los
 * elementos por los que se le pregunta y permite saber cuando uno ha sido repetido
 * 
 * @author D. Garcia
 * @param <T>
 *            Tipo de elementos iterados
 */
public class OnlyOnceCondition<T> implements Condition<T> {

	/**
	 * Conjunto de elementos ya analizados por esta condicion que permite determinar si existe un
	 * repetido
	 */
	private final HashSet<T> alreadyDetected = new HashSet<T>();

	/**
	 * Indica si esta condicion es cumplida por el elemento pasado.
	 * 
	 * @param element
	 *            Elemento a verificar
	 * @return false si ya se verifico este elemento anteriormente
	 * @see net.sf.kfgodel.dgarcia.lang.closures.Condition#isMetBy(java.lang.Object)
	 */
	public boolean isMetBy(T element) {
		if (this.alreadyDetected.contains(element)) {
			return false;
		}
		this.alreadyDetected.add(element);
		return true;
	}

}
