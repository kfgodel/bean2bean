/**
 * 18/07/2013 11:58:20 Copyright (C) 2011 10Pines S.R.L.
 */
package net.sf.kfgodel.bean2bean.testbeans.nested;

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;

/**
 * Esta clase mapea la propiedad anidada con el comportamiento default
 * 
 * @author D. García
 */
public class ObjetoAnidadoToCopyFromDefault {

	@CopyFrom(value = ObjetoAnidado.nested_FIELD + "." + ObjetoAnidado.cadena_FIELD)
	private String cadena;

	public String getCadena() {
		return cadena;
	}

	public void setCadena(final String cadena) {
		this.cadena = cadena;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{cadena: " + cadena + "}";
	}
}
