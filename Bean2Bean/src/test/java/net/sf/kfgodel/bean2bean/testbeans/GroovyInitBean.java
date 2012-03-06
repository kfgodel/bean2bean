/**
 * 06/03/2012 14:06:12 Copyright (C) 2011 Darío L. García
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

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;

/**
 * Esta clase define un mapeo con groovy para testear los cambios al inicializar
 * 
 * @author D. García
 */
public class GroovyInitBean {

	@CopyFrom(value = "texto", getterInterpreter = InterpreterType.GROOVY)
	private String texto;

	public String getTexto() {
		return texto;
	}

	public void setTexto(final String texto) {
		this.texto = texto;
	}

	public static GroovyInitBean create(final String texto) {
		final GroovyInitBean name = new GroovyInitBean();
		name.texto = texto;
		return name;
	}
}
