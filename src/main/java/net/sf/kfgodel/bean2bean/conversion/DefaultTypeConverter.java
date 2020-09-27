/**
 * Created on 29/12/2007 02:10:08 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.conversion;

import net.sf.kfgodel.bean2bean.Bean2Bean;
import net.sf.kfgodel.bean2bean.conversion.converters.AnnotatedClassConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.ArrayArrayConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.ArrayCollectionConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.Collection2CollectionConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.Enum2NumberConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.Enum2StringConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.FormatterConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.JsonStringObjectConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.Number2EnumConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.PolymorphismConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.String2EnumConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.String2NumberConverter;
import net.sf.kfgodel.bean2bean.conversion.converters.WrappedXWorkConverter;
import net.sf.kfgodel.bean2bean.conversion.spring.GeneralConverterRegistration;
import net.sf.kfgodel.bean2bean.conversion.spring.SpecializedConverterRegistration;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.bean2bean.exceptions.MissingDependencyException;
import net.sf.kfgodel.bean2bean.instantiation.EmptyConstructorObjectFactory;
import net.sf.kfgodel.bean2bean.instantiation.ObjectFactory;
import net.sf.kfgodel.bean2bean.population.conversion.TypeConverterCall;
import net.sf.kfgodel.bean2bean.population.conversion.TypeConverterCall.GeneralConverterFromCall;
import net.sf.kfgodel.bean2bean.population.conversion.TypeConverterCall.GeneralConverterToCall;
import net.sf.kfgodel.bean2bean.population.conversion.TypeConverterCall.SpecializedConverterCall;
import net.sf.kfgodel.dgarcia.colecciones.maps.DoubleKeyMap;
import net.sf.kfgodel.dgarcia.colecciones.maps.impl.DoubleKeyHashMap;
import net.sf.kfgodel.dgarcia.lang.reflection.iterators.SuperTypeIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Esta clase define la funcionalidad para convertir entre distintos tipos de datos. <br>
 * Esta clase agrega funcionalidad y puntos de extension a los conversores de XWork y Ognl.
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 */
public class DefaultTypeConverter implements TypeConverter {
	private static final Logger logger = LoggerFactory.getLogger(DefaultTypeConverter.class);

	private Map<String, GeneralTypeConverter<Object, Object>> generalRegistry;

	private DoubleKeyMap<Class<?>, Type, SpecializedTypeConverter<Object, ?>> specializedConverters;

	private Map<String, SpecializedTypeConverter<Object, Object>> specializedRegistry;

	/**
	 * This map serves as a cache for resolved converters
	 */
	private DoubleKeyMap<Class<?>, Type, TypeConverterCall> converterCallCache;

	/**
	 * Factory to instance objects in sub converters
	 */
	private ObjectFactory objectFactory;

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.TypeConverter#convertValue(java.lang.Object,
	 *      java.lang.reflect.Type)
	 */
	public <T> T convertValue(final Object value, final Type expectedType) throws CannotConvertException {
		return this.<T> convertValue(value, expectedType, null);
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.TypeConverter#convertValue(java.lang.Object,
	 *      java.lang.reflect.Type, java.lang.annotation.Annotation[])
	 */
	@SuppressWarnings("unchecked")
	public <T> T convertValue(final Object sourceValue, final Type expectedType, final Annotation[] contextAnnotations) {
		if (sourceValue == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("Returned null without conversion");
			}
			return null;
		}
		if (expectedType == null) {
			throw new IllegalArgumentException("Expected Type shouldn't be null");
		}
		final Class<?> sourceType = sourceValue.getClass();
		if (logger.isDebugEnabled()) {
			logger.debug("Converting from <" + sourceType + "> to <" + expectedType + ">: [" + sourceValue + "]");
		}

		final TypeConverterCall foundConverterCall = findConverterFor(sourceType, expectedType, sourceValue);
		if (foundConverterCall == null) {
			throw new CannotConvertException("There's no registered converter for [" + sourceValue + "] to ["
					+ expectedType + "]", sourceValue, expectedType);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Elected converter: [" + foundConverterCall + "]");
		}
		final T convertedValue = (T) foundConverterCall.convertValue(sourceValue, expectedType, contextAnnotations);
		if (logger.isDebugEnabled()) {
			logger.debug("Converted value: " + convertedValue);
		}
		return convertedValue;
	}

	/**
	 * Searches on local converter for a converter that matches requested conversion
	 * 
	 * @param sourceType
	 *            Class of the source object
	 * @param expectedType
	 *            Type to convert to
	 * @param sourceValue
	 *            Value to convert
	 * @return The converter call to invoke a conversion, or null if no converter is found
	 */
	private TypeConverterCall findConverterFor(final Class<?> sourceType, final Type expectedType,
			final Object sourceValue) {
		TypeConverterCall foundConverterCall = getCachedConverterFor(sourceType, expectedType);
		if (foundConverterCall != null) {
			// We will use a cached one!
			return foundConverterCall;
		}
		final SpecializedTypeConverter<Object, ?> specializedConverter = getSpecializedConverterFor(sourceType,
				expectedType);
		if (specializedConverter != null) {
			foundConverterCall = new SpecializedConverterCall(specializedConverter);
		} else {
			for (final GeneralTypeConverter<Object, Object> converter : getGeneralConverters()) {
				if (converter.acceptsConversionFrom(sourceType, expectedType, sourceValue)) {
					foundConverterCall = new GeneralConverterFromCall(converter);
					break;
				}
				if (converter.acceptsConversionTo(expectedType, sourceType, sourceValue)) {
					foundConverterCall = new GeneralConverterToCall(converter);
					break;
				}
			}
		}
		if (foundConverterCall != null) {
			cacheConverterFor(sourceType, expectedType, foundConverterCall);
		}
		return foundConverterCall;
	}

	/**
	 * Saves a resolved converter for indicated conversion to reuse later
	 * 
	 * @param sourceType
	 *            Class to comvert from
	 * @param expectedType
	 *            Type to convert to
	 * @param foundConverterCall
	 *            Converter call to invoke for the conversion
	 */
	private void cacheConverterFor(final Class<?> sourceType, final Type expectedType,
			final TypeConverterCall foundConverterCall) {
		if (converterCallCache == null) {
			converterCallCache = new DoubleKeyHashMap<Class<?>, Type, TypeConverterCall>();
		}
		converterCallCache.put(sourceType, expectedType, foundConverterCall);
	}

	/**
	 * Returns the converter that was previously used to make the requested conversion
	 * 
	 * @param sourceType
	 *            Class to convert from
	 * @param expectedType
	 *            Type to convert to
	 * @return A cached converter used previuosly or null
	 */
	private TypeConverterCall getCachedConverterFor(final Class<?> sourceType, final Type expectedType) {
		if (converterCallCache == null) {
			return null;
		}
		final TypeConverterCall cachedCall = converterCallCache.get(sourceType, expectedType);
		return cachedCall;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.TypeConverter#convertValueToClass(java.lang.Class,
	 *      java.lang.Object)
	 */
	public <T> T convertValueToClass(final Class<T> expectedClass, final Object value) throws CannotConvertException {
		final Type expectedType = expectedClass;
		@SuppressWarnings("unchecked")
		final T convertedValue = (T) convertValue(value, expectedType);
		return convertedValue;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.TypeConverter#getGeneralConverterByName(java.lang.String)
	 */
	public GeneralTypeConverter<Object, Object> getGeneralConverterByName(final String name) {
		return this.getGeneralRegistry().get(name);
	}

	private Collection<GeneralTypeConverter<Object, Object>> getGeneralConverters() {
		return this.getGeneralRegistry().values();
	}

	private Map<String, GeneralTypeConverter<Object, Object>> getGeneralRegistry() {
		if (generalRegistry == null) {
			generalRegistry = new LinkedHashMap<String, GeneralTypeConverter<Object, Object>>();
		}
		return generalRegistry;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.TypeConverter#getSpecializedConverterByName(java.lang.String)
	 */
	public SpecializedTypeConverter<Object, Object> getSpecializedConverterByName(final String name) {
		return this.getSpecializedRegistry().get(name);
	}

	/**
	 * Devuelve el conversor especifico para los tipos indicados.<br>
	 * Si el tipo esperado esta generificado, primero se buscara un conversor para el tipo con sus
	 * parametros, y luego para el tipo sin parametros.<br>
	 * El orden de busqueda de este metodo es: - Se busca primero un conversor para los dos tipos
	 * indicados.<br>
	 * - Si no encuentra se busca en la jerarquia del tipo fuente hacia el tipo destino. <br>
	 * - Si no se encuentra tampoco, se eliminan los parametros de generics del tipo esperado (en
	 * caso de que hubiere) y se busca nuevamente en la jerarquia del objeto fuente. <br>
	 * - Si no existe ningun conversor se devuelve null.
	 * 
	 * @param sourceType
	 *            Tipo del objeto origen
	 * @param expectedType
	 *            Tipo esperado de la conversion
	 * @return El conversor que permite realizar la conversion entre los tipos indicados o null si
	 *         no existe ninguno.
	 */
	private SpecializedTypeConverter<Object, ?> getSpecializedConverterFor(final Class<?> sourceType,
			final Type expectedType) {
		final Iterator<Type> destinationHierarchyIterator = SuperTypeIterator.createFor(expectedType);
		while (destinationHierarchyIterator.hasNext()) {
			final Type destinationHierarchyType = destinationHierarchyIterator.next();
			final Iterator<Class<?>> sourceHierarchyIterator = SuperTypeIterator.createFor(sourceType);
			while (sourceHierarchyIterator.hasNext()) {
				final Class<?> sourceHierarchyType = sourceHierarchyIterator.next();
				final SpecializedTypeConverter<Object, ?> converter = this.getSpecializedConverters().get(
						sourceHierarchyType, destinationHierarchyType);
				if (converter != null) {
					return converter;
				}
			}
		}
		return null;
	}

	private DoubleKeyMap<Class<?>, Type, SpecializedTypeConverter<Object, ?>> getSpecializedConverters() {
		if (specializedConverters == null) {
			specializedConverters = new DoubleKeyHashMap<Class<?>, Type, SpecializedTypeConverter<Object, ?>>(
					new DoubleKeyHashMap.HashMapCreator<Type, SpecializedTypeConverter<Object, ?>>());
		}
		return specializedConverters;
	}

	private Map<String, SpecializedTypeConverter<Object, Object>> getSpecializedRegistry() {
		if (specializedRegistry == null) {
			specializedRegistry = new HashMap<String, SpecializedTypeConverter<Object, Object>>();
		}
		return specializedRegistry;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.TypeConverter#registerGeneralConverter(net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter)
	 */
	public void registerGeneralConverter(final GeneralTypeConverter<?, ?> converter) {
		final String className = converter.getClass().getName();
		registerGeneralConverter(className, converter);
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.TypeConverter#registerGeneralConverter(java.lang.String,
	 *      net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter)
	 */
	public void registerGeneralConverter(final String name, final GeneralTypeConverter<?, ?> converter) {
		@SuppressWarnings("unchecked")
		final GeneralTypeConverter<Object, Object> converter2 = (GeneralTypeConverter<Object, Object>) converter;
		this.getGeneralRegistry().put(name, converter2);
		invalidateCache();
	}

	/**
	 * Invalidates the cache for converters
	 */
	private void invalidateCache() {
		if (this.converterCallCache == null || converterCallCache.size() == 0) {
			return;
		}
		converterCallCache.clear();
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.TypeConverter#registerSpecializedConverterFor(java.lang.Class,
	 *      java.lang.reflect.Type, net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter)
	 */
	public <S> void registerSpecializedConverterFor(final Class<S> sourceType, final Type destinationType,
			final SpecializedTypeConverter<? super S, ?> converter) {
		final String className = converter.getClass().getName();
		registerSpecializedConverterFor(className, sourceType, destinationType, converter);
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.TypeConverter#registerSpecializedConverterFor(java.lang.String,
	 *      java.lang.Class, java.lang.reflect.Type,
	 *      net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter)
	 */
	@SuppressWarnings("unchecked")
	public <S> void registerSpecializedConverterFor(final String name, final Class<S> sourceType,
			final Type destinationType, final SpecializedTypeConverter<? super S, ?> converter) {

		@SuppressWarnings("rawtypes")
		final SpecializedTypeConverter<Object, ?> converter2 = (SpecializedTypeConverter) converter;
		this.getSpecializedConverters().put(sourceType, destinationType, converter2);
		this.getSpecializedRegistry().put(name, (SpecializedTypeConverter<Object, Object>) converter);
		invalidateCache();
	}

	/**
	 * Inicializa la instancia creada con los conversores default para los tipos comunes
	 * 
	 * @param instancia
	 *            Instancia a inicializar
	 * @param bean2Bean
	 *            Instancia de bean2bean a la que se le delega las conversiones reflexivas
	 */
	public static void defaultInitialization(final TypeConverter instancia, final Bean2Bean bean2Bean) {
		instancia.setObjectFactory(EmptyConstructorObjectFactory.create());

		// Conversores para enums
		instancia.registerSpecializedConverterFor(Enum.class, String.class, Enum2StringConverter.create());
		instancia.registerSpecializedConverterFor(String.class, Enum.class, String2EnumConverter.create());
		instancia.registerSpecializedConverterFor(Number.class, Enum.class, Number2EnumConverter.create());
		final Enum2NumberConverter enum2NumberConverter = Enum2NumberConverter.create();
		instancia.registerSpecializedConverterFor(Enum.class, Number.class, enum2NumberConverter);
		instancia.registerSpecializedConverterFor("Enum2intConverter", Enum.class, Integer.TYPE, enum2NumberConverter);

		// Conversor especial para Strings a numericos
		instancia.registerSpecializedConverterFor(String.class, Number.class, String2NumberConverter.create());

		// Conversor para colecciones
		instancia.registerSpecializedConverterFor(Collection.class, Collection.class,
				Collection2CollectionConverter.create(instancia));

		// Conversor para TOs
		instancia.registerGeneralConverter(AnnotatedClassConverter.create(bean2Bean));

		// Conversor para arrays
		instancia.registerGeneralConverter(ArrayCollectionConverter.create(instancia));
		instancia.registerGeneralConverter(ArrayArrayConverter.create(instancia));

		// Conversor para tipos compatibles (asignables directamente)
		// este conversor debe ser uno de los ultimos, antes del de xwork
		// para asegurar la identidad de los tipos compatibles
		instancia.registerGeneralConverter(PolymorphismConverter.create());

		// Conversor para String utilizando JSON
		// Este conversor esta despues del polimorfico para no convertir cadenas
		// Si no es necesario, impidiendo la conversion directa de Strings a
		// formato JSON!
		JsonStringObjectConverter jsonConverter = null;
		try {
			jsonConverter = JsonStringObjectConverter.create();
		} catch (final NoClassDefFoundError e) {
			if (!"com/fasterxml/jackson/databind/JsonMappingException".equals(e.getMessage())) {
				// Es un error que no conocemos
				throw new MissingDependencyException("There's a missing class creating JSON converter", e);
			} else {
				if (logger.isInfoEnabled()) {
					logger.info("JsonStringObjectConverter skipped, JSON dependency not found");
				}
			}
		}
		if (jsonConverter != null) {
			instancia.registerGeneralConverter(jsonConverter);
		}

		// Conversor general de tipos basicos
		instancia.registerGeneralConverter(WrappedXWorkConverter.create());

		// Conversor opcional, usado sólo por nombre
		instancia.registerGeneralConverter(FormatterConverter.create());
	}

	/**
	 * Crea un converter vacío sin subconversores
	 * 
	 * @return La nueva instancia creada
	 */
	public static DefaultTypeConverter create() {
		final DefaultTypeConverter converter = new DefaultTypeConverter();
		return converter;
	}

	/**
	 * Agrega a este type converter un conjunto de conversores. La lista puede estar compuesta de
	 * {@link GeneralTypeConverter}, {@link GeneralConverterRegistration}, o
	 * {@link SpecializedConverterRegistration}
	 * 
	 * @param converterRegistrations
	 *            Una lista de registraciones para realizar en este conversor de tipos
	 */
	@SuppressWarnings("unchecked")
	public void setBulkConverters(final List<?> converterRegistrations) {
		for (final Object object : converterRegistrations) {
			if (object instanceof SpecializedConverterRegistration) {
				@SuppressWarnings("rawtypes")
				final SpecializedConverterRegistration registration = (SpecializedConverterRegistration) object;
				this.registerSpecializedConverterFor(registration.getRegistrationName(), registration.getSourceType(),
						registration.getDestinationType(), registration.getConverter());
			} else if (object instanceof GeneralConverterRegistration) {
				final GeneralConverterRegistration registration = (GeneralConverterRegistration) object;
				this.registerGeneralConverter(registration.getNameRegistration(), registration.getConverter());
			} else if (object instanceof GeneralTypeConverter) {
				@SuppressWarnings("rawtypes")
				final GeneralTypeConverter converter = (GeneralTypeConverter) object;
				this.registerGeneralConverter(converter);
			} else {
				throw new IllegalArgumentException("Object is not a valid converter: " + object);
			}
		}
	}

	public ObjectFactory getObjectFactory() {
		return objectFactory;
	}

	public void setObjectFactory(final ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

}
