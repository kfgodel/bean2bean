/**
 * Created on 20/12/2007 23:04:04 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.testbeans;

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.CopyTo;
import net.sf.kfgodel.bean2bean.annotations.FormatterPattern;
import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;
import net.sf.kfgodel.dgarcia.testing.Assert;

/**
 * Esta clase representa un bean que realiza varias conversiones entre propiedades del
 * {@link SourceBean}
 * 
 * @version 1.0
 * @since 20/12/2007
 * @author D. Garcia
 */
public class TransformedPrimitiveBean implements BeanTester<SourceBean> {

	@CopyFrom(value = " '\"' + objetoString + '\"' ", getterInterpreter = InterpreterType.OGNL, useConversor = "net.sf.kfgodel.bean2bean.conversion.converters.JsonStringObjectConverter")
	private String jsonString;

	@CopyFrom("primitivoDouble")
	@CopyTo("primitivoDouble")
	private Double objetoDouble;

	@CopyFrom("objetoInteger")
	@CopyTo("objetoInteger")
	private Float objetoFloat;

	@CopyFrom(value = "@java.lang.Integer@parseInt(objetoString)", getterInterpreter = InterpreterType.OGNL)
	@CopyTo(value = "objetoString", getter = "objetoInteger.toString()", getterInterpreter = InterpreterType.OGNL)
	private Integer objetoInteger;

	@CopyFrom("objetoInteger")
	@CopyTo("objetoInteger")
	private String objetoString;

	@CopyFrom("objetoDouble")
	@CopyTo("objetoDouble")
	private double primitivoDouble;

	@CopyFrom(value = "primitivoFloat + 1", getterInterpreter = InterpreterType.OGNL)
	@CopyTo(getter = "primitivoFloat + 1", getterInterpreter = InterpreterType.OGNL)
	private float primitivoFloat;

	@CopyFrom(value = "primitivoInteger + 1", getterInterpreter = InterpreterType.OGNL)
	@CopyTo(getter = "primitivoInteger - 1", getterInterpreter = InterpreterType.OGNL)
	private int primitivoInteger;

	@CopyFrom(value = "primitivoDouble", useConversor = "net.sf.kfgodel.bean2bean.conversion.converters.FormatterConverter", contextAnnotations = FormatterPattern.class)
	@FormatterPattern("%.0f.00")
	private String formateado;

	public String getFormateado() {
		return formateado;
	}

	public void setFormateado(String formateado) {
		this.formateado = formateado;
	}

	/**
	 * @param destination
	 *            Bean al que se le copio el estado
	 * @see net.sf.kfgodel.bean2bean.testbeans.BeanTester#compareWithDestinationBean(java.lang.Object)
	 */
	public void compareWithDestinationBean(SourceBean destination) {
		Assert.notNull(destination);
		Assert.equals(this.getObjetoDouble().doubleValue(), destination.getPrimitivoDouble());
		Assert.equals(new Integer(this.getObjetoFloat().intValue()), destination.getObjetoInteger());
		Assert.equals(this.getObjetoInteger().toString(), destination.getObjetoString());
		Assert.equals(new Integer(this.getObjetoString()), destination.getObjetoInteger());
		Assert.equals(new Double(this.getPrimitivoDouble()), destination.getObjetoDouble());
		Assert.equals(this.getPrimitivoFloat() + 1, destination.getPrimitivoFloat());
		Assert.equals(this.getPrimitivoInteger() - 1, destination.getPrimitivoInteger());

		// Este chequeo no puede hacerse por que el Integer sobreescribe el
		// valor,
		// Cambiar los mapeos para poder chequear
		// Assert.equals("\""+this.getJsonString()+"\"",
		// destination.getObjetoString());
	}

	/**
	 * @param source
	 *            Bean desde el que se copiaron los datos
	 * @see net.sf.kfgodel.bean2bean.testbeans.BeanTester#compareWithSourceBean(java.lang.Object)
	 */
	public void compareWithSourceBean(SourceBean source) {
		Assert.notNull(source);
		Assert.equals(new Double(source.getPrimitivoDouble()), this.getObjetoDouble());
		Assert.equals(new Float(source.getObjetoInteger().floatValue()), this.getObjetoFloat());
		Assert.equals(new Integer(source.getObjetoString()), this.getObjetoInteger());
		Assert.equals(source.getObjetoInteger().toString(), this.getObjetoString());
		Assert.equals(source.getObjetoDouble().doubleValue(), this.getPrimitivoDouble());
		Assert.equals(source.getPrimitivoFloat() + 1, this.getPrimitivoFloat());
		Assert.equals(source.getPrimitivoInteger() + 1, this.getPrimitivoInteger());
		Assert.equals(source.getObjetoString(), getJsonString());
		Assert.equals((int) source.getPrimitivoDouble() + ".00", getFormateado());
	}

	public String getJsonString() {
		return jsonString;
	}

	public Double getObjetoDouble() {
		return this.objetoDouble;
	}

	public Float getObjetoFloat() {
		return this.objetoFloat;
	}

	public Integer getObjetoInteger() {
		return this.objetoInteger;
	}

	public String getObjetoString() {
		return this.objetoString;
	}

	public double getPrimitivoDouble() {
		return this.primitivoDouble;
	}

	public float getPrimitivoFloat() {
		return this.primitivoFloat;
	}

	public int getPrimitivoInteger() {
		return this.primitivoInteger;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public void setObjetoDouble(Double objetoDouble) {
		this.objetoDouble = objetoDouble;
	}

	public void setObjetoFloat(Float objetoFloat) {
		this.objetoFloat = objetoFloat;
	}

	public void setObjetoInteger(Integer objetoInteger) {
		this.objetoInteger = objetoInteger;
	}

	public void setObjetoString(String objetoString) {
		this.objetoString = objetoString;
	}

	public void setPrimitivoDouble(double primitivoDouble) {
		this.primitivoDouble = primitivoDouble;
	}

	public void setPrimitivoFloat(float primitivoFloat) {
		this.primitivoFloat = primitivoFloat;
	}

	public void setPrimitivoInteger(int primitivoInteger) {
		this.primitivoInteger = primitivoInteger;
	}

}
