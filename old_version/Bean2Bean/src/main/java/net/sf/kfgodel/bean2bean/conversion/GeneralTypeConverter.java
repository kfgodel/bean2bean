/**
 * Created on 29/12/2007 02:48:05 Copyright (C) 2007 Dario L. Garcia
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

/**
 * Esta interfaz representa un conversor de tipos que puede tratar las dos operaciones de conversion
 * (es decir, en ambas direcciones). Y mas de un tipo a la vez.
 * 
 * @version 1.0
 * @since 29/12/2007
 * @author D. Garcia
 * @param <S>
 *            Tipo de la conversion S FROM D
 * @param <D>
 *            Tipo de la conversion S TO D
 */
public interface GeneralTypeConverter<S, D> {

	/**
	 * Indica si este conversor puede convertir un valor desde el tipo indicado como fuente al
	 * destino
	 * 
	 * @param sourceType
	 *            Tipo del objeto indicado
	 * @param expectedType
	 *            Tipo esperado como resultado
	 * @param sourceObject
	 *            Objeto desde el que se realizaria la conversion
	 * @return true si es seguro asignarle la conversion a este conversor
	 */
	boolean acceptsConversionFrom(Class<?> sourceType, Type expectedType, Object sourceObject);

	/**
	 * Indica si este conversor puede convertir un valor hacia el tipo esperado, desde el tipo
	 * fuente indicado
	 * 
	 * @param expectedType
	 *            Tipo esperado de la conversion
	 * @param sourceType
	 *            Tipo fuente de la conversion
	 * @param sourceObject
	 *            Objeto desde el que se realizaria la conversion
	 * @return true si es seguro asignarle la conversion a este conversor
	 */
	boolean acceptsConversionTo(Type expectedType, Class<?> sourceType, Object sourceObject);

	/**
	 * Convierte desde el tipo S el objeto pasado al tipo D
	 * 
	 * @param value
	 *            Objeto a convertir
	 * @param expectedType
	 *            tipo esperado de la conversion
	 * @param contextAnnotations
	 *            Annotations que sirven de contexto a la conversion Este parametro es necesario
	 *            solo para algunas conversiones y es opcional. Si no se encontraron annotations en
	 *            el contexto de conversion se pasara null.
	 * @return El objeto convertido
	 */
	D convertFrom(S value, Type expectedType, Annotation[] contextAnnotations);

	/**
	 * Convierte el objeto pasado desde el tipo D al tipo S
	 * 
	 * @param expectedType
	 *            Tipo esperado de la conversion
	 * @param value
	 *            Objeto original
	 * @param contextAnnotations
	 *            Annotations que sirven de contexto a la conversion Este parametro es necesario
	 *            solo para algunas conversiones y es opcional. Si no se encontraron annotations en
	 *            el contexto de conversion se pasara null.
	 * @return El objeto convertido
	 */
	S convertTo(Type expectedType, D value, Annotation[] contextAnnotations);

}
