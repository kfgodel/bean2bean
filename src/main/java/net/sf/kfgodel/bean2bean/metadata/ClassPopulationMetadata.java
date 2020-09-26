/**
 * Created on: 01/03/2010 20:10:01 by: Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.population.instructions.PopulationInstruction;

import java.util.ArrayList;
import java.util.List;


/**
 * This class represents the existing metadata declared by the user to map the properties of a class
 * 
 * @author D. Garcia
 */
public class ClassPopulationMetadata {

	private Class<?> annotatedClass;

	private List<PopulationInstruction> populationInstructions;

	public Class<?> getAnnotatedClass() {
		return annotatedClass;
	}

	public void setAnnotatedClass(Class<?> annotatedClass) {
		this.annotatedClass = annotatedClass;
	}

	public List<PopulationInstruction> getPopulationInstructions() {
		if (populationInstructions == null) {
			populationInstructions = new ArrayList<PopulationInstruction>();
		}
		return populationInstructions;
	}

	public void setPopulationInstructions(List<PopulationInstruction> populationInstructions) {
		this.populationInstructions = populationInstructions;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		builder.append("[ class:");
		builder.append(getAnnotatedClass());
		builder.append(" with ");
		builder.append(getPopulationInstructions().size());
		builder.append(" instructions: ");
		builder.append(getPopulationInstructions());
		builder.append(" ]");
		return builder.toString();
	}

	/**
	 * Creates the metadata for designated class
	 * 
	 * @param metadataClass
	 *            Class for which the metadata applies
	 * @param instructions
	 *            Instructions to populate the properties of the class
	 * @return The population metadata created
	 */
	public static ClassPopulationMetadata create(Class<?> metadataClass, List<PopulationInstruction> instructions) {
		ClassPopulationMetadata metada = new ClassPopulationMetadata();
		metada.annotatedClass = metadataClass;
		metada.populationInstructions = instructions;
		return metada;
	}
}
