/**
 * Created on 03/01/2008 17:17:02 Copyright (C) 2008 Dario L. Garcia
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import net.sf.kfgodel.bean2bean.Bean2Bean;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.bean2bean.instantiation.ObjectFactory;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;


/**
 * This inteface is the main facade for type conversions and type converters used in
 * {@link Bean2Bean}.<br>
 * 
 * @version 1.0
 * @since 03/01/2008
 * @author D. Garcia
 */
public interface TypeConverter {

	/**
	 * Converts the value to te expected {@link Type} (see
	 * {@link ReflectionUtils#getParametricType(Class, Type...) for complex types}).<br>
	 * <br>
	 * This method is based on registered converters to work:<br>
	 * - A {@link SpecializedTypeConverter} will be searched for using source and detintation types.<br>
	 * - If none found, source and destination hierarchy will be searched for a
	 * {@link SpecializedTypeConverter} to match.<br>
	 * - If none found, a {@link GeneralTypeConverter} will be searched for. Looking from the first
	 * registered to the last. - If none converter can be found a {@link CannotConvertException}
	 * will be raised.<br>
	 * <br>
	 * A {@link TypeConverter} should try to mantain object identity if no conversion is needed.<br>
	 * 
	 * @param <T>
	 *            Expected object type
	 * @param value
	 *            Input object to convert
	 * @param expectedType
	 *            {@link Type} expected from the conversion. It can be a simple class, o a
	 *            generified type (see {@link ReflectionUtils#getParametricType(Class, Type...)} to
	 *            get a generified type
	 * @return The converted value
	 * @throws CannotConvertException
	 *             If no converter could be found or an exception raised during conversion
	 */
	public <T> T convertValue(Object value, Type expectedType) throws CannotConvertException;

	/**
	 * Este metodo es especial para {@link Bean2Bean} y permite agregar annotations de contexto al
	 * conversor utilizado, de manera que pueda tomar decisiones en base la informacion de los
	 * annotations pasados.<br>
	 * Este método no tiene mucha utilidad para usarlo directamente desde codigo cliente de este
	 * interfaz.
	 * 
	 * @param <T>
	 *            Tipo del objeto esperado (deberia ser el mismo que el pasado como expectedType)
	 * @param value
	 *            Objeto a convertir
	 * @param expectedType
	 *            Tipo esperado
	 * @param contextAnnotations
	 *            Annotations adicionales para la conversion
	 * @return El valor convertido al tipo esperado
	 */
	public <T> T convertValue(Object value, Type expectedType, Annotation[] contextAnnotations);

	/**
	 * Facility method for plain classes as expected types.<br>
	 * This method only adds a type matching result
	 * 
	 * @param <T>
	 *            Expected object type
	 * @param expectedClass
	 *            Class instance that represents the expected type
	 * @param value
	 *            Objecto to convert
	 * @return Converted object
	 * @throws CannotConvertException
	 *             If no converter could be found or an exception raised during conversion
	 */
	public <T> T convertValueToClass(Class<T> expectedClass, Object value) throws CannotConvertException;

	/**
	 * Gets a {@link GeneralTypeConverter} identified by name
	 * 
	 * @param name
	 *            Name used to register the converter on this instance
	 * @return Previously registered converter o null if none is found under that name
	 */
	public GeneralTypeConverter<Object, Object> getGeneralConverterByName(String name);

	/**
	 * Gets a {@link SpecializedTypeConverter} identified by name
	 * 
	 * @param name
	 *            Name used to register the converter on this instance
	 * @return Previously registered converter o null if none is found under that name
	 */
	public SpecializedTypeConverter<Object, Object> getSpecializedConverterByName(String name);

	/**
	 * Adds a {@link GeneralTypeConverter} to this instance using its class name as converter name
	 * 
	 * @param converter
	 *            Converter added to the resources of this {@link TypeConverter}
	 */
	public void registerGeneralConverter(GeneralTypeConverter<?, ?> converter);

	/**
	 * Adds a {@link GeneralTypeConverter} to this instance using specified name as identifier
	 * 
	 * @param name
	 *            Name used to identify the given converter
	 * @param converter
	 *            Converter added to the resources of this {@link TypeConverter}
	 */
	public void registerGeneralConverter(String name, GeneralTypeConverter<?, ?> converter);

	/**
	 * Adds a {@link SpecializedTypeConverter} that can be used in conversion from sourceType to
	 * destinationType and its subclasses.<br>
	 * Converter class name is used as identifier
	 * 
	 * @param <S>
	 *            Type used as input for the converter
	 * @param sourceType
	 *            Class instance of converter input objects. (Can be class or interface)
	 * @param destinationType
	 *            Type for the expected converted object
	 * @param converter
	 *            Converter to use in the conversion
	 */
	public <S> void registerSpecializedConverterFor(Class<S> sourceType, Type destinationType,
			SpecializedTypeConverter<? super S, ?> converter);

	/**
	 * Adds a {@link SpecializedTypeConverter} that can be used in conversion from sourceType to
	 * destinationType and its subclasses.<br>
	 * Converter class name is used as identifier
	 * 
	 * @param <S>
	 *            Type used as input for the converter
	 * @param name
	 *            Name used to identify the converter
	 * @param sourceType
	 *            Class instance of converter input objects. (Can be class or interface)
	 * @param destinationType
	 *            Type for the expected converted object
	 * @param converter
	 *            Converter to use in the conversion
	 */
	public <S> void registerSpecializedConverterFor(String name, Class<S> sourceType, Type destinationType,
			SpecializedTypeConverter<? super S, ?> converter);

	/**
	 * Gets the object factory to use for this type converter.
	 * 
	 * @return The object factory that sub converters should use
	 */
	public ObjectFactory getObjectFactory();

	/**
	 * Sets the defined ObjectFactory to use for this type converter
	 */
	public void setObjectFactory(ObjectFactory objectFactory);
}