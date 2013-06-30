/**
 * Created on: 15/01/2009 14:56:35 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.population.instructions;

import javax.lang.model.type.NullType;

import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;
import net.sf.kfgodel.bean2bean.conversion.TypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.bean2bean.exceptions.MissingPropertyException;
import net.sf.kfgodel.bean2bean.exceptions.StopPopulationException;
import net.sf.kfgodel.bean2bean.exceptions.TypeExtractionFailedException;
import net.sf.kfgodel.bean2bean.population.conversion.ConversionInstruction;
import net.sf.kfgodel.bean2bean.population.getting.GetterInstruction;
import net.sf.kfgodel.bean2bean.population.setting.SetterInstruction;

/**
 * Esta clase representa una instrccion de como popular un bean, compuesta de tres partes:<br>
 * Como obtener el valor<br>
 * Como convertirlo en el tipo esperado<br>
 * Como asignarlo en la propiedad destino
 * 
 * @author D.Garcia
 * @since 15/01/2009
 */
public class GeneralPopulationInstruction implements PopulationInstruction {

	private GetterInstruction getterInstruction;
	private ConversionInstruction conversionInstruction;
	private SetterInstruction setterInstruction;
	private MissingPropertyAction missingPropertyAction;

	public GetterInstruction getGetterInstruction() {
		return getterInstruction;
	}

	public void setGetterInstruction(final GetterInstruction getterInstruction) {
		this.getterInstruction = getterInstruction;
	}

	public ConversionInstruction getConversionInstruction() {
		return conversionInstruction;
	}

	public void setConversionInstruction(final ConversionInstruction conversionInstruction) {
		this.conversionInstruction = conversionInstruction;
	}

	public SetterInstruction getSetterInstruction() {
		return setterInstruction;
	}

	public void setSetterInstruction(final SetterInstruction setterInstruction) {
		this.setterInstruction = setterInstruction;
	}

	public static GeneralPopulationInstruction create(final GetterInstruction getterInstruction,
			final ConversionInstruction conversionInstruction, final SetterInstruction setterInstruction,
			final MissingPropertyAction action) {
		final GeneralPopulationInstruction instruction = new GeneralPopulationInstruction();
		instruction.setGetterInstruction(getterInstruction);
		instruction.setConversionInstruction(conversionInstruction);
		instruction.setSetterInstruction(setterInstruction);
		instruction.setMissingPropertyAction(action);
		return instruction;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.population.instructions.PopulationInstruction#applyOn(java.lang.Object,
	 *      java.lang.Object, net.sf.kfgodel.bean2bean.conversion.TypeConverter)
	 */
	public void applyOn(final Object source, final Object destination, final TypeConverter typeConverter) {
		Object value;
		try {
			value = this.getGetterInstruction().applyOn(source);
		} catch (final MissingPropertyException e) {
			// Puede estar configurado para asignar null si no existe la propiedad origen
			value = this.getMissingPropertyAction().dealWithMissingPropertyOnGetter(e);
		}
		Object convertedValue;
		try {
			convertedValue = this.getConversionInstruction().applyOn(value, typeConverter, destination);
		} catch (final MissingPropertyException e) {
			try {
				convertedValue = this.getMissingPropertyAction().dealWithMissingPropertyOnConversion(e);
			} catch (final StopPopulationException e1) {
				// Esta configurado para que no se produzca un error si no existe la propiedad
				// destino
				return;
			}
		} catch (final TypeExtractionFailedException e) {
			Class<?> expectedType = NullType.class;
			if (destination != null) {
				expectedType = destination.getClass();
			}
			throw new CannotConvertException("Couldn't convert value from[" + source + "] to [" + destination
					+ "]. Catched a " + e.getClass().getName() + ": " + e.getMessage(), source, expectedType, e);
		}
		try {
			this.getSetterInstruction().applyOn(destination, convertedValue);
		} catch (final MissingPropertyException e) {
			try {
				this.getMissingPropertyAction().dealWithMissingPropertyOnSetter(e, getSetterInstruction(), destination);
			} catch (final StopPopulationException e1) {
				// Esta configurado para que no se produzca un error si no existe la propiedad
				// destino
				return;
			}
		}
	}

	public MissingPropertyAction getMissingPropertyAction() {
		return missingPropertyAction;
	}

	public void setMissingPropertyAction(final MissingPropertyAction missingPropertyAction) {
		this.missingPropertyAction = missingPropertyAction;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		builder.append("[ getter:[");
		builder.append(getGetterInstruction());
		builder.append("], setter:[");
		builder.append(getSetterInstruction());
		builder.append("], conversion:[");
		builder.append(getConversionInstruction());
		builder.append("], whenMissing:");
		builder.append(getMissingPropertyAction());
		builder.append(" ]");
		return builder.toString();
	}
}
