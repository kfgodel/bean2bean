/**
 * Created on 25/12/2007 21:55:38 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.population.instructions;

import net.sf.kfgodel.bean2bean.conversion.TypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.bean2bean.population.conversion.ConversionInstruction;
import net.sf.kfgodel.bean2bean.population.getting.GetterInstruction;
import net.sf.kfgodel.bean2bean.population.setting.SetterInstruction;

/**
 * Esta interfaz representa una instruccion de populacion que sabe como obtener el dato de un objeto
 * y asignarlo a otro
 * 
 * @version 1.0
 * @since 25/12/2007
 * @author D. Garcia
 */
public interface PopulationInstruction {

	/**
	 * @return Sub-instruccion que sabe como obtener el valor
	 */
	GetterInstruction getGetterInstruction();

	/**
	 * @return Sub-intruccion que sabe como convertir el valor al tipo esperado
	 */
	ConversionInstruction getConversionInstruction();

	/**
	 * @return Sub-instruccion que sabe como asignar el valor en destino
	 */
	SetterInstruction getSetterInstruction();

	/**
	 * Aplica esta instruccion para popular los objetos pasados
	 * 
	 * @param source
	 *            Objeto origen de la populacion, sobre el que se aplicará el getter
	 * @param destination
	 *            Objeto sobre el que se aplicara el setter
	 * @param typeConverter
	 *            Conversor de tipo para la conversion
	 * @throws CannotConvertException
	 *             Si existe un error en la conversion
	 */
	void applyOn(Object source, Object destination, TypeConverter typeConverter);

}
