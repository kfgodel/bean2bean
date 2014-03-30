/**
 * Created on 20/12/2007 21:45:34 Copyright (C) 2007 Dario L. Garcia
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
import net.sf.kfgodel.bean2bean.annotations.MissingPropertyAction;
import net.sf.kfgodel.bean2bean.testbeans.BeanTester;
import net.sf.kfgodel.bean2bean.testbeans.SourceBean;
import net.sf.kfgodel.dgarcia.testing.Assert;

/**
 * Esta clase representa un mirror de {@link SourceBean} que copia solo datos primitivos
 * 
 * 
 * @version 1.0
 * @since 20/12/2007
 * @author D. Garcia
 */
public class PlainPrimitiveBean implements BeanTester<SourceBean> {
	@CopyFrom
	@CopyTo
	private Double objetoDouble;
	@CopyFrom
	@CopyTo
	private Float objetoFloat;
	@CopyFrom
	@CopyTo
	private Integer objetoInteger;
	@CopyFrom
	@CopyTo
	private String objetoString;
	@CopyFrom
	@CopyTo
	private double primitivoDouble;
	@CopyFrom
	@CopyTo
	private float primitivoFloat;
	@CopyFrom
	@CopyTo
	private int primitivoInteger;

	@CopyFrom(value = "lalalal", whenMissing = MissingPropertyAction.TREAT_AS_NULL)
	private Object alwasNull;

	@CopyTo(value = "lalalal", whenMissing = MissingPropertyAction.TREAT_AS_NULL)
	private Object neverCopied;

	/**
	 * @param source
	 *            Bean contra el que se debe comparar
	 * @see net.sf.kfgodel.bean2bean.testbeans.BeanTester#compareWithDestinationBean(java.lang.Object)
	 */
	public void compareWithDestinationBean(SourceBean source) {
		Assert.notNull(source);
		Assert.equals(this.getPrimitivoInteger(), source.getPrimitivoInteger());
		Assert.equals(this.getObjetoDouble(), source.getObjetoDouble());
		Assert.equals(this.getObjetoFloat(), source.getObjetoFloat());
		Assert.equals(this.getObjetoInteger(), source.getObjetoInteger());
		Assert.equals(this.getObjetoString(), source.getObjetoString());
		Assert.equals(this.getPrimitivoDouble(), source.getPrimitivoDouble());
		Assert.equals(this.getPrimitivoFloat(), source.getPrimitivoFloat());
	}

	/**
	 * @param sourceBean
	 *            Bean a testear
	 * @see net.sf.kfgodel.bean2bean.testbeans.BeanTester#compareWithSourceBean(java.lang.Object)
	 */
	public void compareWithSourceBean(SourceBean sourceBean) {
		Assert.notNull(sourceBean);
		Assert.equals(sourceBean.getPrimitivoInteger(), this.getPrimitivoInteger());
		Assert.equals(sourceBean.getObjetoDouble(), this.getObjetoDouble());
		Assert.equals(sourceBean.getObjetoFloat(), this.getObjetoFloat());
		Assert.equals(sourceBean.getObjetoInteger(), this.getObjetoInteger());
		Assert.equals(sourceBean.getObjetoString(), this.getObjetoString());
		Assert.equals(sourceBean.getPrimitivoDouble(), this.getPrimitivoDouble());
		Assert.equals(sourceBean.getPrimitivoFloat(), this.getPrimitivoFloat());
		Assert.Null(this.getAlwasNull());
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

	public Object getAlwasNull() {
		return alwasNull;
	}

	public void setAlwasNull(Object alwasNull) {
		this.alwasNull = alwasNull;
	}

	public Object getNeverCopied() {
		return neverCopied;
	}

	public void setNeverCopied(Object neverCopied) {
		this.neverCopied = neverCopied;
	}

}
