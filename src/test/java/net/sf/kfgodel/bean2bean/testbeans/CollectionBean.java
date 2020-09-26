/**
 * Created on 22/12/2007 18:07:21 Copyright (C) 2007 Dario L. Garcia
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
import net.sf.kfgodel.dgarcia.testing.Assert;

import java.util.HashMap;
import java.util.List;

/**
 * Esta clase es un bean con coleccion de objetos transformables
 * 
 * @version 1.0
 * @since 22/12/2007
 * @author D. Garcia
 */
public class CollectionBean implements BeanTester<SourceBean> {

	@CopyFrom
	@CopyTo
	private List<CollectionBean> listaObjetos;

	@CopyFrom("objetoString")
	@CopyTo("objetoString")
	private String nombre;

	/**
	 * @param bean
	 * @see net.sf.kfgodel.bean2bean.testbeans.BeanTester#compareWithDestinationBean(java.lang.Object)
	 */
	public void compareWithDestinationBean(SourceBean bean) {
		Assert.notNull(bean);
		Assert.equals(this.getNombre(), bean.getObjetoString());
		List<SourceBean> objetos = bean.getListaObjetos();
		if (this.getListaObjetos() == null) {
			Assert.Null(objetos);
		}
		Assert.equals(this.getListaObjetos().size(), objetos.size());

		// TODO chequear mejor, por ahora funciona por que el ejemplo es simple
		HashMap<CollectionBean, SourceBean> instancias = new HashMap<CollectionBean, SourceBean>();
		for (int i = 0; i < this.getListaObjetos().size(); i++) {
			CollectionBean cBean = this.getListaObjetos().get(i);
			SourceBean sourceBean = objetos.get(i);
			SourceBean storedSource = instancias.get(cBean);
			if (storedSource != null) {
				Assert.same(sourceBean, storedSource);
				continue;
			}
			Assert.equals(cBean.getNombre(), sourceBean.getObjetoString());
		}
	}

	/**
	 * @param bean
	 * @see net.sf.kfgodel.bean2bean.testbeans.BeanTester#compareWithSourceBean(java.lang.Object)
	 */
	public void compareWithSourceBean(SourceBean bean) {
		Assert.notNull(bean);
		Assert.equals(bean.getObjetoString(), this.getNombre());

		List<SourceBean> objetos = bean.getListaObjetos();
		if (objetos == null) {
			Assert.Null(this.getListaObjetos());
			return;
		}
		Assert.notNull(this.getListaObjetos());

		Assert.equals(objetos.size(), this.getListaObjetos().size());

		// TODO chequear mejor, por ahora funciona por que el ejemplo es simple
		HashMap<SourceBean, CollectionBean> instancias = new HashMap<SourceBean, CollectionBean>();
		for (int i = 0; i < objetos.size(); i++) {
			SourceBean sourceBean = objetos.get(i);
			CollectionBean cBean = this.getListaObjetos().get(i);
			CollectionBean storedCBean = instancias.get(sourceBean);
			if (storedCBean != null) {
				Assert.same(storedCBean, cBean);
				continue;
			}
			Assert.equals(sourceBean.getObjetoString(), cBean.getNombre());
		}

	}

	public List<CollectionBean> getListaObjetos() {
		return this.listaObjetos;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setListaObjetos(List<CollectionBean> listaObjetos) {
		this.listaObjetos = listaObjetos;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
