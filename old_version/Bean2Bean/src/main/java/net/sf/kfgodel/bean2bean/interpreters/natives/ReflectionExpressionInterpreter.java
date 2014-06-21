/**
 * Created on: 21/02/2010 20:27:52 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.interpreters.natives;

import java.lang.reflect.Field;

import net.sf.kfgodel.bean2bean.exceptions.AttributeException;
import net.sf.kfgodel.bean2bean.exceptions.BadMappingException;
import net.sf.kfgodel.bean2bean.exceptions.FailedAssumptionException;
import net.sf.kfgodel.bean2bean.exceptions.MissingPropertyException;
import net.sf.kfgodel.bean2bean.instantiation.ObjectFactory;
import net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter;

/**
 * This class parses field sequence expressions that can be used to get or set value un a call
 * sequence to Java {@link Field} objets
 * 
 * @author D. Garcia
 */
public class ReflectionExpressionInterpreter implements ExpressionInterpreter {

	public static ReflectionExpressionInterpreter create() {
		final ReflectionExpressionInterpreter interpreter = new ReflectionExpressionInterpreter();
		return interpreter;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#evaluate(java.lang.String)
	 */
	public Object evaluate(final String expression) {
		throw new UnsupportedOperationException(this.getClass().getSimpleName() + " doesn't evaluate Strings");
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#evaluateGetterOn(java.lang.Object,
	 *      java.lang.Object, java.lang.Object)
	 */
	public Object evaluateGetterOn(final Object source, final Object storedExpression, final Object context)
			throws MissingPropertyException, AttributeException {
		PropertyChain expression;
		try {
			expression = (PropertyChain) storedExpression;
		} catch (final ClassCastException e) {
			throw new FailedAssumptionException("Precompiled expression[" + storedExpression
					+ "] for reflection interpreter should be a " + PropertyChain.class.getSimpleName(), e);
		}
		final Object currentValue = expression.getValueFrom(source);
		return currentValue;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#generateSetterContextFor(java.lang.Object,
	 *      java.lang.Object)
	 */
	public Object generateSetterContextFor(final Object destination, final Object value) {
		return null;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#generateGetterContextFrom(java.lang.Object)
	 */
	public Object generateGetterContextFrom(final Object sourceObject) {
		return null;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#makeAssignmentOn(java.lang.Object,
	 *      java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void makeAssignmentOn(final Object destination, final Object storedExpression, final Object context,
			final Object value) throws MissingPropertyException, AttributeException {
		// It should be the instance we created
		PropertyChain expression;
		try {
			expression = (PropertyChain) storedExpression;
		} catch (final ClassCastException e) {
			throw new FailedAssumptionException("Precompiled expression[" + storedExpression
					+ "] for reflection interpreter should be a " + PropertyChain.class.getSimpleName(), e);
		}
		expression.setValueOn(destination, value);
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#precompile(String,
	 *      ObjectFactory)
	 */
	public Object precompile(final String expression, final ObjectFactory objectFactory,
			final boolean canCreateMissingProperties) {
		PropertyChain compiled;
		try {
			compiled = PropertyChain.create(expression, objectFactory, canCreateMissingProperties);
		} catch (final IllegalArgumentException e) {
			throw new BadMappingException("Reflection interpreter cannot parse expression[" + expression
					+ "], it should be a property chain", e);
		}
		return compiled;
	}
}
