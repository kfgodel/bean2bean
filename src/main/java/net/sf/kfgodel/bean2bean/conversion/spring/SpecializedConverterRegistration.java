/**
 * Created on: 21/08/2008 12:07:52 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.conversion.spring;

import net.sf.kfgodel.bean2bean.conversion.SpecializedTypeConverter;

/**
 * Esta clase representa una definicion de registro para un conversor agregado al conversor de
 * tipos. Esta clase facilita la definicion de bean en spring. Si no se define un nombre se usa el
 * nombre de la clase conversora
 * 
 * @author Dario Garcia
 */
public class SpecializedConverterRegistration<S> {
	private String registrationName;
	private Class<S> sourceType;
	private Class<?> destinationType;
	private SpecializedTypeConverter<? super S, ?> converter;

	public String getRegistrationName() {
		if (registrationName == null) {
			registrationName = getConverter().getClass().getCanonicalName();
		}
		return registrationName;
	}

	public void setRegistrationName(String registrationName) {
		this.registrationName = registrationName;
	}

	public Class<S> getSourceType() {
		return sourceType;
	}

	public void setSourceType(Class<S> sourceType) {
		this.sourceType = sourceType;
	}

	public Class<?> getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(Class<?> destinationType) {
		this.destinationType = destinationType;
	}

	public SpecializedTypeConverter<? super S, ?> getConverter() {
		return converter;
	}

	public void setConverter(SpecializedTypeConverter<? super S, ?> converter) {
		this.converter = converter;
	}
}
