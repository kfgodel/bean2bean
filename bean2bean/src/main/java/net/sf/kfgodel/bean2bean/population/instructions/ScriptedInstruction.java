/**
 * Created on: 15/01/2009 16:25:02 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.population.instructions;

import net.sf.kfgodel.bean2bean.exceptions.BadMappingException;
import net.sf.kfgodel.bean2bean.exceptions.MissingPropertyException;
import net.sf.kfgodel.bean2bean.instantiation.ObjectFactory;
import net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter;
import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;
import net.sf.kfgodel.bean2bean.metadata.ExpressionDeclaration;
import net.sf.kfgodel.bean2bean.population.getting.GetterInstruction;
import net.sf.kfgodel.bean2bean.population.setting.SetterInstruction;

/**
 * Esta clase representa una instruccion de obtención del valor desde un objeto utilizando una
 * expression de un lenguaje de scripting
 * 
 * @author D.Garcia
 * @since 15/01/2009
 */
public class ScriptedInstruction implements GetterInstruction, SetterInstruction {

	private Object compiledExpression;
	private String originalExpression;
	private ExpressionInterpreter interpreter;
	private InterpreterType interpreterType;
	/**
	 * Factory to create missing instances
	 */
	private ObjectFactory objectFactory;

	public String getOriginalExpression() {
		return originalExpression;
	}

	public void setOriginalExpression(String originalExpression) {
		this.originalExpression = originalExpression;
	}

	public InterpreterType getInterpreterType() {
		return interpreterType;
	}

	public void setInterpreterType(InterpreterType interpreterType) {
		this.interpreterType = interpreterType;
	}

	public Object getCompiledExpression() {
		if (compiledExpression == null) {
			compiledExpression = getInterpreter().precompile(getOriginalExpression(), objectFactory);
		}
		return compiledExpression;
	}

	public ExpressionInterpreter getInterpreter() {
		if (interpreter == null) {
			interpreter = getInterpreterType().getInterpreter();
		}
		return interpreter;
	}

	public static ScriptedInstruction createFor(String expression, InterpreterType interpreterType) {
		ScriptedInstruction instruction = new ScriptedInstruction();
		instruction.setInterpreterType(interpreterType);
		instruction.setOriginalExpression(expression);
		return instruction;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.population.getting.GetterInstruction#applyOn(java.lang.Object)
	 */
	public Object applyOn(Object source) throws MissingPropertyException {
		Object context = this.getInterpreter().generateGetterContextFrom(source);
		Object value;
		try {
			Object expression = getCompiledExpression();
			value = this.getInterpreter().evaluateGetterOn(source, expression, context);
		}
		catch (MissingPropertyException e) {
			// Esta no la queremos catchear
			throw e;
		}
		catch (Exception e) {
			throw new BadMappingException("Couldn't get value from[" + source + "] using interpreter["
					+ getInterpreterType() + "]. Catched a " + e.getClass().getName() + ": " + e.getMessage(), e);
		}
		return value;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.population.setting.SetterInstruction#applyOn(java.lang.Object,
	 *      java.lang.Object)
	 */
	public void applyOn(Object destination, Object convertedValue) throws MissingPropertyException {
		Object context = this.getInterpreter().generateSetterContextFor(destination, convertedValue);
		try {
			this.getInterpreter().makeAssignmentOn(destination, getCompiledExpression(), context, convertedValue);
		}
		catch (MissingPropertyException e) {
			// Esta no la queremos catchear
			throw e;
		}
		catch (Exception e) {
			throw new BadMappingException("Couldn't assign value[" + convertedValue + "] on object[" + destination
					+ "] using interpreter[" + getInterpreterType() + "]. Catched a " + e.getClass().getName() + ": "
					+ e.getMessage(), e);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append("[ ");
		builder.append(this.getInterpreterType());
		builder.append(":\"");
		builder.append(this.getOriginalExpression());
		builder.append("\" ]");
		return builder.toString();
	}

	/**
	 * Metodo que facilita la creacion de la instruccion
	 * 
	 * @param sourceExpression
	 *            Expresion utilizada como base
	 * @return La instruccion creada
	 */
	public static ScriptedInstruction createFor(ExpressionDeclaration sourceExpression, ObjectFactory objectFactory) {
		String expressionValue = sourceExpression.getExpressionValue();
		InterpreterType interpreterType2 = sourceExpression.getInterpreterType();
		ScriptedInstruction instruction = createFor(expressionValue, interpreterType2);
		instruction.objectFactory = objectFactory;
		return instruction;
	}
}
