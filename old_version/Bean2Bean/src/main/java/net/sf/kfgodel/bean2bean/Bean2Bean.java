/**
 * Created on 20/12/2007 22:13:51 Copyright (C) 2007 Dario L. Garcia
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
 * rel="dc:source">bean2bean.svn.sourceforge.net</a>.
 */
package net.sf.kfgodel.bean2bean;

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.CopyTo;
import net.sf.kfgodel.bean2bean.conversion.DefaultTypeConverter;
import net.sf.kfgodel.bean2bean.conversion.TypeConverter;
import net.sf.kfgodel.bean2bean.population.PopulationType;
import net.sf.kfgodel.bean2bean.population.metadata.AnnotatedFieldsMetadaExtractor;
import net.sf.kfgodel.bean2bean.population.metadata.ClassPopulationMetadataExtractor;
import net.sf.kfgodel.bean2bean.tasks.BeanCreationTask;
import net.sf.kfgodel.bean2bean.tasks.BeanPopulationTask;
import net.sf.kfgodel.bean2bean.tasks.BeanTaskProcessor;
import net.sf.kfgodel.tasks.Task;

/**
 * This class is the main entry point of Bean2Bean and serves as a facade for bean operations.<br>
 * 
 * @version 1.0
 * @since 20/12/2007
 * @author D. Garcia
 */
public class Bean2Bean {

	/**
	 * Esta variable contiene al procesador activo para el thread actual en caso de que haya uno
	 * procesando una tarea anterior
	 */
	private static final ThreadLocal<BeanTaskProcessor> activeProcessorVariable = new ThreadLocal<BeanTaskProcessor>();

	/**
	 * Conversor utilizado para la transformacion de tipos al popular
	 */
	private TypeConverter typeConverter;

	/**
	 * Extractor de las instrucciones desde los annotations
	 */
	private ClassPopulationMetadataExtractor metadataExtractor;

	/**
	 * Creates a new instance of destinationType using sourceBean as a data source.<br>
	 * {@link CopyTo} annotations present on sourceBean will be used to map properties from
	 * sourceBean to created bean.<br>
	 * 
	 * @param <T>
	 *            Expected object type
	 * @param destinationType
	 *            Class instance to instantiate (should have an empty constructor)
	 * @param sourceBean
	 *            Object used as a data source and its class as a property mapping definition
	 * @return Created object with mapped values on its properties
	 */
	public <T> T convertTo(final Class<T> destinationType, final Object sourceBean) {
		final BeanCreationTask<T> creationTask = BeanCreationTask.createFor(sourceBean, destinationType,
				PopulationType.METADATA_READ_FROM_SOURCE_TYPE, getTypeConverter(), metadataExtractor);
		return processTask(creationTask);
	}

	/**
	 * Copies the properties from sourceBean to destinationBean using {@link CopyFrom} annotations
	 * in destinationBean class as mapping instructions.<br>
	 * 
	 * @param sourceBean
	 *            Object with the data to copy
	 * @param destinationBean
	 *            Object whose properties annotated with {@link CopyFrom} will be populated
	 */
	public void copyPropertiesFrom(final Object sourceBean, final Object destinationBean) {
		final BeanPopulationTask<Object> populationTask = BeanPopulationTask.createFor(destinationBean, sourceBean,
				PopulationType.METADATA_READ_FROM_DESTINATION_TYPE, getTypeConverter(), metadataExtractor);
		processTask(populationTask);
	}

	/**
	 * Copies the properties from sourceBean to destinationBean using {@link CopyTo} annotations in
	 * sourceBean class as mapping instructions.<br>
	 * 
	 * @param destinationBean
	 *            Object whose properties will be populated
	 * @param sourceBean
	 *            Object with source data and annotations that tell how to do the copying
	 */
	public void copyPropertiesTo(final Object destinationBean, final Object sourceBean) {
		final BeanPopulationTask<Object> populationTask = BeanPopulationTask.createFor(destinationBean, sourceBean,
				PopulationType.METADATA_READ_FROM_SOURCE_TYPE, getTypeConverter(), metadataExtractor);
		processTask(populationTask);
	}

	/**
	 * Creates a new instance of destinationType based on data from the sourceBean.<br>
	 * {@link CopyFrom} annotations on the destinationType will be used to map property values from
	 * the sourceBean to the created instance.<br>
	 * Crea un bean del tipo especificado a partir del objeto pasado desde el cual se tomaran los
	 * 
	 * @param <T>
	 *            Expected Object type
	 * @param sourceBean
	 *            Object to use as source data
	 * @param destinationType
	 *            Class instance to instantiate that is also marked with {@link CopyFrom} annotation
	 *            defining the propery mappings
	 * @return Created instance with values mapped from sourceBean
	 */
	public <T> T createFrom(final Object sourceBean, final Class<T> destinationType) {
		final BeanCreationTask<T> creationTask = BeanCreationTask.createFor(sourceBean, destinationType,
				PopulationType.METADATA_READ_FROM_DESTINATION_TYPE, getTypeConverter(), metadataExtractor);
		return processTask(creationTask);
	}

	/**
	 * {@link TypeConverter} used by this instance in type conversions
	 */
	public TypeConverter getTypeConverter() {
		return typeConverter;
	}

	/**
	 * Sets the {@link TypeConverter} used in type conversion while creating or copying properties
	 */
	public void setTypeConverter(final TypeConverter typeConverter) {
		this.typeConverter = typeConverter;
	}

	/**
	 * Crea un construictor de TOs a partir de un conversor de tipos que le permite realizar las
	 * populaciones entre tipos distintos
	 * 
	 * @param converter
	 *            Conversor de tipos
	 * @return El nuevo constructor de TOs
	 */
	public static Bean2Bean create(final TypeConverter converter) {
		final Bean2Bean beaner = new Bean2Bean();
		beaner.typeConverter = converter;
		beaner.metadataExtractor = AnnotatedFieldsMetadaExtractor.create();
		return beaner;
	}

	/**
	 * Crea el procesador para el hilo actual, de manera que las llamadas recursivas sea resueltas
	 * iterativamente, en vez de usar la pila de ejecucion
	 * 
	 * @return El procesador creado
	 */
	private static BeanTaskProcessor createActiveProcessor() {
		final BeanTaskProcessor taskProcessor = BeanTaskProcessor.create();
		activeProcessorVariable.set(taskProcessor);
		return taskProcessor;
	}

	private static BeanTaskProcessor getActiveProcessor() {
		return activeProcessorVariable.get();
	}

	/**
	 * Crea una instancia default con los conversores básicos definidos
	 * 
	 * @return La instancia creada
	 */
	public static Bean2Bean createDefaultInstance() {
		final DefaultTypeConverter converter = DefaultTypeConverter.create();
		final Bean2Bean defaultInstance = create(converter);
		// Definimos los conversores básicos
		DefaultTypeConverter.defaultInitialization(converter, defaultInstance);
		return defaultInstance;
	}

	/**
	 * Existen tres opciones posibles en la llamada a este metodo. Se esta llamando para popular una
	 * instancia. O se esta llamando por primera vez para la creacion de un bean. O se esta llamando
	 * en forma anidada desde la creacion de un bean, para la creacion recursiva de otro. En caso de
	 * ser una llamada recursiva, se postergara la resolucion real de manera de convertir una
	 * llamada recursiva en un proceso iterado. Para ello se utiliza el procesador de tareas activo.
	 * Si es la primera vez este procesador no existe y debe ser creado.
	 * 
	 * @param <T>
	 *            Tipo del objeto a crear
	 * @param destinationType
	 *            Instancia de clase que indica el tipo de objeto a crear
	 * @param pendingTask
	 *            Tarea de creacion que tiene los parametros para crear la instancia
	 * @return La isntancia creada del tipo indicado
	 */
	private static <T> T processTask(final Task<T> pendingTask) {

		BeanTaskProcessor activeProcessor = getActiveProcessor();
		final boolean isFirstExecutionCall = (activeProcessor == null);

		// Si se prouce una excepcion, se debe eliminar el procesador activo
		try {
			if (activeProcessor == null) {
				activeProcessor = createActiveProcessor();
			} else {
				// Si es una llamada anidada existe la posibilidad de que esta tarea ya
				// haya sido resuelta por una anterior
				final Task<T> alreadyResolvedTask = activeProcessor.getTaskLike(pendingTask);
				if (alreadyResolvedTask != null) {
					final T alreadyCreatedBean = alreadyResolvedTask.getResult();
					return alreadyCreatedBean;
				}
			}

			if (pendingTask instanceof BeanCreationTask<?>) {
				final BeanCreationTask<T> creationTask = (BeanCreationTask<T>) pendingTask;
				// En este punto la tarea es completamente nueva, sea anidada o no
				// se debe crear el nuevo bean anticipadamente para cortar llamadas recursivas
				creationTask.prepareResult();
			}
			activeProcessor.addTask(pendingTask);

			// Solo en la primera llamada a este metodo se resuelven todas las tareas
			// en el resto de las llamadas solo se encola la tarea
			if (isFirstExecutionCall) {
				activeProcessor.processTasks();
			}

			return pendingTask.getResult();
		} finally {
			if (isFirstExecutionCall) {
				// Despues de resolver las tareas se debe eliminar el procesador
				removeActiveProcessor();
			}
		}
	}

	/**
	 * Elimina el procesador activo para este thread
	 */
	private static void removeActiveProcessor() {
		activeProcessorVariable.set(null);
	}

	public ClassPopulationMetadataExtractor getMetadataExtractor() {
		return metadataExtractor;
	}

	public void setMetadataExtractor(final ClassPopulationMetadataExtractor metadataExtractor) {
		this.metadataExtractor = metadataExtractor;
	}

}
