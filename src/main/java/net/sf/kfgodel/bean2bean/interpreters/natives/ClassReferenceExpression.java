/**
 * Created on: 23/02/2010 09:49:07 by: Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.population.conversion.ExpectedTypeExtractor;

import java.lang.reflect.Type;


/**
 * This class represents a reference to a expected type
 * 
 * @author D. Garcia
 */
public class ClassReferenceExpression implements ExpectedTypeExtractor {

	private Class<?> referencedClass;

	/**
	 * @see net.sf.kfgodel.bean2bean.population.conversion.ExpectedTypeExtractor#extractExpectedTypeFrom(java.lang.Object)
	 */
	public Type extractExpectedTypeFrom(Object destination) {
		return referencedClass;
	}

	/**
	 * Tries to create a class reference from the complete class name
	 * 
	 * @param reference
	 *            A package and class name
	 * @return The class references pointing to the class or null if reference does not represents a
	 *         class
	 */
	public static ClassReferenceExpression parse(String reference) {
		Class<?> foundClass;
		try {
			foundClass = Class.forName(reference);
		}
		catch (ClassNotFoundException e) {
			// It's not a valid reference
			return null;
		}

		ClassReferenceExpression classReference = new ClassReferenceExpression();
		classReference.referencedClass = foundClass;
		return classReference;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append("[ class: ");
		builder.append(referencedClass);
		builder.append(" ]");
		return builder.toString();
	}

}
