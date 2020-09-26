/**
 * Created on: 03/03/2010 20:26:58 by: Dario L. Garcia
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
 */
package net.sf.kfgodel.bean2bean.testbeans.copyfromto;

import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;
import net.sf.kfgodel.bean2bean.annotations.FormatterPattern;
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;
import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;
import net.sf.kfgodel.bean2bean.inv.ClaseConDatos;
import net.sf.kfgodel.dgarcia.testing.Assert;

/**
 * 
 * @author D. Garcia
 */
public class ClaseEspejo {

	// Copia desde y hacia una propiedad con el mismo nombre
	@CopyFromAndTo
	private String propiedadPrimitiva;

	// Copia desde y hacia una propiedad con otro nombre
	@CopyFromAndTo("propiedadPrimitiva")
	private String copiadoDesdeOtroNombre;

	// Copia desde y haciauna propiedad publica
	@CopyFromAndTo("atributoPublico")
	private String copiadoDesdeUnaPropiedadPublica;

	// Copia desde y hacia una propiedad privada
	@CopyFromAndTo("atributoPrivado")
	private String copiadoDesdeUnaPropiedadPrivada;

	// Copia desde y hacia una propiedad anidada, crea la instancia si no existe en el bean remoto
	@CopyFromAndTo(value = "propiedadAnidada.propiedadPrimitiva", whenMissing = MissingPropertyAction.CREATE_MISSING_INSTANCES)
	private String copiadoDesdeUnaPropiedadAnidada;

	// Intenta obtener un valor de propiedad que no existe en el origen (y
	// obtiene null)
	@CopyFromAndTo(value = "propiedadInexistente", whenMissing = MissingPropertyAction.TREAT_AS_NULL)
	private String obteniendoNullCuandoNoExiste;

	// Utiliza un tipo de usuario para la conversion especificado con Groovy
	@CopyFromAndTo(value = "numero", localExpectedType = "java.lang.Long")
	private Number especificandoElTipoConReflection;

	// Utiliza una anotacion auxiliar para especificar el patron en la
	// conversion (usa conversor especial) para obtener el valor. Fuerzo el getter local para
	// modificar la cadena ya que ningun conversor se banca
	// pasar de "1.00" a un int
	@CopyFromAndTo(value = "numero", remote2LocalConversor = "net.sf.kfgodel.bean2bean.conversion.converters.FormatterConverter", contextAnnotations = FormatterPattern.class, localGetter = "especificandoFormato.substring(0,1)", localGetterInterpreter = InterpreterType.GROOVY)
	@FormatterPattern("%d.00")
	private String especificandoFormato;

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
		Assert.equals(null, this.getObteniendoNullCuandoNoExiste());
		Assert.equals(Long.class, this.getEspecificandoElTipoConReflection().getClass());
		Assert.equals(datos.getNumero(), this.getEspecificandoElTipoConReflection().intValue());
		Assert.equals(String.valueOf(datos.getNumero()) + ".00", this.getEspecificandoFormato());
	}

	/**
	 * @return
	 */
	public static ClaseEspejo create() {
		ClaseEspejo espejo = new ClaseEspejo();
		espejo.setPropiedadPrimitiva("primitiva");
		espejo.setCopiadoDesdeOtroNombre("primitiva");
		espejo.setCopiadoDesdeUnaPropiedadPublica("publica");
		espejo.setCopiadoDesdeUnaPropiedadPrivada("privada");
		espejo.setCopiadoDesdeUnaPropiedadAnidada("anidada");
		espejo.setEspecificandoElTipoConReflection(1L);
		espejo.setEspecificandoFormato("1.00");
		return espejo;
	}

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

	public String getCopiadoDesdeUnaPropiedadAnidada() {
		return copiadoDesdeUnaPropiedadAnidada;
	}

	public void setCopiadoDesdeUnaPropiedadAnidada(String copiadoDesdeUnaPropiedadAnidada) {
		this.copiadoDesdeUnaPropiedadAnidada = copiadoDesdeUnaPropiedadAnidada;
	}

	public String getObteniendoNullCuandoNoExiste() {
		return obteniendoNullCuandoNoExiste;
	}

	public void setObteniendoNullCuandoNoExiste(String obteniendoNullCuandoNoExiste) {
		this.obteniendoNullCuandoNoExiste = obteniendoNullCuandoNoExiste;
	}

	public Number getEspecificandoElTipoConReflection() {
		return especificandoElTipoConReflection;
	}

	public void setEspecificandoElTipoConReflection(Number especificandoElTipoConReflection) {
		this.especificandoElTipoConReflection = especificandoElTipoConReflection;
	}

	public String getEspecificandoFormato() {
		return especificandoFormato;
	}

	public void setEspecificandoFormato(String especificandoFormato) {
		this.especificandoFormato = especificandoFormato;
	}

}
