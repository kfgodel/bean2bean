/**
 * Created on: 15/01/2009 15:04:08 by: Dario L. Garcia
 * 
 * * <a rel="license" href="http://creativecommons.org/licenses/by/2.5/ar/"><img
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
package net.sf.kfgodel.bean2bean.population.conversion;

import net.sf.kfgodel.bean2bean.conversion.TypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.MissingPropertyException;
import net.sf.kfgodel.bean2bean.exceptions.TypeExtractionFailedException;

/**
 * Esta instruccion indica como convertir el valor al tipo esperado
 * 
 * @author D.Garcia
 * @since 15/01/2009
 */
public interface ConversionInstruction {

	/**
	 * Realiza la conversion del tipo segun esta instruccion
	 * 
	 * @param value
	 *            Valor a convertir
	 * @param typeConverter
	 *            Conversor de tipos usado como base para la conversino
	 * @param destination
	 *            Objeto sobre el que se hara la asignacion que permite obtener informacion
	 *            adicional para la conversion
	 * @return El valor convertido
	 * @throws MissingPropertyException
	 *             Si la propiedad necesaria para aplicar la conversion no está disponible
	 * @throws TypeExtractionFailedException
	 *             Si se produce un error navegando los objetos para definir el tipo esperado
	 */
	Object applyOn(Object value, TypeConverter typeConverter, Object destination) throws MissingPropertyException,
			TypeExtractionFailedException;

}
