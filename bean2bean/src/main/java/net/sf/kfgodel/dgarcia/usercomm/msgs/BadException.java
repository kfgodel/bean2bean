/**
 * Created on 09/01/2007 23:45:04 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.usercomm.msgs;

/**
 * Esta clase representa un mensaje de error para el programador en el que se informa la ocurrencia
 * de una excepcion
 * 
 * @version 1.0
 * @since 09/01/2007
 * @author D. Garcia
 */
public class BadException extends AbstractProgrammerMessage {

	private String contextualMessage;
	private Exception occuredException;
	private ErrorType situationType;

	/**
	 * @return Tipo de situacion en la que se produjo la excepcion
	 */
	public ErrorType getSituationType() {
		return situationType;
	}

	/**
	 * Establece el tipo de este error
	 * 
	 * @param situationType
	 */
	public void setSituationType(ErrorType situationType) {
		this.situationType = situationType;
	}

	/**
	 * @return Obtiene el texto que acompa�a la excepcion
	 */
	public String getContextualMessage() {
		return contextualMessage;
	}

	private void setContextualMessage(String contextualMessage) {
		this.contextualMessage = contextualMessage;
	}

	/**
	 * @return Obtiene la excepcion que causo este mensaje
	 */
	public Exception getOccuredException() {
		return occuredException;
	}

	private void setOccuredException(Exception occuredException) {
		this.occuredException = occuredException;
	}

	/**
	 * Crea una nueva instanciaEnvia un nuevo mensaje de este tipo al programador. Por lo general
	 * esto significa paralizar el programa y mostrar el error por pantalla mediante una
	 * {@link RuntimeException}
	 * 
	 * @param message
	 *            Texto que agrega informacion contextual
	 * @param exception
	 *            Excepcion original
	 * @param situationType
	 *            Tipo de situacion en la que se produce la excepcion
	 */
	public static void hasHappened(String message, Exception exception, ErrorType situationType) {
		BadException newMessage = new BadException();
		newMessage.setContextualMessage(message);
		newMessage.setOccuredException(exception);
		newMessage.setSituationType(situationType);
		newMessage.sendToProgrammer();
	}
}
