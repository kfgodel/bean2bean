/**
 * 18/07/2013 12:02:56 Copyright (C) 2011 10Pines S.R.L.
 */
package net.sf.kfgodel.bean2bean.testbeans.nested;

import net.sf.kfgodel.bean2bean.annotations.CopyTo;
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;

/**
 * Esta clase mapea las propiedades salientes con null
 * 
 * @author D. García
 */
public class ObjetoAnidadoToCopyToNull {

	@CopyTo(value = ObjetoAnidado.nested_FIELD + "." + ObjetoAnidado.cadena_FIELD, whenMissing = MissingPropertyAction.TREAT_AS_NULL)
	private String cadena;

	public String getCadena() {
		return cadena;
	}

	public void setCadena(final String cadena) {
		this.cadena = cadena;
	}

	public static ObjetoAnidadoToCopyToNull create(final String cadena) {
		final ObjetoAnidadoToCopyToNull objeto = new ObjetoAnidadoToCopyToNull();
		objeto.cadena = cadena;
		return objeto;
	}
}
