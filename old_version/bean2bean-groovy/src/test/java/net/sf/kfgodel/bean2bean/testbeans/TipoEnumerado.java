/**
 * Created on 20/12/2007 21:40:35 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.testbeans;


/**
 * Este enum permite el testeo de copias de datos usando enums
 * 
 * @version 1.0
 * @since 20/12/2007
 * @author D. Garcia
 */
public enum TipoEnumerado {
	NORTE, SUR;

	/**
	 * @return Codigo por el cual se identifica a cada enum externamente
	 */
	public String getCodigo() {
		return this.name() + this.ordinal();
	}

	/**
	 * Devuelve el enum correspondiente a partir del codigo
	 * 
	 * @param codigo
	 *            Codigo que identifica al enum
	 * @return EL enum correspondiente o null
	 */
	public static TipoEnumerado getTipoFrom(String codigo) {
		TipoEnumerado[] values = values();
		for (TipoEnumerado tipoEnumerado : values) {
			if (tipoEnumerado.getCodigo().equals(codigo)) {
				return tipoEnumerado;
			}
		}
		return null;
	}
}
