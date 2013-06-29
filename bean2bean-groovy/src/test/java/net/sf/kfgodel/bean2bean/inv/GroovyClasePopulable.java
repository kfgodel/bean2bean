/**
 * Created on: 14/01/2009 15:40:21 by: Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.FormatterPattern;
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;
import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;
import net.sf.kfgodel.bean2bean.inv.ClaseConDatos;
import net.sf.kfgodel.dgarcia.java.util.Contenedores;
import net.sf.kfgodel.dgarcia.testing.Assert;

/**
 * Esta clase prueba los distintos tipos de mapeos que se pueden realizar copiando datos desde otra
 * entidad
 * 
 * @author D.Garcia
 */
public class GroovyClasePopulable {

	// Copia desde una propiedad con el mismo nombre
	@CopyFrom(getterInterpreter = InterpreterType.GROOVY)
	private String propiedadPrimitiva;

	// Copia desde una propiedad con otro nombre
	@CopyFrom(value = "propiedadPrimitiva", getterInterpreter = InterpreterType.GROOVY)
	private String copiadoDesdeOtroNombre;

	// Copia desde una propiedad publica
	@CopyFrom(value = "atributoPublico", getterInterpreter = InterpreterType.GROOVY)
	private String copiadoDesdeUnaPropiedadPublica;

	// Copia desde una propiedad privada
	@CopyFrom(value = "atributoPrivado", getterInterpreter = InterpreterType.GROOVY)
	private String copiadoDesdeUnaPropiedadPrivada;

	// Copia desde una propiedad anidada
	@CopyFrom(value = "propiedadAnidada.propiedadPrimitiva", getterInterpreter = InterpreterType.GROOVY)
	private String copiadoDesdeUnaPropiedadAnidada;

	// Obtiene el valor usando una expresion Groovy en el origen
	@CopyFrom(value = "listaPrimitivos.join(',')", getterInterpreter = InterpreterType.GROOVY)
	private String usandoGroovyEnOrigen;

	// Utiliza una expresión Groovy para la asignacion local
	@CopyFrom(value = "propiedadPrimitiva", getterInterpreter = InterpreterType.GROOVY, setter = "_destino.usandoGroovyEnAsignacion = _valor", setterInterpreter = InterpreterType.GROOVY)
	private String usandoGroovyEnAsignacion;

	// Utiliza una expresion para obtencion y otra para asignacion usando Groovy
	@CopyFrom(value = "propiedadPrimitiva", getterInterpreter = InterpreterType.GROOVY, setter = "_destino.usandoGroovyParaObtenerYAsignar = _valor", setterInterpreter = InterpreterType.GROOVY)
	private String usandoGroovyParaObtenerYAsignar;

	// Intenta obtener un valor de propiedad que no existe en el origen (y
	// obtiene null)
	@CopyFrom(value = "propiedadInexistente", getterInterpreter = InterpreterType.GROOVY, whenMissing = MissingPropertyAction.TREAT_AS_NULL)
	private String obteniendoNullCuandoNoExiste;

	// Utiliza un tipo de usuario para la conversion especificado con Groovy
	@CopyFrom(value = "numero", expectedType = "Long.class", typeInterpreter = InterpreterType.GROOVY)
	private Number especificandoElTipoConGroovy;

	// Utiliza una anotacion auxiliar para especificar el patron en la
	// conversion (usa conversor
	// especial)
	@CopyFrom(value = "numero", getterInterpreter = InterpreterType.GROOVY, useConversor = "net.sf.kfgodel.bean2bean.conversion.converters.FormatterConverter", contextAnnotations = FormatterPattern.class)
	@FormatterPattern("%d.00")
	private String especificandoFormato;

	public String getPropiedadPrimitiva() {
		return propiedadPrimitiva;
	}

	public void setPropiedadPrimitiva(String propiedadPrimitiva) {
		this.propiedadPrimitiva = propiedadPrimitiva;
	}

	public String getCopiadoDesdeOtroNombre() {
		return copiadoDesdeOtroNombre;
	}

	public void setCopiadoDesdeOtroNombre(String copiadoDesdeOtroNombre) {
		this.copiadoDesdeOtroNombre = copiadoDesdeOtroNombre;
	}

	public String getCopiadoDesdeUnaPropiedadPublica() {
		return copiadoDesdeUnaPropiedadPublica;
	}

	public void setCopiadoDesdeUnaPropiedadPublica(String copiadoDesdeUnaPropiedadPublica) {
		this.copiadoDesdeUnaPropiedadPublica = copiadoDesdeUnaPropiedadPublica;
	}

	public String getCopiadoDesdeUnaPropiedadPrivada() {
		return copiadoDesdeUnaPropiedadPrivada;
	}

	public void setCopiadoDesdeUnaPropiedadPrivada(String copiadoDesdeUnaPropiedadPrivada) {
		this.copiadoDesdeUnaPropiedadPrivada = copiadoDesdeUnaPropiedadPrivada;
	}

	public String getUsandoGroovyEnOrigen() {
		return usandoGroovyEnOrigen;
	}

	public void setUsandoGroovyEnOrigen(String usandoGroovyEnOrigen) {
		this.usandoGroovyEnOrigen = usandoGroovyEnOrigen;
	}

	public String getUsandoGroovyEnAsignacion() {
		return usandoGroovyEnAsignacion;
	}

	public void setUsandoGroovyEnAsignacion(String usandoGroovyEnAsignacion) {
		this.usandoGroovyEnAsignacion = usandoGroovyEnAsignacion;
	}

	public String getUsandoGroovyParaObtenerYAsignar() {
		return usandoGroovyParaObtenerYAsignar;
	}

	public void setUsandoGroovyParaObtenerYAsignar(String usandoGroovyParaObtenerYAsignar) {
		this.usandoGroovyParaObtenerYAsignar = usandoGroovyParaObtenerYAsignar;
	}

	public String getObteniendoNullCuandoNoExiste() {
		return obteniendoNullCuandoNoExiste;
	}

	public void setObteniendoNullCuandoNoExiste(String obteniendoNullCuandoNoExiste) {
		this.obteniendoNullCuandoNoExiste = obteniendoNullCuandoNoExiste;
	}

	public Number getEspecificandoElTipoConGroovy() {
		return especificandoElTipoConGroovy;
	}

	public void setEspecificandoElTipoConGroovy(Number especificandoElTipoConGroovy) {
		this.especificandoElTipoConGroovy = especificandoElTipoConGroovy;
	}

	public String getEspecificandoFormato() {
		return especificandoFormato;
	}

	public void setEspecificandoFormato(String especificandoFormato) {
		this.especificandoFormato = especificandoFormato;
	}

	/**
	 * Verifica que el estado de este populable se corresponda con el del objeto pasado de acuerdo
	 * al mapeo de las propiedades
	 * 
	 * @param datos
	 *            Objeto contra el que se comprobara
	 */
	public void verificarContra(ClaseConDatos datos) {
		Assert.equals(datos.getPropiedadPrimitiva(), this.getPropiedadPrimitiva());
		Assert.equals(datos.getPropiedadPrimitiva(), this.getCopiadoDesdeOtroNombre());
		Assert.equals(datos.atributoPublico, this.getCopiadoDesdeUnaPropiedadPublica());
		Assert.equals(datos.getterDelAtributoPrivado(), this.getCopiadoDesdeUnaPropiedadPrivada());
		Assert.equals(datos.getPropiedadAnidada().getPropiedadPrimitiva(), this.getCopiadoDesdeUnaPropiedadAnidada());
		Assert.equals(Contenedores.toStringJoinedBy(",", datos.getListaPrimitivos()), this.getUsandoGroovyEnOrigen());
		Assert.equals(datos.getPropiedadPrimitiva(), this.getUsandoGroovyEnAsignacion());
		Assert.equals(datos.getPropiedadPrimitiva(), this.getUsandoGroovyParaObtenerYAsignar());
		Assert.equals(Long.class, this.getEspecificandoElTipoConGroovy().getClass());
		Assert.equals(String.valueOf(datos.getNumero()) + ".00", this.getEspecificandoFormato());
	}

	public String getCopiadoDesdeUnaPropiedadAnidada() {
		return copiadoDesdeUnaPropiedadAnidada;
	}

	public void setCopiadoDesdeUnaPropiedadAnidada(String copiadoDesdeUnaPropiedadAnidada) {
		this.copiadoDesdeUnaPropiedadAnidada = copiadoDesdeUnaPropiedadAnidada;
	}

}
