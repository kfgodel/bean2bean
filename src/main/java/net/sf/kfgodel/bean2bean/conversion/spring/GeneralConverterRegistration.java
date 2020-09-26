/**
 * Created on: 21/08/2008 12:21:01 by: Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.conversion.GeneralTypeConverter;

/**
 * Esta clase permite registrar un conversor general a traves de una configuracion facilitada de
 * spring, utilizando un nombre para desambiguar
 * 
 * @author Dario Garcia
 */
public class GeneralConverterRegistration {
	private String nameRegistration;
	private GeneralTypeConverter<?, ?> converter;

	public String getNameRegistration() {
		return nameRegistration;
	}

	public void setNameRegistration(String nameRegistration) {
		this.nameRegistration = nameRegistration;
	}

	public GeneralTypeConverter<?, ?> getConverter() {
		return converter;
	}

	public void setConverter(GeneralTypeConverter<?, ?> converter) {
		this.converter = converter;
	}
}
