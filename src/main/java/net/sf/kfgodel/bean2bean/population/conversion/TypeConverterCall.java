/**
 * Created on: 15/01/2009 18:31:01 by: Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter;
import net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter;
import net.sf.kfgodel.bean2bean.conversion.TypeConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;


/**
 * Esta interfaz representa un conversor de valores, que unifica las interfaces de los distintos
 * tipos de conversores
 * 
 * @author D.Garcia
 * @since 15/01/2009
 */
public interface TypeConverterCall {

	/**
	 * Realiza la conversion del valor utilizando el conversor base pasado como referencia para
	 * obtener el conversor especificado
	 * 
	 * @param originalValue
	 *            Valor a convertir
	 * @param expectedType
	 *            Tipoe esperado de la conversion
	 * @param contextAnnotations
	 *            Annotaciones auxiliares
	 * @param alternativeConversor
	 *            Nombre del conversor a utilizar
	 * @param baseTypeConverter
	 *            Conversor base desde el cual obtener el conversor indicado
	 * @return El valor convertido
	 */
	Object convertValue(Object originalValue, Type expectedType, Annotation[] contextAnnotations);

	public static class GenericConverterCall implements TypeConverterCall {
		private TypeConverter typeConverter;

		public GenericConverterCall(TypeConverter typeConverter) {
			this.typeConverter = typeConverter;
		}

		public Object convertValue(Object originalValue, Type expectedType, Annotation[] contextAnnotations) {
			Object convertedValue = typeConverter.convertValue(originalValue, expectedType, contextAnnotations);
			return convertedValue;
		}

		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}

	public static class SpecializedConverterCall implements TypeConverterCall {
		private SpecializedTypeConverter<Object, Object> specializedConverter;

		@SuppressWarnings("unchecked")
		public SpecializedConverterCall(SpecializedTypeConverter<?, ?> converter) {
			this.specializedConverter = (SpecializedTypeConverter<Object, Object>) converter;
		}

		public Object convertValue(Object originalValue, Type expectedType, Annotation[] contextAnnotations) {
			Object convertedValue = specializedConverter.convertTo(expectedType, originalValue, contextAnnotations);
			return convertedValue;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(getClass().getSimpleName());
			builder.append("[");
			builder.append(specializedConverter);
			builder.append("]");
			return builder.toString();
		}
	}

	public static class GeneralConverterFromCall implements TypeConverterCall {
		private GeneralTypeConverter<Object, Object> generalConverter;

		@SuppressWarnings("unchecked")
		public GeneralConverterFromCall(GeneralTypeConverter<?, ?> generalConverter) {
			this.generalConverter = (GeneralTypeConverter<Object, Object>) generalConverter;
		}

		public Object convertValue(Object originalValue, Type expectedType, Annotation[] contextAnnotations) {
			Object convertedValue = generalConverter.convertFrom(originalValue, expectedType, contextAnnotations);
			return convertedValue;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(getClass().getSimpleName());
			builder.append("[");
			builder.append(generalConverter);
			builder.append("]");
			return builder.toString();
		}
	}

	public static class GeneralConverterToCall implements TypeConverterCall {
		private GeneralTypeConverter<Object, Object> generalConverter;

		@SuppressWarnings("unchecked")
		public GeneralConverterToCall(GeneralTypeConverter<?, ?> generalConverter) {
			this.generalConverter = (GeneralTypeConverter<Object, Object>) generalConverter;
		}

		public Object convertValue(Object originalValue, Type expectedType, Annotation[] contextAnnotations) {
			Object convertedValue = generalConverter.convertTo(expectedType, originalValue, contextAnnotations);
			return convertedValue;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder(getClass().getSimpleName());
			builder.append("[");
			builder.append(generalConverter);
			builder.append("]");
			return builder.toString();
		}
	}

}
