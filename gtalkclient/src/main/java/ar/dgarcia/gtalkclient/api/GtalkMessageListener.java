/**
 * 28/12/2011 20:12:01 Copyright (C) 2011 Darío L. García
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

/**
 * Esta clase representa el listener para atender los mensajes recibidos en una conexión
 * 
 * @author D. García
 */
public interface GtalkMessageListener {

	/**
	 * Invocado al recibir un mensaje
	 * 
	 * @param sourceUser
	 *            El ID de mail del usuario que lo envió
	 * @param message
	 *            El mensaje enviado
	 */
	public void onMessageReceivedFrom(String sourceUser, String message);
}
