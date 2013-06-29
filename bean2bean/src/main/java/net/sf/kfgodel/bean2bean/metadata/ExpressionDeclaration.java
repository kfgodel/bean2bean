/**
 * Created on: 15/02/2010 23:51:07 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.metadata;

import net.sf.kfgodel.bean2bean.annotations.Expression;
import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;

/**
 * Esta clase representa una expresion en un lenguaje interpretado, declarada por el usuario
 * 
 * @author D. Garcia
 */
public class ExpressionDeclaration {
	private String expressionValue;
	private InterpreterType interpreterType;

	public String getExpressionValue() {
		return expressionValue;
	}

	public void setExpressionValue(String expressionValue) {
		this.expressionValue = expressionValue;
	}

	public InterpreterType getInterpreterType() {
		return interpreterType;
	}

	public void setInterpreterType(InterpreterType interpreterType) {
		this.interpreterType = interpreterType;
	}

	/**
	 * Constructor minimo
	 */
	public static ExpressionDeclaration create(String expresstion, InterpreterType interpreterType) {
		ExpressionDeclaration declaration = new ExpressionDeclaration();
		declaration.setExpressionValue(expresstion);
		declaration.setInterpreterType(interpreterType);
		return declaration;
	}

	/**
	 * Constructor que facilita la creacion a partir de un annotation
	 */
	public static ExpressionDeclaration createFrom(Expression expectedType) {
		String value = expectedType.value();
		InterpreterType interpreter = expectedType.interpreter();
		ExpressionDeclaration declaration = create(value, interpreter);
		return declaration;
	}

	/**
	 * Indica si esta declaracion contiene una expresion vacia
	 * 
	 * @return true si la expresion no puede usarse como tal
	 */
	public boolean isEmpty() {
		return "".equals(expressionValue);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		builder.append("[");
		builder.append(getInterpreterType());
		builder.append(": \"");
		builder.append(getExpressionValue());
		builder.append("\"]");
		return builder.toString();
	}
}
