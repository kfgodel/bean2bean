/**
 * Created on 25/12/2007 20:13:40 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.population;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;
import net.sf.kfgodel.bean2bean.annotations.CopyTo;
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;
import net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.BadMappingException;
import net.sf.kfgodel.bean2bean.instantiation.ObjectFactory;
import net.sf.kfgodel.bean2bean.metadata.ExpressionDeclaration;
import net.sf.kfgodel.bean2bean.metadata.PropertyMappingDeclaration;
import net.sf.kfgodel.bean2bean.population.conversion.TypeConverterCall;
import net.sf.kfgodel.bean2bean.population.instructions.PopulationInstruction;
import net.sf.kfgodel.bean2bean.population.metadata.AnnotatedFieldsMetadaExtractor;
import net.sf.kfgodel.bean2bean.tasks.BeanPopulationTask;

/**
 * Este enum representa los dos tipos de populacion aplicables en la copia de datos de un bean a
 * otro.<br>
 * La diferencia entre ambos tipos es el origen de la metadata para la populacion
 * 
 * @version 1.0
 * @since 25/12/2007
 * @author D. Garcia
 */
public enum PopulationType {
	/**
	 * La informacion de como realizar la populacion se encuentran en la clase del objeto desde
	 * donde se tomaran los datos
	 */
	METADATA_READ_FROM_SOURCE_TYPE {
		/**
		 * Lee los datos del annotation invocando los métodos especificos
		 * 
		 * @param annotation
		 *            Instancia del annotation con los datos
		 * @return La informacion del mapeo
		 */
		@Override
		protected PropertyMappingDeclaration readMappingFrom(final Annotation[] annotations) {
			final CopyTo copyToAnnotation = (CopyTo) annotations[0];
			if (copyToAnnotation == null) {
				// User didn't use specific annotation
				return readLocal2RemoteMappingFrom((CopyFromAndTo) annotations[1]);
			}

			final ExpressionDeclaration setterDeclaration = ExpressionDeclaration.create(copyToAnnotation.value(),
					copyToAnnotation.setterInterpreter());
			final ExpressionDeclaration getterDeclaration = ExpressionDeclaration.create(copyToAnnotation.getter(),
					copyToAnnotation.getterInterpreter());
			final ExpressionDeclaration expectedTypeDeclaration = ExpressionDeclaration.create(
					copyToAnnotation.expectedType(), copyToAnnotation.typeInterpreter());

			final Class<? extends Annotation>[] contextAnnotations = copyToAnnotation.contextAnnotations();
			final MissingPropertyAction whenMissing = copyToAnnotation.whenMissing();
			final String useConversor = copyToAnnotation.useConversor();
			final PropertyMappingDeclaration mappingDeclaration = PropertyMappingDeclaration.create(getterDeclaration,
					expectedTypeDeclaration, setterDeclaration, whenMissing, useConversor, contextAnnotations, this);
			return mappingDeclaration;
		}

		@Override
		public Class<?> getMetadataClassFor(final BeanPopulationTask<?> populationTask) {
			return populationTask.getSourceBean().getClass();
		}

		@Override
		@SuppressWarnings("unchecked")
		public Class<? extends Annotation>[] getMetadataAnnotationTypes() {
			return new Class[] { CopyTo.class, CopyFromAndTo.class };
		}

		@Override
		public TypeConverterCall getRelatedValueConverter(final GeneralTypeConverter<?, ?> generalConverter) {
			return new TypeConverterCall.GeneralConverterToCall(generalConverter);
		}
	},
	/**
	 * La informacion de como realizar la populacion se encuentra en la clase del objeto hacia donde
	 * se copiaran los datos
	 */
	METADATA_READ_FROM_DESTINATION_TYPE {

		/**
		 * Lee los datos del annotation especifico y los traduce a un mapeo
		 * 
		 * @param annotation
		 *            Anotacion con los datos de configuracion
		 * @return El mapeo interpretado
		 */
		@Override
		protected PropertyMappingDeclaration readMappingFrom(final Annotation[] annotations) {
			final CopyFrom copyFromAnnotation = (CopyFrom) annotations[0];
			if (copyFromAnnotation == null) {
				// User didn't use specific annotation
				return readRemote2LocalMappingFrom((CopyFromAndTo) annotations[1]);
			}

			final ExpressionDeclaration getterDeclaration = ExpressionDeclaration.create(copyFromAnnotation.value(),
					copyFromAnnotation.getterInterpreter());
			final ExpressionDeclaration expectedTypeDeclaration = ExpressionDeclaration.create(
					copyFromAnnotation.expectedType(), copyFromAnnotation.typeInterpreter());
			final ExpressionDeclaration setterDeclaration = ExpressionDeclaration.create(copyFromAnnotation.setter(),
					copyFromAnnotation.setterInterpreter());

			final Class<? extends Annotation>[] contextAnnotations = copyFromAnnotation.contextAnnotations();
			final MissingPropertyAction whenMissing = copyFromAnnotation.whenMissing();
			final String useConversor = copyFromAnnotation.useConversor();
			final PropertyMappingDeclaration mappingDeclaration = PropertyMappingDeclaration.create(getterDeclaration,
					expectedTypeDeclaration, setterDeclaration, whenMissing, useConversor, contextAnnotations, this);
			return mappingDeclaration;
		}

		@Override
		public Class<?> getMetadataClassFor(final BeanPopulationTask<?> populationTask) {
			return populationTask.getPopulatedBean().getClass();
		}

		@Override
		@SuppressWarnings("unchecked")
		public Class<? extends Annotation>[] getMetadataAnnotationTypes() {
			return new Class[] { CopyFrom.class, CopyFromAndTo.class };
		}

		@Override
		public TypeConverterCall getRelatedValueConverter(final GeneralTypeConverter<?, ?> generalConverter) {
			return new TypeConverterCall.GeneralConverterFromCall(generalConverter);
		}
	};

	/**
	 * Devuelve la clase que contiene la metada de como se debe realizar la populacion de la tarea
	 * pasada
	 * 
	 * @param populationTask
	 *            Tarea de populacion desde la que se obtendra la clase
	 * @return La clase que contiene la metada para realizar la populacion
	 */
	public abstract Class<?> getMetadataClassFor(BeanPopulationTask<?> populationTask);

	/**
	 * Creates a mapping for copying a property value from local to remote using the configuration
	 * data on {@link CopyFromAndTo} annotation
	 * 
	 * @param annotation
	 *            The user configured annotation
	 * @return The property mapping data
	 */
	protected PropertyMappingDeclaration readLocal2RemoteMappingFrom(final CopyFromAndTo annotation) {
		// Declaration used for user non-specified declarations
		final ExpressionDeclaration defaultDeclaration = ExpressionDeclaration.create(annotation.value(),
				annotation.valueInterpreter());

		final ExpressionDeclaration setterDeclaration = ExpressionDeclaration.create(annotation.remoteSetter(),
				annotation.remoteSetterInterpreter());
		fillDefaultsIn(setterDeclaration, defaultDeclaration);

		final ExpressionDeclaration getterDeclaration = ExpressionDeclaration.create(annotation.localGetter(),
				annotation.localGetterInterpreter());

		final ExpressionDeclaration expectedTypeDeclaration = ExpressionDeclaration.create(
				annotation.remoteExpectedType(), annotation.remoteTypeInterpreter());

		final Class<? extends Annotation>[] contextAnnotations = annotation.contextAnnotations();
		final MissingPropertyAction whenMissing = annotation.whenMissing();
		final String useConversor = annotation.local2remoteConversor();
		final PropertyMappingDeclaration mappingDeclaration = PropertyMappingDeclaration.create(getterDeclaration,
				expectedTypeDeclaration, setterDeclaration, whenMissing, useConversor, contextAnnotations, this);
		return mappingDeclaration;
	}

	/**
	 * Creates a mapping for copying a property value from remote to local property using the
	 * configuration data on {@link CopyFromAndTo} annotation
	 * 
	 * @param annotation
	 *            The user configured annotation
	 * @return The property mapping data
	 */
	protected PropertyMappingDeclaration readRemote2LocalMappingFrom(final CopyFromAndTo annotation) {
		// Declaration used for user non-specified declarations
		final ExpressionDeclaration defaultDeclaration = ExpressionDeclaration.create(annotation.value(),
				annotation.valueInterpreter());

		final ExpressionDeclaration setterDeclaration = ExpressionDeclaration.create(annotation.localSetter(),
				annotation.localSetterInterpreter());

		final ExpressionDeclaration getterDeclaration = ExpressionDeclaration.create(annotation.remoteGetter(),
				annotation.remoteGetterInterpreter());
		fillDefaultsIn(getterDeclaration, defaultDeclaration);

		final ExpressionDeclaration expectedTypeDeclaration = ExpressionDeclaration.create(
				annotation.localExpectedType(), annotation.localTypeInterpreter());

		final Class<? extends Annotation>[] contextAnnotations = annotation.contextAnnotations();
		final MissingPropertyAction whenMissing = annotation.whenMissing();
		final String useConversor = annotation.remote2LocalConversor();
		final PropertyMappingDeclaration mappingDeclaration = PropertyMappingDeclaration.create(getterDeclaration,
				expectedTypeDeclaration, setterDeclaration, whenMissing, useConversor, contextAnnotations, this);
		return mappingDeclaration;
	}

	/**
	 * Fills uncompleted values taken from default declaration
	 * 
	 * @param overridedDeclaration
	 * @param defaultDeclaration
	 */
	private void fillDefaultsIn(final ExpressionDeclaration overridedDeclaration,
			final ExpressionDeclaration defaultDeclaration) {
		if (overridedDeclaration.isEmpty()) {
			overridedDeclaration.setExpressionValue(defaultDeclaration.getExpressionValue());
			overridedDeclaration.setInterpreterType(defaultDeclaration.getInterpreterType());
		}
	}

	/**
	 * Completa los datos del mapeo que aun estan en blanco con la informacion disponible en el
	 * atributo
	 * 
	 * @param field
	 *            Atributo del cual se puede obtener la informacion
	 * @param mappingDeclaration
	 *            Mapeo a completar
	 */
	private void fillImplicitValuesFor(final Field field, final PropertyMappingDeclaration mappingDeclaration) {
		// Si no se indica getter, se usa la propiedad origen como referencia
		final ExpressionDeclaration getterExpression = mappingDeclaration.getGetterExpression();
		fillImplicitExpression(field, getterExpression);

		// Si no se indica setter, se usa la propiedad origen como referencia de la propiedad
		// destino
		final ExpressionDeclaration setterExpression = mappingDeclaration.getSetterExpression();
		fillImplicitExpression(field, setterExpression);

		// Levanto los annotations del atributo
		final Annotation[] relatedAnnotations = AnnotatedFieldsMetadaExtractor.extractContextAnnotationsFrom(field,
				mappingDeclaration.getRelatedAnnotationTypes());
		mappingDeclaration.setRelatedAnnotations(relatedAnnotations);
	}

	/**
	 * Verifica que la expresion pasada no sea vacia. En cuyo caso la reemplaza por el nombre de la
	 * propiedad
	 * 
	 * @param field
	 *            Atributo a usar como referencia para el reemplazo
	 * @param checkedExpression
	 *            Expresion controlada
	 */
	private void fillImplicitExpression(final Field field, final ExpressionDeclaration checkedExpression) {
		if ("".equals(checkedExpression.getExpressionValue())) {
			// Si está vacia la reemplazamos por el atributo
			final String expressionValue = field.getName();
			checkedExpression.setExpressionValue(expressionValue);
		}
	}

	/**
	 * Extrae el annotation del atributo, asegurandose que no sea nulo
	 * 
	 * @param field
	 *            Artibuto desde el que se obtendrá el annotation
	 * @param classes
	 *            Tipo de anotacion a extraer
	 * @return El annotation con los valores de configuracion
	 */
	protected Annotation[] extractEnsuringAnnotationsFrom(final Field field, final Class<? extends Annotation>[] classes) {

		boolean atLeastOneFound = false;

		final Annotation[] annotations = new Annotation[classes.length];
		for (int i = 0; i < classes.length; i++) {
			final Class<? extends Annotation> annotationType = classes[i];
			final Annotation annotation = field.getAnnotation(annotationType);
			if (annotation != null) {
				atLeastOneFound = true;
			}
			annotations[i] = annotation;
		}
		if (!atLeastOneFound) {
			throw new BadMappingException("Field[" + field + "] should have at least one appropiate annotation"
					+ Arrays.toString(classes));
		}
		return annotations;
	}

	/**
	 * Devuelve los annotations relacionados con este tipo de populacion que indican como realizarlo
	 * 
	 * @return Las clases de los annotations de este tipo de populacion
	 */
	public abstract Class<? extends Annotation>[] getMetadataAnnotationTypes();

	/**
	 * Basado en este tipo de populacion decide que annotation tomar para crear la instrucción
	 * 
	 * @param field
	 *            Atributo desde el cual se creara la instruccion
	 * @param objectFactory
	 *            Factory para crear instancias durante las populaciones
	 * @return La instruccion de populacion creada
	 */
	public PopulationInstruction createInstructionFor(final Field field, final ObjectFactory objectFactory) {
		final Annotation[] annotations = extractEnsuringAnnotationsFrom(field, getMetadataAnnotationTypes());
		final PropertyMappingDeclaration mappingDeclaration = readMappingFrom(annotations);
		fillImplicitValuesFor(field, mappingDeclaration);
		try {
			final PopulationInstruction instruction = AnnotatedFieldsMetadaExtractor.createPopulationInstructionFor(
					mappingDeclaration, objectFactory);
			return instruction;
		} catch (final CannotCreateConversionInstructionException e) {
			throw new BadMappingException("Invalid metadata on field[" + field + "] review your annotation "
					+ Arrays.toString(annotations), e);
		}
	}

	/**
	 * Obtiene los datos del annotation utilizando los metodos especificos
	 * 
	 * @param annotations
	 *            Annotations encontrados en el atributo
	 * @return El mapeo leido
	 */
	protected abstract PropertyMappingDeclaration readMappingFrom(Annotation[] annotations);

	/**
	 * Devuelve el conversor de valores que se corresponde con este tipo de populacion
	 * 
	 * @return El {@link TypeConverterCall} adecuado
	 */
	public abstract TypeConverterCall getRelatedValueConverter(GeneralTypeConverter<?, ?> generalConverter);
}
