/**
 * Created on: 16/02/2010 20:41:38 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.population.conversion;

import net.sf.kfgodel.bean2bean.conversion.TypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.MissingPropertyException;

/**
 * Esta clase es una instruccion que no realiza ninguna conversion. Se utiliza para expresiones
 * sobre las cuales no es posible evaluar los tipos de origen o destino. En el caso de OGNL, las
 * 
 * @author D. Garcia
 */
public class NonConversionInstruction implements ConversionInstruction {

	/**
	 * @see net.sf.kfgodel.bean2bean.population.conversion.ConversionInstruction#applyOn(java.lang.Object,
	 *      net.sf.kfgodel.bean2bean.conversion.TypeConverter, java.lang.Object)
	 */
	public Object applyOn(Object value, TypeConverter typeConverter, Object destination)
			throws MissingPropertyException {
		return value;
	}

}
