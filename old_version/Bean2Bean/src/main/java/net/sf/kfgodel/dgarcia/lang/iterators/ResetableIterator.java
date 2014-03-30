/**
 * Created on 13/01/2007 23:39:45 Copyright (C) 2007 Dario L. Garcia
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
 * Based on a work at <a xmlns:dc="http:/import java.util.Iterator;

import net.sf.kfgodel.dgarcia.lang.extensions.Resetable;
ean2bean"
 * rel="dc:source">bean2bean.svn.sourceforge.net</a>
 */
package net.sf.kfgodel.dgarcia.lang.iterators;

import java.util.Iterator;

import net.sf.kfgodel.dgarcia.lang.extensions.Resetable;


/**
 * Esta interfaz representa un objeto que puede revertir su estado al momento de su creacion.
 * Mediante esta interfaz pueden reutilizarse ciertos objetos en vez de crear nuevos
 * 
 * @version 1.0
 * @since 13/01/2007
 * @author D. Garcia
 */
public interface ResetableIterator<T> extends Iterator<T>, Resetable {
}
