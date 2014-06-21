/**
 * 18/07/2013 12:05:48 Copyright (C) 2011 10Pines S.R.L.
 */
package net.sf.kfgodel.bean2bean.testbeans.nested;

import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;

/**
 * Esta clase mapea bidireccionalmente una propiedad anidada con null
 * 
 * @author D. García
 */
public class ObjetoAnidadoToCopyFromAndToNull {

	@CopyFromAndTo(value = ObjetoAnidado.nested_FIELD + "." + ObjetoAnidado.cadena_FIELD, whenMissing = MissingPropertyAction.TREAT_AS_NULL)
	private String cadena;

	public String getCadena() {
		return cadena;
	}

	public void setCadena(final String cadena) {
		this.cadena = cadena;
	}

	public static ObjetoAnidadoToCopyFromAndToNull create(final String cadena) {
		final ObjetoAnidadoToCopyFromAndToNull objeto = new ObjetoAnidadoToCopyFromAndToNull();
		objeto.cadena = cadena;
		return objeto;
	}
}
