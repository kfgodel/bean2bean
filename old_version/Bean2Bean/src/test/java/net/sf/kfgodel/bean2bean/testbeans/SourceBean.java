/**
 * Created on 20/12/2007 20:43:26 Copyright (C) 2007 Dario L. Garcia
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

import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase representa un bean tipico que sirve de fuente de datos para los demas beans que son
 * "mirrors" de este
 * 
 * @version 1.0
 * @since 20/12/2007
 * @author D. Garcia
 */
public class SourceBean {
	private List<SourceBean> listaObjetos;
	private Double objetoDouble;
	private TipoEnumerado objetoEnum;
	private Float objetoFloat;
	private Integer objetoInteger;
	private SourceBean objetoPlano;
	private String objetoString;
	private double primitivoDouble;
	private float primitivoFloat;
	private int primitivoInteger;

	public List<SourceBean> getListaObjetos() {
		if (this.listaObjetos == null) {
			this.listaObjetos = new ArrayList<SourceBean>();
		}
		return this.listaObjetos;
	}

	public Double getObjetoDouble() {
		return this.objetoDouble;
	}

	public TipoEnumerado getObjetoEnum() {
		return this.objetoEnum;
	}

	public Float getObjetoFloat() {
		return this.objetoFloat;
	}

	public Integer getObjetoInteger() {
		return this.objetoInteger;
	}

	public SourceBean getObjetoPlano() {
		return this.objetoPlano;
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

	public void setListaObjetos(List<SourceBean> listaObjetos) {
		this.listaObjetos = listaObjetos;
	}

	public void setObjetoDouble(Double objetoDouble) {
		this.objetoDouble = objetoDouble;
	}

	public void setObjetoEnum(TipoEnumerado enumerado) {
		this.objetoEnum = enumerado;
	}

	public void setObjetoFloat(Float objetoFloat) {
		this.objetoFloat = objetoFloat;
	}

	public void setObjetoInteger(Integer objetoInteger) {
		this.objetoInteger = objetoInteger;
	}

	public void setObjetoPlano(SourceBean objetoPlano) {
		this.objetoPlano = objetoPlano;
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
