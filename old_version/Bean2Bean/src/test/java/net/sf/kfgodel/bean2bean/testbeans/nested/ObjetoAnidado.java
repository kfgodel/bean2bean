/**
 * 18/07/2013 11:46:17 Copyright (C) 2011 10Pines S.R.L.
 */
package net.sf.kfgodel.bean2bean.testbeans.nested;

/**
 * Esta clase representa un objeto que tiene propiedades anidadas
 * 
 * @author D. García
 */
public class ObjetoAnidado {

	public static final String DEFAULT_CADENA = "Creado";

	private ObjetoAnidado nested;
	public static final String nested_FIELD = "nested";

	private String cadena;
	public static final String cadena_FIELD = "cadena";

	public ObjetoAnidado() {
		this.cadena = DEFAULT_CADENA;
	}

	public ObjetoAnidado getNested() {
		return nested;
	}

	public void setNested(final ObjetoAnidado nested) {
		this.nested = nested;
	}

	public String getCadena() {
		return cadena;
	}

	public void setCadena(final String cadena) {
		this.cadena = cadena;
	}

	public static ObjetoAnidado create(final String cadena) {
		final ObjetoAnidado objeto = new ObjetoAnidado();
		objeto.cadena = cadena;
		return objeto;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "{cadena: " + cadena + ", nested: " + nested + "}";
	}
}
