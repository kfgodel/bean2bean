/**
 * Created on 22/12/2007 02:19:51 Copyright (C) 2007 Dario L. Garcia
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
import net.sf.kfgodel.bean2bean.interpreters.InterpreterType;
import net.sf.kfgodel.dgarcia.testing.Assert;

/**
 * Esta clase prueba las posibles maneras de transformar un dato enumerado
 * 
 * @version 1.0
 * @since 22/12/2007
 * @author D. Garcia
 */
public class EnumerationBean implements BeanTester<SourceBean> {

	@CopyFrom("objetoEnum.codigo")
	@CopyTo(value = "objetoEnum", getter = "@net.sf.kfgodel.bean2bean.testbeans.TipoEnumerado@getTipoFrom(codigoEnum)", getterInterpreter = InterpreterType.OGNL)
	private String codigoEnum;

	@CopyFrom("objetoEnum")
	@CopyTo("objetoEnum")
	private TipoEnumerado enumerado;

	@CopyFrom("objetoEnum")
	@CopyTo("objetoEnum")
	private String enumeradoPorName;

	@CopyFrom("objetoEnum")
	@CopyTo("objetoEnum")
	private int enumeradoPorOrdinal;

	/**
	 * @param destination
	 * @see net.sf.kfgodel.bean2bean.testbeans.BeanTester#compareWithDestinationBean(java.lang.Object)
	 */
	public void compareWithDestinationBean(SourceBean destination) {
		Assert.notNull(destination);
		Assert.equals(this.getEnumerado(), destination.getObjetoEnum());
		Assert.equals(this.getEnumeradoPorName(), destination.getObjetoEnum().name());
		Assert.equals(this.getEnumeradoPorOrdinal(), destination.getObjetoEnum().ordinal());
		Assert.equals(this.getCodigoEnum(), destination.getObjetoEnum().getCodigo());
	}

	/**
	 * @param source
	 * @see net.sf.kfgodel.bean2bean.testbeans.BeanTester#compareWithSourceBean(java.lang.Object)
	 */
	public void compareWithSourceBean(SourceBean source) {
		Assert.notNull(source);
		Assert.equals(source.getObjetoEnum(), this.getEnumerado());
		Assert.equals(source.getObjetoEnum().name(), this.getEnumeradoPorName());
		Assert.equals(source.getObjetoEnum().ordinal(), this.getEnumeradoPorOrdinal());
		Assert.equals(source.getObjetoEnum().getCodigo(), this.getCodigoEnum());
	}

	public String getCodigoEnum() {
		return this.codigoEnum;
	}

	public TipoEnumerado getEnumerado() {
		return this.enumerado;
	}

	public String getEnumeradoPorName() {
		return this.enumeradoPorName;
	}

	public int getEnumeradoPorOrdinal() {
		return this.enumeradoPorOrdinal;
	}

	public void setCodigoEnum(String codigoEnum) {
		this.codigoEnum = codigoEnum;
	}

	public void setEnumerado(TipoEnumerado enumerado) {
		this.enumerado = enumerado;
	}

	public void setEnumeradoPorName(String enumeradoPorName) {
		this.enumeradoPorName = enumeradoPorName;
	}

	public void setEnumeradoPorOrdinal(int enumeradoPorOrdinal) {
		this.enumeradoPorOrdinal = enumeradoPorOrdinal;
	}
}
