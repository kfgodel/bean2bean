/**
 * 18/07/2013 12:03:10 Copyright (C) 2011 10Pines S.R.L.
 */
package net.sf.kfgodel.bean2bean.testbeans.nested;

import net.sf.kfgodel.bean2bean.annotations.CopyTo;
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;

/**
 * Esta clase mapea la propiedad saliente creando las instancias nulas
 * 
 * @author D. García
 */
public class ObjetoAnidadoToCopyToCreateInstance {

	@CopyTo(value = ObjetoAnidado.nested_FIELD + "." + ObjetoAnidado.cadena_FIELD, whenMissing = MissingPropertyAction.CREATE_MISSING_INSTANCES)
	private String cadena;

	public String getCadena() {
		return cadena;
	}

	public void setCadena(final String cadena) {
		this.cadena = cadena;
	}

	public static ObjetoAnidadoToCopyToCreateInstance create(final String cadena) {
		final ObjetoAnidadoToCopyToCreateInstance objeto = new ObjetoAnidadoToCopyToCreateInstance();
		objeto.cadena = cadena;
		return objeto;
	}
}
