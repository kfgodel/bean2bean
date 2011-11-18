/**
 * Created on: 13/01/2009 01:12:43 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.interpreters.groovy;

import net.sf.kfgodel.bean2bean.exceptions.MissingPropertyException;
import net.sf.kfgodel.bean2bean.instantiation.ObjectFactory;
import net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

/**
 * Esta clase representa un interprete de groovy
 * 
 * @author D.Garcia
 */
public class GroovyExpressionInterpreter implements ExpressionInterpreter {

	private GroovyShell groovyShell;

	public GroovyShell getGroovyShell() {
		return groovyShell;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#evaluate(java.lang.String)
	 */
	public Object evaluate(String expression) {
		Object result = this.getGroovyShell().evaluate(expression);
		return result;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#evaluateGetterOn(java.lang.Object,
	 *      java.lang.Object, java.lang.Object)
	 */
	public Object evaluateGetterOn(Object source, Object expression, Object context) throws MissingPropertyException {
		Script script = (Script) expression;
		Binding binding = (Binding) context;
		script.setBinding(binding);
		Object result;
		try {
			result = script.run();
		}
		catch (groovy.lang.MissingPropertyException e) {
			throw new MissingPropertyException("Groovy couldn't access the property", e);
		}
		return result;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#generateSetterContextFor(java.lang.Object,
	 *      java.lang.Object)
	 */
	public Object generateSetterContextFor(Object destination, Object value) {
		Binding binding = new Binding();
		binding.setVariable(GroovyConstants.OBJETO_DESTINO, destination);
		binding.setVariable(GroovyConstants.VALOR, value);
		binding.setVariable(GroovyConstants.OBJETO_ANFITRION, destination);
		return binding;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#generateGetterContextFrom(java.lang.Object)
	 */
	public Object generateGetterContextFrom(Object sourceObject) {
		Binding binding = new Binding();
		binding.setVariable(GroovyConstants.OBJETO_ORIGEN, sourceObject);
		binding.setVariable(GroovyConstants.OBJETO_ANFITRION, sourceObject);
		return binding;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#makeAssignmentOn(java.lang.Object,
	 *      java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void makeAssignmentOn(Object destination, Object expression, Object context, Object value) {
		Script script = (Script) expression;
		Binding binding = (Binding) context;
		script.setBinding(binding);
		try {
			script.run();
		}
		catch (groovy.lang.MissingPropertyException e) {
			throw new MissingPropertyException("Groovy couldn't finde the property", e);
		}
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#precompile(String,
	 *      ObjectFactory)
	 */
	public Object precompile(String expression, ObjectFactory objectFactory) {
		StringBuilder builder = new StringBuilder();
		builder.append("def ejecutable = {");
		builder.append(expression);
		builder.append("}; ");
		builder.append("ejecutable.delegate = ");
		builder.append(GroovyConstants.OBJETO_ANFITRION);
		builder.append(";");
		builder.append("ejecutable()");
		Script script = this.getGroovyShell().parse(builder.toString());
		return script;
	}

	public static GroovyExpressionInterpreter create() {
		GroovyExpressionInterpreter interpreter = new GroovyExpressionInterpreter();
		interpreter.groovyShell = new GroovyShell();
		return interpreter;
	}
}
