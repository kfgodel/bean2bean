/**
 * Created on 11/01/2007 21:23:51 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.comm.handlers;

/**
 * Esta interfaz representa una acciona ser realizada en el momento que se recibe un mensaje
 * 
 * @version 1.0
 * @since 11/01/2007
 * @author D. Garcia
 * @param <T>
 *            Tipo de mensaje concreto que permite manejar esta clase
 */
public interface MessageHandler<T> {

	/**
	 * Realiza las acciones correspondientes al tipo de mensaje
	 * 
	 * @param message
	 *            Instancia de mensaje de un tipo que sirve de disparador para las acciones a
	 *            realizar
	 */
	void handle(T message);

}
