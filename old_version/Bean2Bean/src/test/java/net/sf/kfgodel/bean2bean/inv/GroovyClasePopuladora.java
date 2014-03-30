/**
 * Created on: 14/01/2009 15:40:33 by: Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.annotations.CopyTo;
import net.sf.kfgodel.bean2bean.annotations.FormatterPattern;
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;
import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;
import net.sf.kfgodel.dgarcia.testing.Assert;

/**
 * Esta clase prueba las distintas formas de populacion tomando los datos desde esta instancia y
 * asignandosea a otra
 * 
 * @author D.Garcia
 */
public class GroovyClasePopuladora {

	// Copia hacia una propiedad con otro nombre
	@CopyTo(value = "_destino.copiadaDesdeOtroNombre = _valor", setterInterpreter = InterpreterType.GROOVY, getterInterpreter = InterpreterType.GROOVY)
	private String copiadoHaciaOtroNombre;

	// Copia hacia una propiedad publica
	@CopyTo(value = "_destino.atributoPublico = _valor", setterInterpreter = InterpreterType.GROOVY, getterInterpreter = InterpreterType.GROOVY)
	private String copiadoHaciaUnaPropiedadPublica;

	// Copia hacia una propiedad privada
	@CopyTo(value = "_destino.atributoPrivado = _valor", setterInterpreter = InterpreterType.GROOVY, getterInterpreter = InterpreterType.GROOVY)
	private String copiadoHaciaUnaPropiedadPrivada;

	// Copia hacia una propiedad anidada indicando tipo
	@CopyTo(value = "_destino.propiedadAnidadaInstanciada.propiedadPrimitiva = _valor", setterInterpreter = InterpreterType.GROOVY, getterInterpreter = InterpreterType.GROOVY, expectedType = "String.class", typeInterpreter = InterpreterType.GROOVY)
	private String copiadoHaciaUnaPropiedadAnidada;

	// Realiza la asignacion usando una expresion Groovy en el destino
	@CopyTo(value = "_destino.asignadoConGroovy = _valor", setterInterpreter = InterpreterType.GROOVY, getterInterpreter = InterpreterType.GROOVY)
	private String usandoGroovyEnDestino;

	// Utiliza una expresión Groovy para la obtención del valor local
	@CopyTo(value = "_destino.obtenidoConGroovy = _valor", setterInterpreter = InterpreterType.GROOVY, getter = "usandoGroovyEnObtencion", getterInterpreter = InterpreterType.GROOVY)
	private String usandoGroovyEnObtencion;

	// Utiliza una expresion para obtencion y otra para asignacion usando Groovy
	@CopyTo(value = "_destino.obtenidoYAsignadoConGroovy = _valor", setterInterpreter = InterpreterType.GROOVY, getter = "usandoGroovyParaObtenerYAsignar", getterInterpreter = InterpreterType.GROOVY)
	private String usandoGroovyParaObtenerYAsignar;

	// Intenta asignar un valor en propiedad que no existe (y no genera
	// excepcion)
	@CopyTo(value = "_destino.propiedadInexistente = _valor", setterInterpreter = InterpreterType.GROOVY, getterInterpreter = InterpreterType.GROOVY, whenMissing = MissingPropertyAction.TREAT_AS_NULL)
	private String silenciosoSiNoExistePropiedad;

	// TODO: Crear nuevas instancias si no existen! Groovy
	// Intenta asignar un valor en propiedad que no existe (y crea las
	// instancias intermedias)
	// @CopyTo(value = "propiedadAnidada.propiedadAnidada.propiedadPrimitiva",
	// whenMissing =
	// MissingPropertyAction.CREATE_MISSING_INSTANCES)
	private String generandoPathDePropiedades;

	// Utiliza un tipo de usuario para la conversion especificado con Groovy
	@CopyTo(value = "_destino.longConGroovy = _valor", setterInterpreter = InterpreterType.GROOVY, getterInterpreter = InterpreterType.GROOVY, expectedType = "Long.class", typeInterpreter = InterpreterType.GROOVY)
	private Number especificandoElTipoConGroovy;

	// Utiliza una anotacion auxiliar para especificar el patron en la
	// conversion (usa conversor especial)
	@CopyTo(value = "_destino.conApostrofes = _valor", getterInterpreter = InterpreterType.GROOVY, setterInterpreter = InterpreterType.GROOVY, useConversor = "net.sf.kfgodel.bean2bean.conversion.converters.FormatterConverter", contextAnnotations = FormatterPattern.class)
	@FormatterPattern("'%s'")
	private String especificandoFormato;

	// Copia hacia una propiedad anidada sin indicar tipo
	@CopyTo(value = "_destino.propiedadAnidadaInstanciada.fromSimpleString = _valor", setterInterpreter = InterpreterType.GROOVY, getterInterpreter = InterpreterType.GROOVY)
	private String toNestedProperty;

	public String getCopiadoHaciaOtroNombre() {
		return copiadoHaciaOtroNombre;
	}

	public void setCopiadoHaciaOtroNombre(String copiadoHaciaOtroNombre) {
		this.copiadoHaciaOtroNombre = copiadoHaciaOtroNombre;
	}

	public String getCopiadoHaciaUnaPropiedadPublica() {
		return copiadoHaciaUnaPropiedadPublica;
	}

	public void setCopiadoHaciaUnaPropiedadPublica(String copiadoHaciaUnaPropiedadPublica) {
		this.copiadoHaciaUnaPropiedadPublica = copiadoHaciaUnaPropiedadPublica;
	}

	public String getCopiadoHaciaUnaPropiedadPrivada() {
		return copiadoHaciaUnaPropiedadPrivada;
	}

	public void setCopiadoHaciaUnaPropiedadPrivada(String copiadoHaciaUnaPropiedadPrivada) {
		this.copiadoHaciaUnaPropiedadPrivada = copiadoHaciaUnaPropiedadPrivada;
	}

	public String getUsandoGroovyEnDestino() {
		return usandoGroovyEnDestino;
	}

	public void setUsandoGroovyEnDestino(String usandoGroovyEnDestino) {
		this.usandoGroovyEnDestino = usandoGroovyEnDestino;
	}

	public String getUsandoGroovyEnObtencion() {
		return usandoGroovyEnObtencion;
	}

	public void setUsandoGroovyEnObtencion(String usandoGroovyEnObtencion) {
		this.usandoGroovyEnObtencion = usandoGroovyEnObtencion;
	}

	public String getUsandoGroovyParaObtenerYAsignar() {
		return usandoGroovyParaObtenerYAsignar;
	}

	public void setUsandoGroovyParaObtenerYAsignar(String usandoGroovyParaObtenerYAsignar) {
		this.usandoGroovyParaObtenerYAsignar = usandoGroovyParaObtenerYAsignar;
	}

	public String getSilenciosoSiNoExistePropiedad() {
		return silenciosoSiNoExistePropiedad;
	}

	public void setSilenciosoSiNoExistePropiedad(String silenciosoSiNoExistePropiedad) {
		this.silenciosoSiNoExistePropiedad = silenciosoSiNoExistePropiedad;
	}

	public String getGenerandoPathDePropiedades() {
		return generandoPathDePropiedades;
	}

	public void setGenerandoPathDePropiedades(String generandoPathDePropiedades) {
		this.generandoPathDePropiedades = generandoPathDePropiedades;
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

	public static GroovyClasePopuladora create() {
		GroovyClasePopuladora populadora = new GroovyClasePopuladora();
		populadora.setCopiadoHaciaOtroNombre("CopiadoHaciaOtroNombre");
		populadora.setCopiadoHaciaUnaPropiedadPublica("CopiadoHaciaUnaPropiedadPublica");
		populadora.setCopiadoHaciaUnaPropiedadPrivada("CopiadoHaciaUnaPropiedadPrivada");
		populadora.setCopiadoHaciaUnaPropiedadAnidada("CopiadoHaciaUnaPropiedadAnidada");
		populadora.setUsandoGroovyEnDestino("UsandoGroovyEnDestino");
		populadora.setUsandoGroovyEnObtencion("UsandoGroovyEnObtencion");
		populadora.setUsandoGroovyParaObtenerYAsignar("UsandoGroovyParaObtenerYAsignar");
		populadora.setSilenciosoSiNoExistePropiedad("SilenciosoSiNoExistePropiedad");
		populadora.setGenerandoPathDePropiedades("GenerandoPathDePropiedades");
		populadora.setEspecificandoElTipoConGroovy(3.0);
		populadora.setEspecificandoFormato("EspecificandoFormato");
		return populadora;
	}

	/**
	 * @param populada
	 */
	public void verificarContra(ClasePopulada populada) {
		Assert.equals(this.getCopiadoHaciaOtroNombre(), populada.getCopiadaDesdeOtroNombre());
		Assert.equals(this.getCopiadoHaciaUnaPropiedadPublica(), populada.atributoPublico);
		Assert.equals(this.getCopiadoHaciaUnaPropiedadPrivada(), populada.getterAtributoPrivado());
		Assert.equals(this.getCopiadoHaciaUnaPropiedadAnidada(), populada.getPropiedadAnidadaInstanciada()
				.getPropiedadPrimitiva());
		Assert.equals(this.getUsandoGroovyEnDestino(), populada.getAsignadoConGroovy());
		Assert.equals(this.getUsandoGroovyEnObtencion(), populada.getObtenidoConGroovy());
		Assert.equals(this.getUsandoGroovyParaObtenerYAsignar(), populada.getObtenidoYAsignadoConGroovy());
		// Assert.equals(this.getGenerandoPathDePropiedades(),
		// populada.getPropiedadAnidada().getPropiedadAnidada()
		// .getPropiedadPrimitiva());
		Assert.equals(Long.class, populada.getLongConGroovy().getClass());
		Assert.equals("'" + this.getEspecificandoFormato() + "'", populada.getConApostrofes());
		Assert.equals(this.getToNestedProperty(), populada.getPropiedadAnidadaInstanciada().getFromSimpleString());
	}

	public String getCopiadoHaciaUnaPropiedadAnidada() {
		return copiadoHaciaUnaPropiedadAnidada;
	}

	public void setCopiadoHaciaUnaPropiedadAnidada(String copiadoHaciaUnaPropiedadAnidada) {
		this.copiadoHaciaUnaPropiedadAnidada = copiadoHaciaUnaPropiedadAnidada;
	}

	public String getToNestedProperty() {
		return toNestedProperty;
	}

	public void setToNestedProperty(String toNestedProperty) {
		this.toNestedProperty = toNestedProperty;
	}

}
