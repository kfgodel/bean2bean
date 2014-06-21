/**
 * Created on 29/12/2007 19:19:20 Copyright (C) 2007 Dario L. Garcia
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
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Iterator;

import net.sf.kfgodel.bean2bean.Bean2Bean;
import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;
import net.sf.kfgodel.bean2bean.annotations.CopyTo;
import net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.dgarcia.lang.iterators.basic.ConditionalIterator;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;
import net.sf.kfgodel.dgarcia.lang.reflection.conditions.AnnotatedCondition;

/**
 * Esta clase sabe como convertir desde y hacia objetos annotados con los annotations
 * {@link CopyFrom}, {@link CopyTo} y {@link CopyFromAndTo}
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 */
public class AnnotatedClassConverter implements GeneralTypeConverter<Object, Object> {

	/**
	 * Generador de los beans para estructuras recursivas
	 */
	private Bean2Bean beanGenerator;

	public Bean2Bean safeGetBeanGenerator() {
		if (beanGenerator == null) {
			throw new IllegalStateException("There's no instance of bean2bean defined for the " + getClass()
					+ " instance, and we need one to do the conversions");
		}
		return beanGenerator;
	}

	public Bean2Bean getBeanGenerator() {
		return beanGenerator;
	}

	public void setBeanGenerator(final Bean2Bean beanGenerator) {
		this.beanGenerator = beanGenerator;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertFrom(java.lang.Object,
	 *      java.lang.reflect.Type, java.lang.annotation.Annotation[])
	 */
	public Object convertFrom(final Object value, final Type expectedType, final Annotation[] contextAnnotations) {
		if (expectedType == null) {
			throw new CannotConvertException("Cannot make conversion. Expected type was not defined", value,
					expectedType);
		}
		final Class<?> annotatedClass = ReflectionUtils.degenerify(expectedType);
		if (annotatedClass == null) {
			throw new CannotConvertException("Passed-in argument type[" + expectedType
					+ "] does not match expected type", value, expectedType);
		}
		final Object convertedValue = this.safeGetBeanGenerator().createFrom(value, annotatedClass);
		return convertedValue;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#convertTo(java.lang.reflect.Type,
	 *      java.lang.Object, java.lang.annotation.Annotation[])
	 */
	public Object convertTo(final Type expectedType, final Object value, final Annotation[] contextAnnotations) {
		if (expectedType == null) {
			throw new CannotConvertException("Cannot make conversion. Expected type was not defined", value,
					expectedType);
		}
		final Class<?> unAnnotatedClass = ReflectionUtils.degenerify(expectedType);
		if (unAnnotatedClass == null) {
			throw new CannotConvertException("Can not get a class instance from expected type[" + expectedType + "]",
					value, expectedType);
		}
		final Object convertedValue = this.safeGetBeanGenerator().convertTo(unAnnotatedClass, value);
		return convertedValue;
	}

	/**
	 * Indica si la clase pasada tiene atributos annotados con las annotations indicadas
	 * 
	 * @param checkedType
	 *            Tipo a comprobar por annotations
	 * @return true si la clase esta annotada y es convertible
	 */
	private boolean isAnnotatedClass(final Class<?> checkedType, final Class<? extends Annotation>... annotations) {
		final Iterator<Field> allFields = ReflectionUtils.getAllFieldsOf(checkedType);
		final AnnotatedCondition siEstaAnotado = AnnotatedCondition.create(annotations);
		final ConditionalIterator<Field> iteradorAtributosAnotados = ConditionalIterator.createFrom(siEstaAnotado,
				allFields);
		return iteradorAtributosAnotados.hasNext();
	}

	/**
	 * Crea este conversor. Se debe indicar el generador de beans a utilizar
	 * 
	 * @param beanGenerator
	 *            Si se indica null, se postergará la inicializacion utilizando la instancia
	 *            singleton
	 * @return El conversor creado
	 */
	public static AnnotatedClassConverter create(final Bean2Bean beanGenerator) {
		final AnnotatedClassConverter converter = new AnnotatedClassConverter();
		converter.setBeanGenerator(beanGenerator);
		return converter;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionFrom(java.lang.Class,
	 *      java.lang.reflect.Type, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public boolean acceptsConversionFrom(final Class<?> sourceType, final Type expectedType, final Object sourceObject) {
		final Class<?> destinationClass = ReflectionUtils.degenerify(expectedType);
		if (destinationClass == null) {
			return false;
		}
		final boolean isAnnotatedDestination = isAnnotatedClass(destinationClass, CopyFrom.class, CopyFromAndTo.class);
		return isAnnotatedDestination;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter#acceptsConversionTo(java.lang.reflect.Type,
	 *      java.lang.Class, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public boolean acceptsConversionTo(final Type expectedType, final Class<?> sourceType, final Object sourceObject) {
		final boolean isAnnotatedSource = isAnnotatedClass(sourceType, CopyTo.class, CopyFromAndTo.class);
		return isAnnotatedSource;
	}

}
