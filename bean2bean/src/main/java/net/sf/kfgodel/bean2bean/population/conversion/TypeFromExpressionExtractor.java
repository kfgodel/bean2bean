/**
 * Created on: 15/01/2009 19:25:17 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.population.conversion;

import java.lang.reflect.Type;

import net.sf.kfgodel.bean2bean.exceptions.BadMappingException;
import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;
import net.sf.kfgodel.bean2bean.population.instructions.ScriptedInstruction;


/**
 * Esta clase representa un extractor del tipo esperado que se basa en una expresion de scripting
 * 
 * @author D.Garcia
 * @since 15/01/2009
 */
public class TypeFromExpressionExtractor implements ExpectedTypeExtractor {

	private ScriptedInstruction extractorInstruction;

	public ScriptedInstruction getExtractorInstruction() {
		return extractorInstruction;
	}

	public void setExtractorInstruction(ScriptedInstruction extractorInstruction) {
		this.extractorInstruction = extractorInstruction;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.population.conversion.ExpectedTypeExtractor#extractExpectedTypeFrom(java.lang.Object)
	 */
	public Type extractExpectedTypeFrom(Object destination) {
		Object resultingType = getExtractorInstruction().applyOn(destination);
		if (!(resultingType instanceof Type)) {
			throw new BadMappingException("Evaluated expression[" + getExtractorInstruction().getOriginalExpression()
					+ "] didn't return a valid " + Type.class.getName() + ": " + resultingType);
		}
		return (Type) resultingType;
	}

	public static TypeFromExpressionExtractor create(String expression, InterpreterType interpreterType) {
		TypeFromExpressionExtractor extractor = new TypeFromExpressionExtractor();
		ScriptedInstruction instruction = ScriptedInstruction.createFor(expression, interpreterType);
		extractor.setExtractorInstruction(instruction);
		return extractor;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append("[");
		builder.append(this.getExtractorInstruction());
		builder.append("]");
		return builder.toString();
	}

}
