/**
 * Created on 11/01/2007 21:37:52 Copyright (C) 2007 Dario L. Garcia
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

import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase es la implementacion default de un {@link ActionRepertoire}
 * 
 * @version 1.0
 * @since 11/01/2007
 * @author D. Garcia
 */
public class DefaultActionRepertoire implements ActionRepertoire {

	/**
	 * Acciones a realizar por cada tipo de mensaje
	 */
	private final Map<Class<?>, MessageHandler<?>> handlers = new HashMap<Class<?>, MessageHandler<?>>();

	/**
	 * @see net.sf.kfgodel.dgarcia.comm.handlers.ActionRepertoire#doFor(java.lang.Class,
	 *      net.sf.kfgodel.dgarcia.comm.handlers.MessageHandler)
	 */
	public <T> void doFor(Class<T> messageType, MessageHandler<T> typeHandler) {
		handlers.put(messageType, typeHandler);
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.comm.handlers.ActionRepertoire#getHandlerFor(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public <T> MessageHandler<T> getHandlerFor(Class<? extends T> messageType) {
		MessageHandler<T> handler = (MessageHandler<T>) handlers.get(messageType);
		return handler;
	}

}
