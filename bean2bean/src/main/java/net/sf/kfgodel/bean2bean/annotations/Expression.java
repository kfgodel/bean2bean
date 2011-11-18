/**
 * Created on: 14/01/2009 18:06:10 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.annotations;

import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;

/**
 * Este annotation representa una expresión de scripting evaluable por un lenguaje, indicado como
 * interprete
 * 
 * @author D.Garcia
 */
public @interface Expression {
	/**
	 * Expresión a evaluar, definida en el lenguaje deseado
	 * 
	 * @return Una expresión evaluable por el lenguaje elegido
	 */
	String value();

	/**
	 * Define el interprete utilizado para evaluar la expresión.<br>
	 * El interprete define además los nombres de las variables que se deben utilizar para referirse
	 * a los objetos del contexto asociado a la ejecución de la expresion. Cada lenguaje tiene una
	 * clase asociada con constantes que indican los nombres de las variables utilizadas para
	 * referirse a los distintos objetos.<br>
	 * Por defecto el interprete es OGNL.
	 * 
	 * @return El interprete a utilizar para evaluar la expresion
	 */
	InterpreterType interpreter() default InterpreterType.OGNL;
}
