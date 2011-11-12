/**
 * Created on 06/01/2008 18:56:11 Copyright (C) 2008 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Este annotation representa un patron de conversion que es informacion adicional al momento de
 * realizar la conversion.<br>
 * Este annotation sirve para especificar patrones de conversion en entidades que no tiene un tipo
 * asociado (suele suceder con {@link String}s que representan un valor numerico con cierto formato,
 * o fechas).<br>
 * Para aquellos caso este annotation puede ser agregado al atributo marcado con {@link CopyFrom},
 * {@link CopyTo}, o {@link CopyFromAndTo} y sera pasado al conversor para que sepa como formatear
 * la conversion.
 * 
 * @version 1.0
 * @since 06/01/2008
 * @author D. Garcia
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConversionPattern {
	/**
	 * Formato expresado como cadena, que es entendible para el conversor
	 * 
	 * @return El formato que debe utilizarse al realizar la conversion
	 */
	String value();
}
