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
import net.sf.kfgodel.bean2bean.inv.ClasePopulada;
import net.sf.kfgodel.dgarcia.testing.Assert;

/**
 * Esta clase prueba las distintas formas de populacion tomando los datos desde esta instancia y
 * asignandosea a otra
 * 
 * @author D.Garcia
 */
public class OgnlClasePopuladora {

	// Copia hacia una propiedad con el mismo nombre en el tipo destino
	@CopyTo(getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL)
	private String propiedadPrimitiva;

	// Copia hacia una propiedad con otro nombre
	@CopyTo(value = "copiadaDesdeOtroNombre", getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL)
	private String copiadoHaciaOtroNombre;

	// Copia hacia una propiedad publica
	@CopyTo(value = "atributoPublico", getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL)
	private String copiadoHaciaUnaPropiedadPublica;

	// Copia desde una propiedad anidada indicando tipo
	@CopyTo(value = "propiedadAnidadaInstanciada.propiedadPrimitiva", getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL, expectedType = "@java.lang.String@class", typeInterpreter = InterpreterType.OGNL)
	private String copiadoHaciaUnaPropiedadAnidada;

	// Realiza la asignación usando una expresion OGNL en el destino
	@CopyTo(value = "asignadoConOgnl", getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL)
	private String usandoOgnlEnDestino;

	// Utiliza una expresión OGNL para la obtención del valor local
	@CopyTo(value = "obtenidoConOgnl", setterInterpreter = InterpreterType.OGNL, getter = "usandoOgnlEnObtencion", getterInterpreter = InterpreterType.OGNL)
	private String usandoOgnlEnObtencion;

	// Utiliza una expresion para la obtención y otra para la asignacion usando
	// OGNL
	@CopyTo(value = "obtenidoYAsignadoConOgnl", setterInterpreter = InterpreterType.OGNL, getter = "usandoOgnlParaObtenerYAsignar", getterInterpreter = InterpreterType.OGNL)
	private String usandoOgnlParaObtenerYAsignar;

	// Intenta asignar un valor en propiedad que no existe (y no genera
	// excepcion)
	@CopyTo(value = "propiedadInexistente", getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL, whenMissing = MissingPropertyAction.TREAT_AS_NULL)
	private String silenciosoSiNoExistePropiedad;

	// TODO: Crear nuevas instancias si no existen!
	// Intenta asignar un valor en propiedad que no existe (y crea las
	// instancias intermedias)
	// @CopyTo(value = "propiedadAnidada.propiedadAnidada.propiedadPrimitiva",
	// whenMissing =
	// MissingPropertyAction.CREATE_MISSING_INSTANCES)
	private String generandoPathDePropiedades;

	// Utiliza un tipo de usuario para la conversion especificado con OGNL
	@CopyTo(value = "longConOgnl", getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL, expectedType = "@java.lang.Long@class", typeInterpreter = InterpreterType.OGNL)
	private Number especificandoElTipoConOgnl;

	// Utiliza una anotacion auxiliar para especificar el patron en la
	// conversion (usa conversor especial)
	@CopyTo(value = "conApostrofes", getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL, useConversor = "net.sf.kfgodel.bean2bean.conversion.converters.FormatterConverter", contextAnnotations = FormatterPattern.class)
	@FormatterPattern("'%s'")
	private String especificandoFormato;

	// Copia hacia una propiedad anidada sin indicar tipo
	@CopyTo(value = "propiedadAnidadaInstanciada.fromSimpleString", getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL)
	private String toNestedProperty;

	public String getPropiedadPrimitiva() {
		return propiedadPrimitiva;
	}

	public void setPropiedadPrimitiva(String propiedadPrimitiva) {
		this.propiedadPrimitiva = propiedadPrimitiva;
	}

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

	public String getUsandoOgnlEnDestino() {
		return usandoOgnlEnDestino;
	}

	public void setUsandoOgnlEnDestino(String usandoOgnlEnDestino) {
		this.usandoOgnlEnDestino = usandoOgnlEnDestino;
	}

	public String getUsandoOgnlEnObtencion() {
		return usandoOgnlEnObtencion;
	}

	public void setUsandoOgnlEnObtencion(String usandoOgnlEnObtencion) {
		this.usandoOgnlEnObtencion = usandoOgnlEnObtencion;
	}

	public String getUsandoOgnlParaObtenerYAsignar() {
		return usandoOgnlParaObtenerYAsignar;
	}

	public void setUsandoOgnlParaObtenerYAsignar(String usandoOgnlParaObtenerYAsignar) {
		this.usandoOgnlParaObtenerYAsignar = usandoOgnlParaObtenerYAsignar;
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

	public Number getEspecificandoElTipoConOgnl() {
		return especificandoElTipoConOgnl;
	}

	public void setEspecificandoElTipoConOgnl(Number especificandoElTipoConOgnl) {
		this.especificandoElTipoConOgnl = especificandoElTipoConOgnl;
	}

	public String getEspecificandoFormato() {
		return especificandoFormato;
	}

	public void setEspecificandoFormato(String especificandoFormato) {
		this.especificandoFormato = especificandoFormato;
	}

	public static OgnlClasePopuladora create() {
		OgnlClasePopuladora populadora = new OgnlClasePopuladora();
		populadora.setPropiedadPrimitiva("PropiedadPrimitiva");
		populadora.setCopiadoHaciaOtroNombre("CopiadoHaciaOtroNombre");
		populadora.setCopiadoHaciaUnaPropiedadPublica("CopiadoHaciaUnaPropiedadPublica");
		populadora.setCopiadoHaciaUnaPropiedadAnidada("CopiadoHaciaUnaPropiedadAnidada");
		populadora.setUsandoOgnlEnDestino("UsandoOgnlEnDestino");
		populadora.setUsandoOgnlEnObtencion("UsandoOgnlEnObtencion");
		populadora.setUsandoOgnlParaObtenerYAsignar("UsandoOgnlParaObtenerYAsignar");
		populadora.setSilenciosoSiNoExistePropiedad("SilenciosoSiNoExistePropiedad");
		populadora.setGenerandoPathDePropiedades("GenerandoPathDePropiedades");
		populadora.setEspecificandoElTipoConOgnl(2.0);
		populadora.setEspecificandoFormato("EspecificandoFormato");
		return populadora;
	}

	/**
	 * @param populada
	 */
	public void verificarContra(ClasePopulada populada) {
		Assert.equals(this.getPropiedadPrimitiva(), populada.getPropiedadPrimitiva());
		Assert.equals(this.getCopiadoHaciaOtroNombre(), populada.getCopiadaDesdeOtroNombre());
		Assert.equals(this.getCopiadoHaciaUnaPropiedadPublica(), populada.atributoPublico);
		Assert.equals(this.getCopiadoHaciaUnaPropiedadAnidada(), populada.getPropiedadAnidadaInstanciada()
				.getPropiedadPrimitiva());
		Assert.equals(this.getUsandoOgnlEnDestino(), populada.getAsignadoConOgnl());
		Assert.equals(this.getUsandoOgnlEnObtencion(), populada.getObtenidoConOgnl());
		Assert.equals(this.getUsandoOgnlParaObtenerYAsignar(), populada.getObtenidoYAsignadoConOgnl());
		// Assert.equals(this.getGenerandoPathDePropiedades(),
		// populada.getPropiedadAnidada().getPropiedadAnidada()
		// .getPropiedadPrimitiva());
		Assert.equals(Long.class, populada.getLongConOgnl().getClass());
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
