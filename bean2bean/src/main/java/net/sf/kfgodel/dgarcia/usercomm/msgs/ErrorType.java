/**
 * Created on 11/01/2007 00:07:37 Copyright (C) 2007 Dario L. Garcia
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
 * Este enum tipifica los tipos de errores mas comunes en la ejecucion del programa para poder ser
 * notificados al programador
 * 
 * @version 1.0
 * @since 11/01/2007
 * @author D. Garcia
 */
public enum ErrorType {
	/**
	 * Representa un flujo de ejecucion en el que se produce un estado invalido del sistema.
	 * Basicamente el estado del sistema impide la ejecucion en curso
	 */
	CONTRADICTORY_EXECUTION,

	/**
	 * Representa un flujo de ejecucion en el que se produce una situacion para la cual no se tiene
	 * definido un curso de accion (ya sea por extraña o imposible, o por falta de madurez en el
	 * codigo)
	 */
	UNHANDLED_SITUATION,

	/**
	 * Representa un flujo de ejecucion en el que se llego a un punto en el cual la configuracion no
	 * permite continuar correctamente. A diferencia de {@link #CONTRACT_VIOLATION} el error se debe
	 * a un error al configurar externamente un modulo. El error se encuentra en la utilizacion de
	 * la parte "abierta" de un modulo. Por ejemplo, valores invalidos en objetos de configuracion.
	 */
	BAD_CONFIGURATION,

	/**
	 * Representa la mala utilizacion de una porcion de c�digo (generalmente pasaje de parametros
	 * incorrectos para un metodo). A diferencia de {@link #BAD_CONFIGURATION}, en este caso el
	 * codigo que se "mal uso" no esta abierto a modificaciones, es un error l�gico del uso del
	 * c�digo interno a un m�dulo. Una falla que no respeta un contrato explicito o implicito del
	 * c�digo
	 */
	CONTRACT_VIOLATION
}
