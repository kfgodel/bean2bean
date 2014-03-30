/**
 * Created on: 12/01/2009 14:52:54 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.interpreters.ognl;

import java.util.HashMap;
import java.util.Map;

import net.sf.kfgodel.bean2bean.exceptions.BadMappingException;
import net.sf.kfgodel.bean2bean.exceptions.MissingPropertyException;
import net.sf.kfgodel.bean2bean.instantiation.ObjectFactory;
import net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;
import ognl.NoSuchPropertyException;
import ognl.Ognl;
import ognl.OgnlException;

/**
 * Esta clase es un interprete de expresiones utilizando el lenguaje de OGNL
 * 
 * @author D.Garcia
 */
public class OgnlExpressionInterpreter implements ExpressionInterpreter {

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#precompile(String,
	 *      ObjectFactory)
	 */
	public Object precompile(final String expression, final ObjectFactory objectFactory,
			final boolean canCreateMissingProperties) {
		try {
			return Ognl.parseExpression(expression);
		} catch (final OgnlException e) {
			throw new BadMappingException("Expression[\"" + expression + "\"] is not a valid OGNL expression", e);
		}
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#generateGetterContextFrom(java.lang.Object)
	 */
	public Object generateGetterContextFrom(final Object sourceObject) {
		final Map<String, Object> contexto = new HashMap<String, Object>();
		contexto.put(OgnlConstants.OBJETO_ORIGEN, sourceObject);
		return contexto;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#evaluateGetterOn(java.lang.Object,
	 *      java.lang.Object, java.lang.Object)
	 */
	public Object evaluateGetterOn(final Object source, final Object expression, final Object context)
			throws MissingPropertyException {
		try {
			@SuppressWarnings("unchecked")
			final Map<String, Object> contexto = (Map) context;
			final Object value = Ognl.getValue(expression, contexto, source);
			return value;
		} catch (final NoSuchPropertyException e) {
			throw new MissingPropertyException("OGNL couldn't access the property [" + e.getMessage()
					+ "]. Only public properties are available to OGNL", e);
		} catch (final OgnlException e) {
			throw new BadMappingException("OGNL error with expression[" + expression + "] on object[" + source
					+ "], context:[" + context + "]", e);
		}
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#evaluate(java.lang.String)
	 */
	public Object evaluate(final String expression) {
		try {
			final Object value = Ognl.getValue(expression, null);
			return value;
		} catch (final OgnlException e) {
			throw new BadMappingException("OGNL error evaluating expression[" + expression + "]", e);
		}
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#generateSetterContextFor(java.lang.Object,
	 *      java.lang.Object)
	 */
	public Object generateSetterContextFor(final Object destination, final Object value) {
		final Map<String, Object> contexto = new HashMap<String, Object>();
		contexto.put(OgnlConstants.OBJETO_DESTINO, destination);
		contexto.put(OgnlConstants.VALOR, value);
		return contexto;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.interpreters.ExpressionInterpreter#makeAssignmentOn(java.lang.Object,
	 *      java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void makeAssignmentOn(final Object destination, final Object expression, final Object context,
			final Object value) {
		try {
			@SuppressWarnings("unchecked")
			final Map<String, Object> contexto = (Map) context;
			if (ReflectionUtils.isPropertyChain(expression.toString())) {
				// Si es un path de propiedades, se puede asignar
				Ognl.setValue(expression, contexto, destination, value);
			} else {
				// Si no, solo se evalua la exresion
				Ognl.getValue(expression, contexto, destination);
			}
		} catch (final NoSuchPropertyException e) {
			throw new MissingPropertyException("OGNL could not find the property", e);
		} catch (final OgnlException e) {
			throw new BadMappingException("OGNL error with expression[" + expression + "] on object[" + destination
					+ "] and value[" + value + "], context:[" + context + "]", e);
		}
	}
}
