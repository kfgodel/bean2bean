/**
 * 22/11/2005 02:13:24 Copyright (C) 2006 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.lang.closures;

/**
 * Una expresion es un bloque de codigo que puede ser evaluado en distintos objetos devolviendo un
 * valor resultado de la evaluacion
 * 
 * @param <T>
 *            Tipo del objeto usado como variable de entrada de esta expresion
 * @param <R>
 *            Tipo del objeto retornado
 * @version 1.0
 * @since 2006-03-23
 * @author D. Garcia
 */
public interface Expression<T, R> {
	/**
	 * Evalua esta expresion sobre el objeto pasado devolviendo el resultado
	 * 
	 * @param element
	 *            Objeto sobre el que evaluar esta instancia
	 * @return El resultado de la evaluacion o null si no se devolvio nada (null tambien puede ser
	 *         el resultado de la evaluacion)
	 */
	public R evaluateOn(T element);
}
