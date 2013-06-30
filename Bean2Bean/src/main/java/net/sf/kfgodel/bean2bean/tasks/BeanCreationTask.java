/**
 * Created on 25/12/2007 15:52:45 Copyright (C) 2007 Dario L. Garcia
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
import net.sf.kfgodel.bean2bean.instantiation.ObjectFactory;
import net.sf.kfgodel.bean2bean.population.PopulationType;
import net.sf.kfgodel.bean2bean.population.metadata.ClassPopulationMetadataExtractor;
import net.sf.kfgodel.tasks.Task;

/**
 * Esta clase representa una tarea de creacion de un bean, basandose en el estado de otro para
 * popularlo a un estado consistente inicial.<br>
 * El tipo de populacion permite indicar si la informacion de
 * 
 * @version 1.0
 * @since 25/12/2007
 * @author D. Garcia
 * @param <R>
 */
public class BeanCreationTask<R> implements Task<R> {

	/**
	 * Clase a instanciar para crear el bean
	 */
	private Class<R> destinationType;
	/**
	 * Indica si aun no se ha populado la nueva instancia
	 */
	private boolean isResultUnpopulated;
	/**
	 * Tipo de populacion a realizar sobre la instancia creada
	 */
	private PopulationType populationType;
	/**
	 * Instancia creada
	 */
	private R result;
	/**
	 * Objeto con el estado que debe ser copiado sobre la instancia creada
	 */
	private Object sourceBean;

	/**
	 * Conversor a utilizar para la populacion
	 */
	private TypeConverter typeConverter;

	private ClassPopulationMetadataExtractor metadataExtractor;

	/**
	 * @see net.sf.kfgodel.tasks.Task#cycleDetected()
	 */
	public void cycleDetected() {
		throw new BadMappingException("Creation cycle detected! Same bean cannot be created twice!" + this.toString());
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
	public boolean equals(final Object obj) {
		if (!(obj instanceof BeanCreationTask<?>)) {
			return false;
		}
		final BeanCreationTask<?> that = (BeanCreationTask<?>) obj;
		if (!this.getPopulationType().equals(that.getPopulationType())) {
			return false;
		}
		if (!this.getDestinationType().equals(that.getDestinationType())) {
			return false;
		}
		if (!(this.getSourceBean() == that.getSourceBean())) {
			return false;
		}
		return true;
	}

	public Class<R> getDestinationType() {
		return this.destinationType;
	}

	/**
	 * @return La subtarea de populacion que debe ser realizada para que esta tarea se considere
	 *         completa
	 * @see net.sf.kfgodel.tasks.Task#getNextSubTask()
	 */
	public Task<R> getNextSubTask() {
		final BeanPopulationTask<R> populationSubtask = BeanPopulationTask.createFor(this.getResult(), getSourceBean(),
				this.getPopulationType(), this.getTypeConverter(), metadataExtractor);
		this.isResultUnpopulated = false;
		return populationSubtask;
	}

	public PopulationType getPopulationType() {
		return populationType;
	}

	/**
	 * @return El bean creado
	 * @see net.sf.kfgodel.tasks.Task#getResult()
	 */
	public R getResult() {
		return this.result;
	}

	public Object getSourceBean() {
		return this.sourceBean;
	}

	private TypeConverter getTypeConverter() {
		return typeConverter;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getDestinationType().hashCode() ^ this.getSourceBean().hashCode();
	}

	/**
	 * @return true si aun no se populo el bean creado
	 * @see net.sf.kfgodel.tasks.Task#hasPendingSubTasks()
	 */
	public boolean hasPendingSubTasks() {
		return this.isResultUnpopulated;
	}

	/**
	 * @see net.sf.kfgodel.tasks.Task#prepareTask()
	 */
	public void prepareTask() {
	}

	public void setResult(final R result) {
		this.result = result;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(" (");
		if (this.getPopulationType().equals(PopulationType.METADATA_READ_FROM_DESTINATION_TYPE)) {
			builder.append(this.getDestinationType().getSimpleName());
			builder.append(" FROM ");
			builder.append(this.getSourceBean());
		} else {
			builder.append(this.getSourceBean());
			builder.append(" TO ");
			builder.append(this.getDestinationType().getSimpleName());
		}
		builder.append(")");
		return builder.toString();
	}

	public static <R> BeanCreationTask<R> createFor(final Object sourceBean, final Class<R> destinationType,
			final PopulationType populationType, final TypeConverter converter,
			final ClassPopulationMetadataExtractor extractor) {
		final BeanCreationTask<R> task = new BeanCreationTask<R>();
		task.sourceBean = sourceBean;
		task.destinationType = destinationType;
		task.isResultUnpopulated = true;
		task.populationType = populationType;
		task.typeConverter = converter;
		task.metadataExtractor = extractor;
		return task;
	}

	/**
	 * Creates the new instance of the bean to use as result. This method is called before
	 * populating beans properties as recursion might occur and this result cuts it
	 */
	@SuppressWarnings("unchecked")
	public void prepareResult() {
		final TypeConverter usedTypeConverter = getTypeConverter();
		final ObjectFactory objectFactory = usedTypeConverter.getObjectFactory();
		if (objectFactory == null) {
			throw new IllegalStateException("Configuration error: No object factory was found for TypeConverter["
					+ usedTypeConverter + "]");
		}
		// Esta ida y vuelta de casteos es necesario para el compilador de maven
		final Object instantiate = objectFactory.instantiate(getDestinationType());
		final R newInstace = (R) instantiate;
		setResult(newInstace);
	}

}