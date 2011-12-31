/**
 * 28/12/2011 19:50:06 Copyright (C) 2011 Darío L. García
 * 
 * <a rel="license" href="http://creativecommons.org/licenses/by/3.0/"><img
 * alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/by/3.0/88x31.png" /></a><br />
 * <span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/Text"
 * property="dct:title" rel="dct:type">Software</span> by <span
 * xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Darío García</span> is
 * licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/3.0/">Creative
 * Commons Attribution 3.0 Unported License</a>.
 */
package ar.dgarcia.gtalkclient.api;

import java.util.Set;

/**
 * Esta clase representa una conexión con un servidor de gtalk para establecer comunicaciones via
 * texto
 * 
 * @author D. García
 */
public interface GtalkConnection {

	/**
	 * Indica si esta conexión puede ser utilizada para la comunicación de mensajes
	 * 
	 * @return true si la conexión puede ser usada
	 */
	boolean isConnected();

	/**
	 * Cierra la conexión para devolver los recursos
	 */
	void close();

	/**
	 * Envía el mensaje indicado al usuario indicado por ID de mail
	 * 
	 * @param destinationUser
	 *            Identificador del usuario destino
	 * @param message
	 *            El mensaje a mandar
	 */
	void sendMessageTo(String destinationUser, String message);

	/**
	 * Devuelve una lista de los contactos del usuario de la conexión actual
	 * 
	 * @return la lista de contactos identificadas por mail
	 */
	Set<String> getContacts();

	/**
	 * Indica si el usuario bajo el ID pasado está conectado o no
	 * 
	 * @param otherUser
	 * @return true si el otro usuario esta conectado. False si no está conectado o no es contacto
	 *         de este
	 */
	boolean isContactPresent(String otherUser);

}
