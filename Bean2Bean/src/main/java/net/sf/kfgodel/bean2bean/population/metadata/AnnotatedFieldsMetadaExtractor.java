/**
 * Created on 23/12/2007 03:33:53 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.population.metadata;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;
import net.sf.kfgodel.bean2bean.annotations.CopyTo;
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;
import net.sf.kfgodel.bean2bean.instantiation.ObjectFactory;
import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;
import net.sf.kfgodel.bean2bean.interpreters.natives.ClassReferenceExpression;
import net.sf.kfgodel.bean2bean.metadata.ClassPopulationMetadata;
import net.sf.kfgodel.bean2bean.metadata.ExpressionDeclaration;
import net.sf.kfgodel.bean2bean.metadata.PropertyMappingDeclaration;
import net.sf.kfgodel.bean2bean.population.CannotCreateConversionInstructionException;
import net.sf.kfgodel.bean2bean.population.PopulationType;
import net.sf.kfgodel.bean2bean.population.conversion.ConversionInstruction;
import net.sf.kfgodel.bean2bean.population.conversion.ExpectedTypeExtractor;
import net.sf.kfgodel.bean2bean.population.conversion.GeneralConversionInstruction;
import net.sf.kfgodel.bean2bean.population.conversion.NonConversionInstruction;
import net.sf.kfgodel.bean2bean.population.conversion.NullTypeExtractor;
import net.sf.kfgodel.bean2bean.population.conversion.PropertyChainTypeExtractor;
import net.sf.kfgodel.bean2bean.population.conversion.TypeFromExpressionExtractor;
import net.sf.kfgodel.bean2bean.population.getting.GetterInstruction;
import net.sf.kfgodel.bean2bean.population.instructions.GeneralPopulationInstruction;
import net.sf.kfgodel.bean2bean.population.instructions.PopulationInstruction;
import net.sf.kfgodel.bean2bean.population.instructions.ScriptedInstruction;
import net.sf.kfgodel.bean2bean.population.setting.SetterInstruction;
import net.sf.kfgodel.dgarcia.lang.iterators.basic.ConditionalIterator;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;
import net.sf.kfgodel.dgarcia.lang.reflection.conditions.AnnotatedCondition;

/**
 * Esta clase es un repositorio de informacion acerca de como popular desde y hacia clases annotadas
 * con los annotations {@link CopyFrom}, {@link CopyFromAndTo}, y {@link CopyTo}
 * 
 * @version 1.0
 * @since 23/12/2007
 * @author D. Garcia
 */
public class AnnotatedFieldsMetadaExtractor implements ClassPopulationMetadataExtractor {

	/**
	 * Mapa de metada de populacion por tipo de populacion y por clase
	 */
	private Map<PopulationType, Map<Class<?>, ClassPopulationMetadata>> metadataPerPopulationType;

	/**
	 * Extrae los annotations asociados del atributo pasado
	 * 
	 * @param field
	 *            Atributo del que se extraeran los annotations
	 * @param contextAnnotationsTypes
	 *            Tipos de los annotations a buscar
	 * @return Un array en el mismo orden con los annotations extraidos, o un null si no existía en
	 *         el atributo
	 */
	public static Annotation[] extractContextAnnotationsFrom(final Field field,
			final Class<? extends Annotation>[] contextAnnotationsTypes) {
		Annotation[] contextAnnotations = null;
		if (contextAnnotationsTypes != null) {
			contextAnnotations = getAnnotationContextFor(contextAnnotationsTypes, field);
		}
		return contextAnnotations;
	}

	/**
	 * Crea las instrucciones de ensamblado a partir la clase pasada y del tipo de populacion
	 * recorriendo con reflection las propiedades de la clase anotadas con los annotations
	 * especiales
	 * 
	 * @param metadataClass
	 *            La clase para la que se quieren obtener las instrucciones, la cual debe tener
	 *            annotations para realizar la populacion
	 * @param populationType
	 *            Tipo de populacion a realizar
	 * @param objectFactory
	 *            Factory para crear instancias en de los scripts
	 * @return El conjunto de instrucciones
	 */
	private static ClassPopulationMetadata createMetadataFor(final Class<?> metadataClass,
			final PopulationType populationType, final ObjectFactory objectFactory) {
		final Iterator<Field> allFields = ReflectionUtils.getAllFieldsOf(metadataClass);
		final AnnotatedCondition anotadosConInstrucciones = AnnotatedCondition.create(populationType
				.getMetadataAnnotationTypes());
		final ConditionalIterator<Field> annotatedFields = ConditionalIterator.createFrom(anotadosConInstrucciones,
				allFields);

		final List<PopulationInstruction> instructions = new ArrayList<PopulationInstruction>();
		while (annotatedFields.hasNext()) {
			final Field field = annotatedFields.next();
			final PopulationInstruction instruction = populationType.createInstructionFor(field, objectFactory);
			instructions.add(instruction);
		}
		final ClassPopulationMetadata metadata = ClassPopulationMetadata.create(metadataClass, instructions);
		return metadata;
	}

	/**
	 * Devuelve los annotations de los tipos indicados que se encuentren en el atributo pasado
	 * 
	 * @param contextAnnotationsTypes
	 *            Tipo de los annotations que deberian existir en el atributo. Si el tipo de
	 *            annotation no esta presenta se incluira un valor null.
	 * @param field
	 *            Atributo del que se extraeran los annotations
	 * @return Un array con los annotations presentes en el atributo o espacios con null en los
	 *         annotations faltantes
	 */
	private static Annotation[] getAnnotationContextFor(final Class<? extends Annotation>[] contextAnnotationsTypes,
			final Field field) {
		final Annotation[] contextAnnotations = new Annotation[contextAnnotationsTypes.length];
		for (int i = 0; i < contextAnnotationsTypes.length; i++) {
			final Class<? extends Annotation> annotationType = contextAnnotationsTypes[i];
			final Annotation annotation = field.getAnnotation(annotationType);
			contextAnnotations[i] = annotation;
		}
		return contextAnnotations;
	}

	/**
	 * @param objectFactory
	 * @see net.sf.kfgodel.bean2bean.population.metadata.ClassPopulationMetadataExtractor#getMetadataFor(java.lang.Class,
	 *      net.sf.kfgodel.bean2bean.population.PopulationType)
	 */
	public ClassPopulationMetadata getMetadataFor(final Class<?> metadataClass, final PopulationType populationType,
			final ObjectFactory objectFactory) {
		final Map<Class<?>, ClassPopulationMetadata> metadataPerClass = getMetadataPerClass(populationType);
		ClassPopulationMetadata metadata = metadataPerClass.get(metadataClass);
		if (metadata == null) {
			metadata = createMetadataFor(metadataClass, populationType, objectFactory);
			metadataPerClass.put(metadataClass, metadata);
		}
		return metadata;
	}

	/**
	 * Devuelve el mapa de instrucciones por clase para el tipo de populacion indicada
	 * 
	 * @param populationType
	 *            Tipo de populacion para el cual quiere obtenerse el mapa de instrucciones
	 * @return El mapa de instrucciones por clase (sera creado uno nuevo si no existe)
	 */
	private Map<Class<?>, ClassPopulationMetadata> getMetadataPerClass(final PopulationType populationType) {
		Map<Class<?>, ClassPopulationMetadata> metadataPerClass = getMetadataPerPopulationType().get(populationType);
		if (metadataPerClass == null) {
			metadataPerClass = createMetadataCacheMap();
			getMetadataPerPopulationType().put(populationType, metadataPerClass);
		}
		return metadataPerClass;
	}

	/**
	 * Crea el mapa utilizado como cache o referencia de la metada por cada clase
	 * 
	 * @return El mapa creado para la metadata de un tipo de populacion
	 */
	private Map<Class<?>, ClassPopulationMetadata> createMetadataCacheMap() {
		return new WeakHashMap<Class<?>, ClassPopulationMetadata>();
	}

	/**
	 * Creates a conversion instruction based on the expectedType declaration made by the user.
	 * 
	 * @param objectFactory
	 */
	private static ConversionInstruction createConversionInstructionFor(final PropertyMappingDeclaration mapping,
			final ObjectFactory objectFactory) throws CannotCreateConversionInstructionException {

		final String alternativeConversor = mapping.getDesignatedConverter();

		final ExpressionDeclaration typeDeclaration = mapping.getExpectedTypeDeclaration();

		ExpectedTypeExtractor typeExtractor = null;
		if (typeDeclaration.isEmpty()) {
			// Si no indico como, debería ser un path de propiedades para llegar al tipo esperado
			final ExpressionDeclaration setterDeclaration = mapping.getSetterExpression();
			final String setterExpression = setterDeclaration.getExpressionValue();
			if (ReflectionUtils.isPropertyChain(setterExpression)) {
				typeExtractor = PropertyChainTypeExtractor.create(setterExpression, objectFactory);
			} else {
				if (alternativeConversor != null && alternativeConversor.length() > 0) {
					// Indico un conversor pero no sabemos el tipo esperado, dejamos que falle el
					// conversor si requiere un tipo especial. Asumimos que el usuario sabe lo que
					// hace y no
					// necesita un tipo esperado
					typeExtractor = NullTypeExtractor.create();
				} else {
					// Llegado este punto si no podemos determinar un tipo, obviamos la conversion y
					// quizás el usuario usa otro mecanismo (tal vez no necesita conversion). Si no
					// se encargó él, se producirá un error al intentar la asignacion
					return new NonConversionInstruction();
				}
			}
		} else {
			// El usuario indico como obtener el tipo esperado, usamos sus instrucciones
			if (InterpreterType.REFLECTION.equals(typeDeclaration.getInterpreterType())) {
				// Podria ser una referencia a una clase, intentamos por ese camino
				typeExtractor = ClassReferenceExpression.parse(typeDeclaration.getExpressionValue());
			}

			// No era una referencia a una clase, intentamos como una expresión a evaluar
			if (typeExtractor == null) {
				typeExtractor = TypeFromExpressionExtractor.create(typeDeclaration.getExpressionValue(),
						typeDeclaration.getInterpreterType());
			}
		}

		final Annotation[] contextAnnotations = mapping.getRelatedAnnotations();
		final PopulationType populationType = mapping.getPopulationType();
		final GeneralConversionInstruction conversionInstruction = GeneralConversionInstruction.create(typeExtractor,
				contextAnnotations, alternativeConversor, populationType);
		return conversionInstruction;
	}

	/**
	 * Crea una instruccion de populación usando las indicaciones del mapeo
	 * 
	 * @param mapping
	 *            Objeto que contiene los datos del mapeo
	 * @param objectFactory
	 *            Factory para crear instancias necesarias en los scripts
	 * @return La instrucción creada para popular
	 * @throws CannotCreateConversionInstructionException
	 *             Si la conversion no pudo determinarse
	 */
	public static PopulationInstruction createPopulationInstructionFor(final PropertyMappingDeclaration mapping,
			final ObjectFactory objectFactory) throws CannotCreateConversionInstructionException {
		final GetterInstruction getterInstruction = ScriptedInstruction.createFor(mapping.getGetterExpression(),
				objectFactory);
		final ConversionInstruction conversionInstruction = createConversionInstructionFor(mapping, objectFactory);
		final SetterInstruction setterInstruction = ScriptedInstruction.createFor(mapping.getSetterExpression(),
				objectFactory);
		final MissingPropertyAction whenMissingAction = mapping.getActionWhenMissing();

		final GeneralPopulationInstruction populationInstruction = GeneralPopulationInstruction.create(
				getterInstruction, conversionInstruction, setterInstruction, whenMissingAction);
		return populationInstruction;
	}

	public static ClassPopulationMetadataExtractor create() {
		final AnnotatedFieldsMetadaExtractor extractor = new AnnotatedFieldsMetadaExtractor();
		return extractor;
	}

	private Map<PopulationType, Map<Class<?>, ClassPopulationMetadata>> getMetadataPerPopulationType() {
		if (metadataPerPopulationType == null) {
			metadataPerPopulationType = new EnumMap<PopulationType, Map<Class<?>, ClassPopulationMetadata>>(
					PopulationType.class);
		}
		return metadataPerPopulationType;
	}

}
