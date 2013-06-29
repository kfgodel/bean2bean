/**
 * Created on 22/12/2007 23:56:17 Copyright (C) 2007 Dario L. Garcia
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

import java.util.Iterator;
import java.util.LinkedList;

import net.sf.kfgodel.bean2bean.conversion.TypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.BadMappingException;
import net.sf.kfgodel.bean2bean.metadata.ClassPopulationMetadata;
import net.sf.kfgodel.bean2bean.population.ClassPopulationMetadaExtractor;
import net.sf.kfgodel.bean2bean.population.PopulationType;
import net.sf.kfgodel.bean2bean.population.instructions.PopulationInstruction;
import net.sf.kfgodel.tasks.Task;


/**
 * Esta clase representa la populacion de un bean basandose siguiendo los annotations presentes en
 * la clase, destino u origen de los datos.
 * 
 * @version 1.0
 * @since 22/12/2007
 * @author D. Garcia
 * @param <T>
 *            Tipo del bean a popular
 */
public class BeanPopulationTask<T> implements Task<T> {
	/**
	 * Bean a popular
	 */
	private T populatedBean;
	/**
	 * Sub tareas de populacion de atributos
	 */
	private LinkedList<PropertyPopulationTask> populationTasks;
	/**
	 * Tipo de metadata de la populacion
	 */
	private PopulationType populationType;
	/**
	 * Bean con los datos a copiar
	 */
	private Object sourceBean;

	/**
	 * Conversor a utilizar para la populacion
	 */
	private TypeConverter typeConverter;

	/**
	 * @see net.sf.kfgodel.tasks.Task#cycleDetected()
	 */
	public void cycleDetected() {
		throw new BadMappingException("Bean population cycle detected! Cannot populate same bean twice!"
				+ this.toString());
	}

	/**
	 * @see net.sf.kfgodel.tasks.Task#endTask()
	 */
	public void endTask() {
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BeanPopulationTask<?>)) {
			return false;
		}
		BeanPopulationTask<?> that = (BeanPopulationTask<?>) obj;
		if (!this.getPopulationType().equals(that.getPopulationType())) {
			return false;
		}
		if (!(this.getPopulatedBean() == that.getPopulatedBean())) {
			return false;
		}
		if (!(this.getSourceBean() == that.getSourceBean())) {
			return false;
		}
		return true;
	}

	/**
	 * @see net.sf.kfgodel.tasks.Task#getNextSubTask()
	 */
	@SuppressWarnings("unchecked")
	public Task<T> getNextSubTask() {
		PropertyPopulationTask subtask = this.getPopulationTasks().removeFirst();
		return (Task<T>) subtask;
	}

	public T getPopulatedBean() {
		return populatedBean;
	}

	/**
	 * @return the populationTasks
	 */
	public LinkedList<PropertyPopulationTask> getPopulationTasks() {
		if (populationTasks == null) {
			populationTasks = new LinkedList<PropertyPopulationTask>();
		}
		return populationTasks;
	}

	public PopulationType getPopulationType() {
		return populationType;
	}

	/**
	 * @see net.sf.kfgodel.tasks.Task#getResult()
	 */
	public T getResult() {
		return null;
	}

	public Object getSourceBean() {
		return sourceBean;
	}

	private TypeConverter getTypeConverter() {
		return typeConverter;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getPopulatedBean().hashCode() ^ this.getSourceBean().hashCode();
	}

	/**
	 * @see net.sf.kfgodel.tasks.Task#hasPendingSubTasks()
	 */
	public boolean hasPendingSubTasks() {
		return !this.getPopulationTasks().isEmpty();
	}

	/**
	 * @see net.sf.kfgodel.tasks.Task#prepareTask()
	 */
	public void prepareTask() {
		Class<?> metadataClass = this.getPopulationType().getMetadataClassFor(this);
		ClassPopulationMetadata classMetadata = ClassPopulationMetadaExtractor.getMetadataFor(metadataClass, this
				.getPopulationType());
		Iterator<PopulationInstruction> instructions = classMetadata.getPopulationInstructions().iterator();
		while (instructions.hasNext()) {
			PopulationInstruction instruction = instructions.next();
			PropertyPopulationTask populationSubTask = PropertyPopulationTask.createFor(this.getSourceBean(),
					instruction, this.getPopulatedBean(), this.getTypeConverter());
			this.getPopulationTasks().add(populationSubTask);
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" (");
		if (this.getPopulationType().equals(PopulationType.METADATA_READ_FROM_DESTINATION_TYPE)) {
			builder.append(this.getPopulatedBean());
			builder.append(" FROM ");
			builder.append(this.getSourceBean());
		}
		else {
			builder.append(this.getSourceBean());
			builder.append(" TO ");
			builder.append(this.getPopulatedBean());
		}
		builder.append(")");
		return builder.toString();
	}

	/**
	 * Creates a new task to populate populatedBean from data on sourceBean
	 */
	public static <T> BeanPopulationTask<T> createFor(T populatedBean, Object sourceBean,
			PopulationType populationType, TypeConverter converter) {
		BeanPopulationTask<T> task = new BeanPopulationTask<T>();
		task.populatedBean = populatedBean;
		task.sourceBean = sourceBean;
		task.populationType = populationType;
		task.typeConverter = converter;
		return task;
	}

}
