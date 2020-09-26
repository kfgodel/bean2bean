/**
 * Created on 29/12/2007 20:42:49 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.conversion.converters;

import net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter;
import net.sf.kfgodel.bean2bean.conversion.TypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * Esta clase sabe como convertir entre arrays y colecciones de elementos
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 */
@SuppressWarnings("unchecked")
public class ArrayCollectionConverter implements GeneralTypeConverter<Object, Collection> {

	/**
	 * Conversor al que se le delegarán las conversiones básicas
	 */
	private TypeConverter baseConverter;

	public TypeConverter getBaseConverter() {
		return baseConverter;
	}

	public void setBaseConverter(final TypeConverter baseConverter) {
		this.baseConverter = baseConverter;
	}

	/**
	 * @param value
	 *            Array del que se obtendran los elementos
	 * @param expectedType
	 *            Declaracion de un tipo de coleccion esperada
	 * @return La coleccion con los elementos del array
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertFrom(java.lang.Object,
	 *      java.lang.reflect.Type, java.lang.annotation.Annotation[])
	 */
	public Collection convertFrom(final Object value, final Type expectedType, final Annotation[] contextAnnotations) {
		if (expectedType == null) {
			throw new CannotConvertException("Cannot make conversion. Expected type was not defined", value,
					expectedType);
		}
		final Class<?> collectionClass = ReflectionUtils.degenerify(expectedType);
		if (collectionClass == null) {
			throw new CannotConvertException("Cannot determine collecion class for expected type", value,
					collectionClass);
		}
		final Type elementType = ReflectionUtils.getElementTypeParameterFrom(expectedType);
		final int arrayLength = Array.getLength(value);
		Collection destinationCol;
		try {
			destinationCol = Collection2CollectionConverter.instantiate(collectionClass, arrayLength);
		} catch (final CouldNotInstanstiateException e) {
			throw new CannotConvertException("Couldn't instatiante a collection", value, expectedType, e);
		}
		for (int i = 0; i < arrayLength; i++) {
			final Object sourceElement = Array.get(value, i);
			Object elementToAdd = sourceElement;
			if (elementType != null) {
				elementToAdd = this.getBaseConverter().convertValue(sourceElement, elementType);
			}
			destinationCol.add(elementToAdd);
		}
		return destinationCol;
	}

	/**
	 * @param expectedType
	 *            Declaracin del tipo de array esperado
	 * @param value
	 *            Coleccion con los elementos a agregar en el array
	 * @param contextAnnotations
	 *            No usado
	 * @return Un array con los elementos de la coleccion
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertTo(java.lang.reflect.Type,
	 *      java.lang.Object, java.lang.annotation.Annotation[])
	 */
	public Object convertTo(final Type expectedType, final Collection value, final Annotation[] contextAnnotations) {
		if (expectedType == null) {
			throw new CannotConvertException("Cannot make conversion. Expected type was not defined", value,
					expectedType);
		}
		final Class<?> arrayClass = ReflectionUtils.degenerify(expectedType);
		if (arrayClass == null) {
			throw new CannotConvertException("Cannot determine array class for expecte type", value, arrayClass);
		}
		final Class<?> elementType = arrayClass.getComponentType();
		if (elementType == null) {
			throw new CannotConvertException("Expected type should be an array[" + expectedType + "]", value,
					expectedType);
		}
		final Object createdArray = Array.newInstance(elementType, value.size());
		int i = 0;
		for (final Object sourceElement : value) {
			final Object elementToAdd = this.getBaseConverter().convertValue(sourceElement, elementType);
			Array.set(createdArray, i, elementToAdd);
			i++;
		}
		return createdArray;
	}

	public static ArrayCollectionConverter create(final TypeConverter baseConverter) {
		final ArrayCollectionConverter converter = new ArrayCollectionConverter();
		converter.setBaseConverter(baseConverter);
		return converter;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionFrom(java.lang.Class,
	 *      java.lang.reflect.Type, java.lang.Object)
	 */
	public boolean acceptsConversionFrom(final Class<?> sourceType, final Type expectedType, final Object sourceObject) {
		if (!sourceType.isArray()) {
			return false;
		}
		final Class<?> expectedClass = ReflectionUtils.degenerify(expectedType);
		if (!Collection.class.isAssignableFrom(expectedClass)) {
			return false;
		}
		return true;
	}

	/**
	 * @param expectedType
	 * @param sourceType
	 * @param sourceObject
	 * @return empty comment
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionTo(java.lang.reflect.Type,
	 *      java.lang.Class, java.lang.Object)
	 */
	public boolean acceptsConversionTo(final Type expectedType, final Class<?> sourceType, final Object sourceObject) {
		if (!Collection.class.isAssignableFrom(sourceType)) {
			return false;
		}
		final Class<?> expectedClass = ReflectionUtils.degenerify(expectedType);
		if (!expectedClass.isArray()) {
			return false;
		}
		return true;
	}

}
