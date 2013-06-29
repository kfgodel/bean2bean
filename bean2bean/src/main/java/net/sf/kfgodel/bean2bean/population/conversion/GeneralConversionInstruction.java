/**
 * Created on: 15/01/2009 18:21:15 by: Dario L. Garcia
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;

import net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter;
import net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter;
import net.sf.kfgodel.bean2bean.conversion.TypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.bean2bean.exceptions.MissingPropertyException;
import net.sf.kfgodel.bean2bean.population.PopulationType;
import net.sf.kfgodel.bean2bean.population.conversion.TypeConverterCall.GenericConverterCall;
import net.sf.kfgodel.bean2bean.population.conversion.TypeConverterCall.SpecializedConverterCall;


/**
 * Esta clase representa la instruccion de Conversion del valor obtenido tomada desde el annotation
 * de configuracion
 * 
 * @author D.Garcia
 * @since 15/01/2009
 */
public class GeneralConversionInstruction implements ConversionInstruction {

	private ExpectedTypeExtractor typeExtractor;
	private TypeConverterCall valueConverter;
	private PopulationType populationType;
	private Annotation[] contextAnnotations;
	private String conversorName;
	private TypeConverterCall converterCall;

	public TypeConverterCall getConverterCall() {
		return converterCall;
	}

	public ExpectedTypeExtractor getTypeExtractor() {
		return typeExtractor;
	}

	public void setTypeExtractor(ExpectedTypeExtractor typeExtractor) {
		this.typeExtractor = typeExtractor;
	}

	public TypeConverterCall getValueConverter(TypeConverter baseConverter) {
		if (valueConverter == null) {
			valueConverter = obtainValueConverterFrom(baseConverter);
		}
		return valueConverter;
	}

	/**
	 * Obtiene el conversor a partir del conversor base pasada utilizando el nombre indicado para
	 * encontrarlo
	 * 
	 * @param baseConverter
	 *            Conversor base a utilizar
	 * @param originalValue
	 *            Valor a converitr
	 * @return El conversor correspondiente
	 */
	private TypeConverterCall obtainValueConverterFrom(TypeConverter baseConverter) {
		if ("".equals(getConversorName())) {
			return new GenericConverterCall(baseConverter);
		}
		SpecializedTypeConverter<Object, Object> specializedConverter = baseConverter
				.getSpecializedConverterByName(getConversorName());
		if (specializedConverter != null) {
			return new SpecializedConverterCall(specializedConverter);
		}

		GeneralTypeConverter<Object, Object> generalConverter = baseConverter
				.getGeneralConverterByName(getConversorName());
		if (generalConverter != null) {
			return getPopulationType().getRelatedValueConverter(generalConverter);
		}
		return null;
	}

	public void setValueConverter(TypeConverterCall valueConverter) {
		this.valueConverter = valueConverter;
	}

	public String getConversorName() {
		return conversorName;
	}

	public void setConversorName(String conversorName) {
		this.conversorName = conversorName;
	}

	public Annotation[] getContextAnnotations() {
		return contextAnnotations;
	}

	public void setContextAnnotations(Annotation[] contextAnnotations) {
		this.contextAnnotations = contextAnnotations;
	}

	/**
	 * @see net.sf.kfgodel.bean2bean.population.conversion.ConversionInstruction#applyOn(java.lang.Object,
	 *      net.sf.kfgodel.bean2bean.conversion.TypeConverter)
	 */
	public Object applyOn(Object originalValue, TypeConverter typeConverter, Object destination)
			throws MissingPropertyException {
		Type expectedType = this.getTypeExtractor().extractExpectedTypeFrom(destination);
		TypeConverterCall selectedConverterCall = this.getValueConverter(typeConverter);
		if (selectedConverterCall == null) {
			throw new CannotConvertException("A converter was not found for the name[" + getConversorName() + "]",
					originalValue, expectedType);
		}
		Object converted = selectedConverterCall.convertValue(originalValue, expectedType, contextAnnotations);
		return converted;
	}

	public static GeneralConversionInstruction create(ExpectedTypeExtractor typeExtractor, Annotation[] annotations,
			String alternativeConversor, PopulationType populationType) {
		GeneralConversionInstruction instruction = new GeneralConversionInstruction();
		instruction.setContextAnnotations(annotations);
		instruction.setTypeExtractor(typeExtractor);
		instruction.setConversorName(alternativeConversor);
		instruction.setPopulationType(populationType);
		return instruction;
	}

	public void setPopulationType(PopulationType populationType) {
		this.populationType = populationType;
	}

	public PopulationType getPopulationType() {
		return populationType;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(getClass().getSimpleName());
		builder.append("[ extractor:");
		builder.append(getTypeExtractor());
		builder.append(", converter:");
		builder.append(valueConverter);
		builder.append(", name:\"");
		builder.append(getConversorName());
		builder.append("\", population:");
		builder.append(getPopulationType());
		builder.append(", annots:");
		builder.append(Arrays.toString(getContextAnnotations()));
		builder.append(" ]");
		return builder.toString();
	}

}
