/**
 * 24/05/2013 22:55:09 Copyright (C) 2011 Darío L. García
 * 
 * <a rel="license" href="http://creativecommons.org/licenses/by/3.0/"><img
 * alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/by/3.0/88x31.png" /></a><br />
 * <span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/Text"
 * property="dct:title" rel="dct:type">Software</span> by <span
 * xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Darío García</span> is
 * licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/3.0/">Creative
 * Commons Attribution 3.0 Unported License</a>.
 */
package net.sf.kfgodel.bean2bean.testbeans;

/**
 * Clase no anotada para verificar conversiones a String
 * 
 * @author D. García
 */
public class NonAnnotatedBean {

	private String stringValue;

	private Long longValue;

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(final String stringValue) {
		this.stringValue = stringValue;
	}

	public Long getLongValue() {
		return longValue;
	}

	public void setLongValue(final Long longValue) {
		this.longValue = longValue;
	}

	public static NonAnnotatedBean create(final String stringValue, final Long longValue) {
		final NonAnnotatedBean bean = new NonAnnotatedBean();
		bean.longValue = longValue;
		bean.stringValue = stringValue;
		return bean;
	}
}
