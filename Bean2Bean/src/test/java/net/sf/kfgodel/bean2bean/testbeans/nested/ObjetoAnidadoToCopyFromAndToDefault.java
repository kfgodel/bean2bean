/**
 * 18/07/2013 12:05:41 Copyright (C) 2011 10Pines S.R.L.
 */
package net.sf.kfgodel.bean2bean.testbeans.nested;

import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;

/**
 * Esta clase mapea una propiedad anidada bidireccionalmente con el comportamiento default
 * 
 * @author D. García
 */
public class ObjetoAnidadoToCopyFromAndToDefault {

	@CopyFromAndTo(value = ObjetoAnidado.nested_FIELD + "." + ObjetoAnidado.cadena_FIELD)
	private String cadena;

	public String getCadena() {
		return cadena;
	}

	public void setCadena(final String cadena) {
		this.cadena = cadena;
	}

	public static ObjetoAnidadoToCopyFromAndToDefault create(final String cadena) {
		final ObjetoAnidadoToCopyFromAndToDefault objeto = new ObjetoAnidadoToCopyFromAndToDefault();
		objeto.cadena = cadena;
		return objeto;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "{cadena: " + cadena + "}";
	}
}
