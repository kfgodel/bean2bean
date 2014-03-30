/**
 * Created on 11/01/2007 21:30:04 Copyright (C) 2007 Dario L. Garcia
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
 * Esta interfaz representa un repertorio de acciones que se realizaran al recibir distintos tipos
 * de mensajes. Un {@link ActionRepertoire} es tipicamente configurado para tratar mesajes de un
 * tipo con un tipo particular de {@link MessageHandler}. Luego se le van pasando distintos tipos de
 * mensaje y reaccionara acorde a su configuracion y al tipo de mensaje recibido.
 * 
 * @version 1.0
 * @since 11/01/2007
 * @author D. Garcia
 */
public interface ActionRepertoire {

	/**
	 * Registra un handler de mensajes para que trate el tipo de mensajes recibido
	 * 
	 * @param <T>
	 *            Tipo de mensaje que se esta registrando
	 * @param messageType
	 *            Tipo de mensajes a tratar
	 * @param typeHandler
	 *            Tratador de los mensajes del tipo especificado
	 */
	public <T> void doFor(Class<T> messageType, MessageHandler<T> typeHandler);

	/**
	 * Devuelve el handler de mensajes correspondiente al tipo de mensaje pasado
	 * 
	 * @param <T>
	 *            Tipo de mensaje a tratar
	 * @param messageType
	 *            Clase del tipo de mensaje que se debe tratar
	 * @return Handler del mensaje previamente configurado o null si no existe ninguno
	 */
	public <T> MessageHandler<T> getHandlerFor(Class<? extends T> messageType);

}
