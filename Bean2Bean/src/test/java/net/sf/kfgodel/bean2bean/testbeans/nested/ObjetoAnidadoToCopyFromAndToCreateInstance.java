/**
 * 18/07/2013 12:05:56 Copyright (C) 2011 10Pines S.R.L.
 */
package net.sf.kfgodel.bean2bean.testbeans.nested;

import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;

/**
 * Esta clase mapea una propiedad bidireccional creando las instancias intermedias
 * 
 * @author D. García
 */
public class ObjetoAnidadoToCopyFromAndToCreateInstance {

	@CopyFromAndTo(value = ObjetoAnidado.nested_FIELD + "." + ObjetoAnidado.cadena_FIELD, whenMissing = MissingPropertyAction.CREATE_MISSING_INSTANCES)
	private String cadena;

	public String getCadena() {
		return cadena;
	}

	public void setCadena(final String cadena) {
		this.cadena = cadena;
	}

	public static ObjetoAnidadoToCopyFromAndToCreateInstance create(final String cadena) {
		final ObjetoAnidadoToCopyFromAndToCreateInstance objeto = new ObjetoAnidadoToCopyFromAndToCreateInstance();
		objeto.cadena = cadena;
		return objeto;
	}
}
