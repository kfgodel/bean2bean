/**
 * Created on 10/01/2007 22:40:52 Copyright (C) 2007 Dario L. Garcia
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
 * Esta clase representa un mensaje de error para el programador en el que se notifica de una
 * ejecucion erronea. Una ejecucion que no deberia haber sucedido si todo se hubiese hecho bien
 * 
 * @version 1.0
 * @since 10/01/2007
 * @author D. Garcia
 */
public class ErroneousExecution extends AbstractProgrammerMessage {
	/**
	 * Texto que describe el error de la ejecucion
	 */
	private String errorDescription;

	/**
	 * Tipificacion del error ocurrido
	 */
	private ErrorType situationType;

	/**
	 * Excepcion que contiene el stackStrace
	 */
	private Exception contextStack;

	/**
	 * @return Obtiene el mensaje que describe el error
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Envia un nuevo mensaje de este tipo al programador informando acerca de la situacion
	 * producida. Por lo general esto significa paralizar el programa y mostrar el error por
	 * pantalla mediante una {@link RuntimeException}
	 * 
	 * @param description
	 *            Descripcion del error
	 * @param situationType
	 *            Tipo de situacion en la que se produjo el error
	 */
	public static void hasHappened(String description, ErrorType situationType) {
		ErroneousExecution newMessage = new ErroneousExecution();
		newMessage.errorDescription = description;
		newMessage.situationType = situationType;
		newMessage.contextStack = new RuntimeException("Context:");
		newMessage.sendToProgrammer();
	}

	/**
	 * @return Instancia que tifica el error
	 */
	public ErrorType getSituationType() {
		return situationType;
	}

	/**
	 * Establece el tipo de error producido
	 * 
	 * @param errorType
	 */
	public void setSituationType(ErrorType errorType) {
		this.situationType = errorType;
	}

	/**
	 * @return Devuelve una excepcion que tiene capturado el stack del momento en que se produjo el
	 *         error
	 */
	public Exception getContextStack() {
		return contextStack;
	}
}
