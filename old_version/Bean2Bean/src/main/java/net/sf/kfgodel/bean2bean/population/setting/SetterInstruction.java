/**
 * Created on: 15/01/2009 15:04:11 by: Dario L. Garcia
 * 
 * * <a rel="license" href="http://creativecommons.oimport
 * net.sf.kfgodel.bean2bean.interpreters.MissingPropertyException; ="border-width:0"
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
package net.sf.kfgodel.bean2bean.population.setting;

import net.sf.kfgodel.bean2bean.exceptions.MissingPropertyException;

/**
 * Esta interfaz representa la instrucción de como asignar el valor en la propiedad destino
 * 
 * @author D.Garcia
 * @since 15/01/2009
 */
public interface SetterInstruction {

	/**
	 * Aplica esta instruccion sobre el objeto destino con el valor a asignar
	 * 
	 * @param destination
	 *            Objeto sobre el que se realizara la asignacion
	 * @param convertedValue
	 *            Valor a asignar
	 */
	void applyOn(Object destination, Object convertedValue) throws MissingPropertyException;

	/**
	 * Expresion que indica la expresion original utilizada para generar esta instruccion
	 * 
	 * @return El string utilizado para generar esta instrucccion
	 */
	String getOriginalExpression();

}
