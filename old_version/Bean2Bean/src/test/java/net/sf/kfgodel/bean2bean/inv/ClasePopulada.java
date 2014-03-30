/**
 * Created on: 14/01/2009 23:59:52 by: Dario L. Garcia
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


/**
 * Clase que es populada desde la populadora
 * 
 * @author D.Garcia
 */
public class ClasePopulada {
	private String propiedadPrimitiva;
	private String copiadaDesdeOtroNombre;
	public String atributoPublico;
	private String atributoPrivado;
	private ClasePopulada propiedadAnidadaInstanciada;
	private String asignadoConOgnl;
	private String asignadoConGroovy;
	private String obtenidoConOgnl;
	private String obtenidoConGroovy;
	private String obtenidoYAsignadoConOgnl;
	private String obtenidoYAsignadoConGroovy;
	private String obtencionConGroovyYasignacionOgnl;
	private ClasePopulada propiedadAnidada;
	private Number longConOgnl;
	private Number longConGroovy;
	private String conApostrofes;
	private String fromSimpleString;

	public String getFromSimpleString() {
		return fromSimpleString;
	}

	public void setFromSimpleString(String fromSimpleString) {
		this.fromSimpleString = fromSimpleString;
	}

	public String getPropiedadPrimitiva() {
		return propiedadPrimitiva;
	}

	public void setPropiedadPrimitiva(String propiedadPrimitiva) {
		this.propiedadPrimitiva = propiedadPrimitiva;
	}

	public String getCopiadaDesdeOtroNombre() {
		return copiadaDesdeOtroNombre;
	}

	public void setCopiadaDesdeOtroNombre(String copiadaDesdeOtroNombre) {
		this.copiadaDesdeOtroNombre = copiadaDesdeOtroNombre;
	}

	public String getAsignadoConOgnl() {
		return asignadoConOgnl;
	}

	public void setAsignadoConOgnl(String asignadoConOgnl) {
		this.asignadoConOgnl = asignadoConOgnl;
	}

	public String getAsignadoConGroovy() {
		return asignadoConGroovy;
	}

	public void setAsignadoConGroovy(String asignadoConGroovy) {
		this.asignadoConGroovy = asignadoConGroovy;
	}

	public String getObtenidoConOgnl() {
		return obtenidoConOgnl;
	}

	public void setObtenidoConOgnl(String obtenidoConOgnl) {
		this.obtenidoConOgnl = obtenidoConOgnl;
	}

	public String getObtenidoConGroovy() {
		return obtenidoConGroovy;
	}

	public void setObtenidoConGroovy(String obtenidoConGroovy) {
		this.obtenidoConGroovy = obtenidoConGroovy;
	}

	public String getObtenidoYAsignadoConOgnl() {
		return obtenidoYAsignadoConOgnl;
	}

	public void setObtenidoYAsignadoConOgnl(String obtenidoYAsignadoConOgnl) {
		this.obtenidoYAsignadoConOgnl = obtenidoYAsignadoConOgnl;
	}

	public String getObtenidoYAsignadoConGroovy() {
		return obtenidoYAsignadoConGroovy;
	}

	public void setObtenidoYAsignadoConGroovy(String obtenidoYAsignadoConGroovy) {
		this.obtenidoYAsignadoConGroovy = obtenidoYAsignadoConGroovy;
	}

	public String getObtencionConGroovyYasignacionOgnl() {
		return obtencionConGroovyYasignacionOgnl;
	}

	public void setObtencionConGroovyYasignacionOgnl(String obtencionConGroovyYasignacionOgnl) {
		this.obtencionConGroovyYasignacionOgnl = obtencionConGroovyYasignacionOgnl;
	}

	public ClasePopulada getPropiedadAnidada() {
		return propiedadAnidada;
	}

	public void setPropiedadAnidada(ClasePopulada propiedadAnidada) {
		this.propiedadAnidada = propiedadAnidada;
	}

	public Number getLongConOgnl() {
		return longConOgnl;
	}

	public void setLongConOgnl(Number longConOgnl) {
		this.longConOgnl = longConOgnl;
	}

	public Number getLongConGroovy() {
		return longConGroovy;
	}

	public void setLongConGroovy(Number longConGroovy) {
		this.longConGroovy = longConGroovy;
	}

	public String getConApostrofes() {
		return conApostrofes;
	}

	public void setConApostrofes(String conApostrofes) {
		this.conApostrofes = conApostrofes;
	}

	public String getterAtributoPrivado() {
		return atributoPrivado;
	}

	public ClasePopulada getPropiedadAnidadaInstanciada() {
		if (propiedadAnidadaInstanciada == null) {
			propiedadAnidadaInstanciada = new ClasePopulada();
		}
		return propiedadAnidadaInstanciada;
	}

	public void setPropiedadAnidadaInstanciada(ClasePopulada propiedadAnidadaInstanciada) {
		this.propiedadAnidadaInstanciada = propiedadAnidadaInstanciada;
	}
}
