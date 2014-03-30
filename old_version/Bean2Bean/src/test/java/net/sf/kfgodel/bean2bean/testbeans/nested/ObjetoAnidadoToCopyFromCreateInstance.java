/**
 * 18/07/2013 11:58:44 Copyright (C) 2011 10Pines S.R.L.
 */
package net.sf.kfgodel.bean2bean.testbeans.nested;

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;

/**
 * Esta clase mapea la propiedad creando instancias intermedias
 * 
 * @author D. García
 */
public class ObjetoAnidadoToCopyFromCreateInstance {

	@CopyFrom(value = ObjetoAnidado.nested_FIELD + "." + ObjetoAnidado.cadena_FIELD, whenMissing = MissingPropertyAction.CREATE_MISSING_INSTANCES)
	private String cadena;

	public String getCadena() {
		return cadena;
	}

	public void setCadena(final String cadena) {
		this.cadena = cadena;
	}

}
