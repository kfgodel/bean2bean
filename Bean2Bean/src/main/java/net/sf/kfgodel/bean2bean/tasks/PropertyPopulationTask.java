/**
 * Created on 23/12/2007 03:26:52 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.tasks;

import net.sf.kfgodel.bean2bean.conversion.TypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.BadMappingException;
import net.sf.kfgodel.bean2bean.population.instructions.PopulationInstruction;
import net.sf.kfgodel.tasks.Task;

/**
 * Esta clase representa una tarea de populacion de un atributo
 * 
 * @version 1.0
 * @since 23/12/2007
 * @author D. Garcia
 */
public class PropertyPopulationTask implements Task<Object> {

	/**
	 * Objeto en el que se asignara el valor
	 */
	private Object destination;
	/**
	 * Instruccion que sabe como realizar la populacion
	 */
	private PopulationInstruction instruction;
	/**
	 * Objeto del que se tomara el valor a popular
	 */
	private Object source;

	/**
	 * Conversor a utilizar en las asignaciones
	 */
	private TypeConverter typeConverter;

	/**
	 * @see net.sf.kfgodel.tasks.Task#cycleDetected()
	 */
	public void cycleDetected() {
		throw new BadMappingException("Property population cycle detected! Cannot populate same property twice!"
				+ this.toString());
	}

	/**
	 * @see net.sf.kfgodel.tasks.Task#endTask()
	 */
	public void endTask() {
	}

	public Object getDestination() {
		return destination;
	}

	public PopulationInstruction getInstruction() {
		return instruction;
	}

	/**
	 * @see net.sf.kfgodel.tasks.Task#getNextSubTask()
	 */
	public Task<Object> getNextSubTask() {
		return null;
	}

	/**
	 * @see net.sf.kfgodel.tasks.Task#getResult()
	 */
	public Object getResult() {
		return null;
	}

	public Object getSource() {
		return source;
	}

	private TypeConverter getTypeConverter() {
		return typeConverter;
	}

	/**
	 * @see net.sf.kfgodel.tasks.Task#hasPendingSubTasks()
	 */
	public boolean hasPendingSubTasks() {
		return false;
	}

	/**
	 * @see net.sf.kfgodel.tasks.Task#prepareTask()
	 */
	public void prepareTask() {
		this.getInstruction().applyOn(this.getSource(), this.getDestination(), this.getTypeConverter());
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" (");
		builder.append(this.getSource());
		builder.append(" -> ");
		builder.append(this.getInstruction());
		builder.append(" -> ");
		builder.append(this.getDestination());
		builder.append(")");
		return builder.toString();
	}

	public static PropertyPopulationTask createFor(Object source, PopulationInstruction instruction,
			Object destination, TypeConverter converter) {
		PropertyPopulationTask task = new PropertyPopulationTask();
		task.source = source;
		task.instruction = instruction;
		task.destination = destination;
		task.typeConverter = converter;
		return task;
	}

}
