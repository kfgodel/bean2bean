/**
 * Created on: 12/01/2009 13:33:24 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.interpreters;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.CopyTo;
import net.sf.kfgodel.bean2bean.exceptions.MissingDependencyException;
import net.sf.kfgodel.bean2bean.interpreters.groovy.GroovyConstants;
import net.sf.kfgodel.bean2bean.interpreters.groovy.GroovyExpressionInterpreter;
import net.sf.kfgodel.bean2bean.interpreters.natives.ReflectionExpressionInterpreter;
import net.sf.kfgodel.bean2bean.interpreters.ognl.OgnlConstants;
import net.sf.kfgodel.bean2bean.interpreters.ognl.OgnlExpressionInterpreter;

/**
 * This enum declares all the available languages to parse expressions in annotations {@link CopyTo}
 * and {@link CopyFrom}. Each language has its weak an strong points.<br>
 * 
 * 
 * @author D.Garcia
 */
public enum InterpreterType {
	/**
	 * Uses OGNL [http://www.ognl.org/] to parse expressiones. See {@link OgnlConstants} to access
	 * variables in the context
	 */
	OGNL {
		@Override
		public ExpressionInterpreter getInterpreter() {
			try {
				return new OgnlExpressionInterpreter();
			} catch (final NoClassDefFoundError e) {
				throw new MissingDependencyException(
						"Cannot create OGNL interpreter. Is OGNL dependency in the project?", e);
			}
		}
	},
	/**
	 * Uses Groovy [http://groovy.codehaus.org/] to parse expressions. See {@link GroovyConstants }
	 * to access variables in the context
	 */
	GROOVY {
		@Override
		public ExpressionInterpreter getInterpreter() {
			try {
				return GroovyExpressionInterpreter.create();
			} catch (final NoClassDefFoundError e) {
				throw new MissingDependencyException(
						"Cannot create Groovy interpreter. Is Groovy dependency in the project?", e);
			}
		}

		@Override
		public void initialize() {
			// Para inicializar sólo basta con evaluar una expresión cualquiera
			final ExpressionInterpreter interpreter = getInterpreter();
			interpreter.evaluate("this");
		}
	},
	/**
	 * Uses java reflection {@link Field}s and {@link Method} objetcs to get the value and make
	 * assignments. It only accepts expression that representa a sequence of fields or properties
	 * ("field1.field2.field3"). You loose laguage expressivity but you gain processing speed and
	 * cut third party dependencies
	 */
	REFLECTION {
		/**
		 * @see net.sf.kfgodel.bean2bean.interpreters.InterpreterType#getInterpreter()
		 */
		@Override
		public ExpressionInterpreter getInterpreter() {
			return ReflectionExpressionInterpreter.create();
		}
	};

	/**
	 * Devuelve un interprete de las expresiones que puede ser utilizado para evaluar Strings
	 * utilizando el lenguaje que representa este tipo de interprete
	 * 
	 * @return El interprete de las expresiones
	 */
	public abstract ExpressionInterpreter getInterpreter();

	/**
	 * Inicializa este interprete de manera que su estado esté preparado para evaluar las
	 * expresiones rápidamente
	 */
	public void initialize() {
	}
}
