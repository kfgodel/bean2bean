/**
 * Created on 29/12/2007 20:01:12 Copyright (C) 2007 Dario L. Garcia
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter;
import net.sf.kfgodel.bean2bean.conversion.TypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;


/**
 * Esta clase sabe como convertir de una coleccion a otra utilizando la parametrizacion de sus
 * elementos para realizar la conversion de cada elemento. Si ninguna conversion es necesaria, se
 * devuelve la misma coleccion origen. Si el tipo de la coleccion origen es distinta de la destino,
 * o el tipo del objeto esperado es distitno del tipo contenido, se generara un nuevo contenedor del
 * tipo esperado con las instancias.<br>
 * Para generar las instancias se utilizaran constructores de colecciones conocidos o, en ultima
 * instancia, el contructor niladico. Si no se puede instanciar la coleccion esperada, se producira
 * un error de conversion.<br>
 * Se utilizara el {@link HashSet} como implementacion de un {@link Set}, y el {@link ArrayList}
 * como implementacion de un {@link List}
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 */
@SuppressWarnings("unchecked")
public class Collection2CollectionConverter implements SpecializedTypeConverter<Collection, Collection> {

	/**
	 * Conversor al que se le delegaran las conversiones basicas
	 */
	private TypeConverter baseConverter;

	public TypeConverter getBaseConverter() {
		return baseConverter;
	}

	public void setBaseConverter(TypeConverter baseConverter) {
		this.baseConverter = baseConverter;
	}

	/**
	 * @param expectedType
	 *            Coleccion esperada
	 * @param sourceObject
	 *            Coleccion de donde se tomaran los objetos
	 * @param contextAnnotations
	 *            No usado
	 * @return La misma coleccion si no es necesaria ninguna conversion
	 * @throws CannotConvertException
	 * @see net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter#convertTo(java.lang.reflect.Type,
	 *      java.lang.Object, java.lang.annotation.Annotation[])
	 */
	public Collection convertTo(Type expectedType, Collection sourceObject, Annotation[] contextAnnotations)
			throws CannotConvertException {
		if (expectedType == null) {
			throw new CannotConvertException("Cannot make conversion. Expected type was not defined", sourceObject,
					expectedType);
		}
		Class<?> expectedClass = ReflectionUtils.degenerify(expectedType);
		Collection destinationCol;
		try {
			destinationCol = instantiate(expectedClass, sourceObject.size());
		}
		catch (CouldNotInstanstiateException e) {
			throw new CannotConvertException("Couldn't instantiate a collection", sourceObject, expectedType, e);
		}

		boolean conversionWasNeeded = false;
		Type elementType = ReflectionUtils.getElementTypeParameterFrom(expectedType);
		for (Object sourceElement : sourceObject) {
			Object elementToAdd = sourceElement;
			if (elementType != null) {
				elementToAdd = this.getBaseConverter().convertValue(sourceElement, elementType);
			}
			if (elementToAdd != sourceElement) {
				conversionWasNeeded = true;
			}
			destinationCol.add(elementToAdd);
		}
		if (!conversionWasNeeded && expectedClass.isAssignableFrom(sourceObject.getClass())) {
			destinationCol = sourceObject;
		}
		return destinationCol;
	}

	public static Collection2CollectionConverter create(TypeConverter baseConverter) {
		Collection2CollectionConverter converter = new Collection2CollectionConverter();
		converter.setBaseConverter(baseConverter);
		return converter;
	}

	/**
	 * Crea una instancia de la clase pasada, tratando de reservar el tamaño indicado
	 * 
	 * @param expectedClass
	 *            Clase que representa la coleccion a instanciar
	 * @param size
	 *            Tamaño inicial que deberia tener
	 * @return La instancia de la coleccion indicada
	 * @throws CannotConvertException
	 *             Si la coleccion no pudo ser instanciada
	 */
	public static Collection instantiate(Class<?> expectedClass, int size) throws CouldNotInstanstiateException {
		if (expectedClass.equals(Set.class)) {
			return new HashSet(size);
		}
		if (expectedClass.equals(HashSet.class)) {
			return new HashSet(size);
		}
		if (expectedClass.equals(List.class)) {
			return new ArrayList(size);
		}
		if (expectedClass.equals(ArrayList.class)) {
			return new ArrayList(size);
		}
		try {
			Constructor<?> constructor = expectedClass.getConstructor();
			constructor.setAccessible(true);
			Object newInstance = constructor.newInstance();
			return (Collection) newInstance;
		}
		catch (SecurityException e) {
			throw new CouldNotInstanstiateException("Cannot instantiate the collection beacause a security constraint["
					+ expectedClass + "]", e);
		}
		catch (IllegalArgumentException e) {
			throw new CouldNotInstanstiateException("Empty constructor should no generate this exception["
					+ expectedClass + "]", e);
		}
		catch (NoSuchMethodException e) {
			throw new CouldNotInstanstiateException("There's no empty constructor for class[" + expectedClass + "]", e);
		}
		catch (InstantiationException e) {
			throw new CouldNotInstanstiateException("Class[" + expectedClass + "] is not instantiable", e);
		}
		catch (IllegalAccessException e) {
			throw new CouldNotInstanstiateException("Empty constructor from [" + expectedClass
					+ "] is not accesible from this converter", e);
		}
		catch (InvocationTargetException e) {
			throw new CouldNotInstanstiateException("An inner exception occured when using empty constructor from ["
					+ expectedClass + "]", e);
		}
	}

}
