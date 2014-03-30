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
import net.sf.kfgodel.dgarcia.testing.Assert;

/**
 * This class test OGNL with different combinations for the configurations
 * 
 * @author D.Garcia
 */
public class OgnlClasePopulable {

	// Copia desde una propiedad con el mismo nombre
	@CopyFrom(getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL)
	private String propiedadPrimitiva;

	// Copia desde una propiedad con otro nombre
	@CopyFrom(value = "propiedadPrimitiva", getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL)
	private String copiadoDesdeOtroNombre;

	// Copia desde una propiedad publica
	@CopyFrom(value = "atributoPublico", getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL)
	private String copiadoDesdeUnaPropiedadPublica;

	// Copia desde una propiedad anidada
	@CopyFrom(value = "propiedadAnidada.propiedadPrimitiva", getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL)
	private String copiadoDesdeUnaPropiedadAnidada;

	// Obtiene el valor usando una expresion OGNL en el origen
	@CopyFrom(value = "propiedadPrimitiva == 'primitiva0'? 'raiz' : propiedadPrimitiva", getterInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL)
	private String usandoOgnlEnOrigen;

	// Utiliza una expresión OGNL para la asignacion local
	@CopyFrom(value = "propiedadPrimitiva", getterInterpreter = InterpreterType.OGNL, setter = "setUsandoOgnlEnAsignacion(#_valor)", setterInterpreter = InterpreterType.OGNL)
	private String usandoOgnlEnAsignacion;

	// Utiliza una expresion para la obtención y otra para la asignacion usando
	// OGNL
	@CopyFrom(value = "propiedadPrimitiva", getterInterpreter = InterpreterType.OGNL, setter = "usandoOgnlParaObtenerYAsignar", setterInterpreter = InterpreterType.OGNL)
	private String usandoOgnlParaObtenerYAsignar;

	// Intenta obtener un valor de propiedad que no existe en el origen (y
	// obtiene null)
	@CopyFrom(value = "propiedadInexistente", getterInterpreter = InterpreterType.OGNL, whenMissing = MissingPropertyAction.TREAT_AS_NULL, setterInterpreter = InterpreterType.OGNL)
	private String obteniendoNullCuandoNoExiste;

	// Utiliza un tipo de usuario para la conversion especificado con OGNL
	@CopyFrom(value = "numero", getterInterpreter = InterpreterType.OGNL, expectedType = "@java.lang.Long@class", typeInterpreter = InterpreterType.OGNL, setterInterpreter = InterpreterType.OGNL)
	private Number especificandoElTipoConOgnl;

	// Utiliza una anotacion auxiliar para especificar el patron en la
	// conversion (usa conversor
	// especial)
	@CopyFrom(value = "numero", getterInterpreter = InterpreterType.OGNL, useConversor = "net.sf.kfgodel.bean2bean.conversion.converters.FormatterConverter", contextAnnotations = FormatterPattern.class, setterInterpreter = InterpreterType.OGNL)
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

	public String getUsandoOgnlEnOrigen() {
		return usandoOgnlEnOrigen;
	}

	public void setUsandoOgnlEnOrigen(String usandoOgnlEnOrigen) {
		this.usandoOgnlEnOrigen = usandoOgnlEnOrigen;
	}

	public String getUsandoOgnlEnAsignacion() {
		return usandoOgnlEnAsignacion;
	}

	public void setUsandoOgnlEnAsignacion(String usandoOgnlEnAsignacion) {
		this.usandoOgnlEnAsignacion = usandoOgnlEnAsignacion;
	}

	public String getUsandoOgnlParaObtenerYAsignar() {
		return usandoOgnlParaObtenerYAsignar;
	}

	public void setUsandoOgnlParaObtenerYAsignar(String usandoOgnlParaObtenerYAsignar) {
		this.usandoOgnlParaObtenerYAsignar = usandoOgnlParaObtenerYAsignar;
	}

	public String getObteniendoNullCuandoNoExiste() {
		return obteniendoNullCuandoNoExiste;
	}

	public void setObteniendoNullCuandoNoExiste(String obteniendoNullCuandoNoExiste) {
		this.obteniendoNullCuandoNoExiste = obteniendoNullCuandoNoExiste;
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
		Assert.equals(datos.getPropiedadAnidada().getPropiedadPrimitiva(), this.getCopiadoDesdeUnaPropiedadAnidada());
		Assert.equals(datos.getPropiedadPrimitiva().equals("primitiva0") ? "raiz" : datos.getPropiedadPrimitiva(), this
				.getUsandoOgnlEnOrigen());
		Assert.equals(datos.getPropiedadPrimitiva(), this.getUsandoOgnlEnAsignacion());
		Assert.equals(datos.getPropiedadPrimitiva(), this.getUsandoOgnlParaObtenerYAsignar());
		Assert.equals(null, this.getObteniendoNullCuandoNoExiste());
		Assert.equals(Long.class, this.getEspecificandoElTipoConOgnl().getClass());
		Assert.equals(String.valueOf(datos.getNumero()) + ".00", this.getEspecificandoFormato());
	}

	public String getCopiadoDesdeUnaPropiedadAnidada() {
		return copiadoDesdeUnaPropiedadAnidada;
	}

	public void setCopiadoDesdeUnaPropiedadAnidada(String copiadoDesdeUnaPropiedadAnidada) {
		this.copiadoDesdeUnaPropiedadAnidada = copiadoDesdeUnaPropiedadAnidada;
	}

}
