/**
 * Created on: 14/01/2009 15:40:05 by: Dario L. Garcia
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
 * 
 */
package net.sf.kfgodel.bean2bean.inv;

import java.util.List;

import net.sf.kfgodel.dgarcia.java.util.Contenedores;

/**
 * Esta clase representa un origen de datos que son utilizables por las demás clases para sus
 * populaciones. Esta clase sirve para testear los tipos de populacion, y no las conversiones de
 * datos
 * 
 * @author D.Garcia
 */
public class ClaseConDatos {
	private String atributoPrivado = "privado";
	public String atributoPublico = "publico";
	private String propiedadPrimitiva = "primitiva";
	private int numero;
	private Double flotante;
	private ClaseConDatos propiedadAnidada;
	private List<ClaseConDatos> listaAnidados;
	private List<String> listaPrimitivos;

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public List<ClaseConDatos> getListaAnidados() {
		return listaAnidados;
	}

	public void setListaAnidados(List<ClaseConDatos> listaAnidados) {
		this.listaAnidados = listaAnidados;
	}

	public List<String> getListaPrimitivos() {
		return listaPrimitivos;
	}

	public void setListaPrimitivos(List<String> listaPrimitivos) {
		this.listaPrimitivos = listaPrimitivos;
	}

	public ClaseConDatos() {
	}

	public ClaseConDatos(int numero) {
		this.atributoPrivado = "privado" + numero;
		this.atributoPublico = "publico" + numero;
		this.propiedadPrimitiva = "primitiva" + numero;
		this.numero = numero;
		this.flotante = new Double(numero);
	}

	public String getPropiedadPrimitiva() {
		return propiedadPrimitiva;
	}

	public void setPropiedadPrimitiva(String propiedadPrimitiva) {
		this.propiedadPrimitiva = propiedadPrimitiva;
	}

	public ClaseConDatos getPropiedadAnidada() {
		return propiedadAnidada;
	}

	public void setPropiedadAnidada(ClaseConDatos anidado) {
		this.propiedadAnidada = anidado;
	}

	public static ClaseConDatos create() {
		ClaseConDatos instancia = new ClaseConDatos(0);
		instancia.propiedadAnidada = new ClaseConDatos(1);
		instancia.listaAnidados = Contenedores.toArrayListWith(new ClaseConDatos(2), new ClaseConDatos(3));
		instancia.listaPrimitivos = Contenedores.toArrayListWith("elemento1", "elemento2");

		return instancia;
	}

	public Double getFlotante() {
		return flotante;
	}

	public void setFlotante(Double flotante) {
		this.flotante = flotante;
	}

	public String getterDelAtributoPrivado() {
		return atributoPrivado;
	}
}
