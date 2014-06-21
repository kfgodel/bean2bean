/**
 * Created on 22/12/2007 02:17:02 Copyright (C) 2007 Dario L. Garcia
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

import java.util.HashMap;

import net.sf.kfgodel.bean2bean.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.annotations.CopyTo;
import net.sf.kfgodel.bean2bean.testbeans.BeanTester;
import net.sf.kfgodel.bean2bean.testbeans.SourceBean;
import net.sf.kfgodel.dgarcia.testing.Assert;


/**
 * Esta clase representa un bean que tiene referencia circular a si mismo
 * 
 * @version 1.0
 * @since 22/12/2007
 * @author D. Garcia
 */
public class RecursiveBean implements BeanTester<SourceBean> {

	// Esta propiedad sirve de nombre identificatorio
	@CopyFrom("objetoString")
	@CopyTo("objetoString")
	private String nombre;

	@CopyFrom("objetoPlano")
	@CopyTo("objetoPlano")
	private RecursiveBean otherBean;

	/**
	 * @param destination
	 * @see net.sf.kfgodel.bean2bean.testbeans.BeanTester#compareWithDestinationBean(java.lang.Object)
	 */
	public void compareWithDestinationBean(SourceBean destination) {
		Assert.notNull(destination);
		HashMap<RecursiveBean, SourceBean> instances = new HashMap<RecursiveBean, SourceBean>();
		RecursiveBean source = this;
		while (source != null) {
			Assert.equals(source.getNombre(), destination.getObjetoString());
			instances.put(source, destination);
			source = source.getOtherBean();
			destination = destination.getObjetoPlano();
			SourceBean storedDestination = instances.get(source);
			if (storedDestination != null) {
				Assert.same(storedDestination, destination);
			}
			if (source == null) {
				Assert.Null(destination);
			}
			if (instances.keySet().contains(source)) {
				// Corta el bucle si se repite un objeto en la referencia
				break;
			}
		}
	}

	/**
	 * @param source
	 * @see net.sf.kfgodel.bean2bean.testbeans.BeanTester#compareWithSourceBean(java.lang.Object)
	 */
	public void compareWithSourceBean(SourceBean source) {
		Assert.notNull(source);
		HashMap<SourceBean, RecursiveBean> instances = new HashMap<SourceBean, RecursiveBean>();
		RecursiveBean destination = this;
		while (source != null) {
			Assert.equals(source.getObjetoString(), destination.getNombre());
			instances.put(source, destination);
			source = source.getObjetoPlano();
			destination = destination.getOtherBean();
			RecursiveBean storedDestination = instances.get(source);
			if (storedDestination != null) {
				Assert.same(storedDestination, destination);
			}
			if (source == null) {
				Assert.Null(destination);
			}
			if (instances.keySet().contains(source)) {
				// Corta el bucle si se repite un objeto en la referencia
				break;
			}
		}
	}

	public String getNombre() {
		return this.nombre;
	}

	public RecursiveBean getOtherBean() {
		return this.otherBean;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setOtherBean(RecursiveBean otherBean) {
		this.otherBean = otherBean;
	}

}
