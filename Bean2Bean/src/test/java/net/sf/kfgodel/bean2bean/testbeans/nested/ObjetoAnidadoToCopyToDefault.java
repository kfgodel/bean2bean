/**
 * 18/07/2013 12:02:33 Copyright (C) 2011 10Pines S.R.L.
 */
package net.sf.kfgodel.bean2bean.testbeans.nested;

import net.sf.kfgodel.bean2bean.annotations.CopyTo;

/**
 * Esta clase mapea la propiedad anidada con el comportamiento default
 * 
 * @author D. García
 */
public class ObjetoAnidadoToCopyToDefault {

	@CopyTo(value = ObjetoAnidado.nested_FIELD + "." + ObjetoAnidado.cadena_FIELD)
	private String cadena;

	public String getCadena() {
		return cadena;
	}

	public void setCadena(final String cadena) {
		this.cadena = cadena;
	}

	public static ObjetoAnidadoToCopyToDefault create(final String cadena) {
		final ObjetoAnidadoToCopyToDefault objeto = new ObjetoAnidadoToCopyToDefault();
		objeto.cadena = cadena;
		return objeto;
	}
}
