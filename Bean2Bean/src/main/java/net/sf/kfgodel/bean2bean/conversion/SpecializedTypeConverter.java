/**
 * Created on 24/12/2007 18:32:22 Copyright (C) 2007 Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;


/**
 * Interfaz que representa un conversor especializado de un tipo a otro.<br>
 * Este conversor es unidireccional, solo sabe convertir del tipo A al B, pero no al reves.
 * 
 * @version 1.0
 * @since 24/12/2007
 * @author D. Garcia
 * @param <S>
 *            Tipo del objeto tomado como fuente de la conversion
 * @param <D>
 *            Tipo del objeto destino de la conversion
 */
public interface SpecializedTypeConverter<S, D> {
	/**
	 * Convierte el objeto pasado al tipo esperado
	 * 
	 * @param expectedType
	 *            Tipo indicado como esperado de la conversion
	 * @param sourceObject
	 *            Objeto del tipo origen que debe ser convertido al tipo destino
	 * @param contextAnnotations
	 *            Annotations que sirven de contexto a la conversion Este parametro es necesario
	 *            solo para algunas conversiones y es opcional. Si no se encontraron annotations en
	 *            el contexto de conversion se pasara null.
	 * @return El objeto del tipo esperado que representa al mismo objeto pasado
	 * @throws CannotConvertException
	 *             Si la conversion no puede ser realizada con los parametros pasados
	 */
	public D convertTo(Type expectedType, S sourceObject, Annotation[] contextAnnotations)
			throws CannotConvertException;
}
