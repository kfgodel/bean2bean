/**
 * Created on 20/12/2007 20:20:44 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean;

import java.util.Arrays;

import net.sf.kfgodel.bean2bean.Bean2Bean;
import net.sf.kfgodel.bean2bean.testbeans.BeanTester;
import net.sf.kfgodel.bean2bean.testbeans.CollectionBean;
import net.sf.kfgodel.bean2bean.testbeans.EnumerationBean;
import net.sf.kfgodel.bean2bean.testbeans.PlainPrimitiveBean;
import net.sf.kfgodel.bean2bean.testbeans.RecursiveBean;
import net.sf.kfgodel.bean2bean.testbeans.SourceBean;
import net.sf.kfgodel.bean2bean.testbeans.TipoEnumerado;
import net.sf.kfgodel.bean2bean.testbeans.TransformedPrimitiveBean;
import net.sf.kfgodel.dgarcia.testing.Assert;

import org.junit.Test;


/**
 * Esta clase testea la creacion de bean a partir de los annotations de TOs
 * 
 * @version 1.0
 * @since 20/12/2007
 * @author D. Garcia
 */
public class BeanCreationTest {

	/**
	 * Comprueba la creacion del tipo indicado utilizando al propio bean creado para el chequeo de
	 * los datos
	 * 
	 * @param destinationType
	 *            Tipo de bean a crear desde el {@link SourceBean}
	 */
	private <T extends BeanTester<SourceBean>> void checkFromCreation(Class<T> destinationType) {
		SourceBean sourceBean = this.createSourceBean();
		T createdBean = Bean2Bean.getInstance().createFrom(sourceBean, destinationType);
		Assert.notNull(createdBean);
		createdBean.compareWithSourceBean(sourceBean);
	}

	/**
	 * Comprueba la creacion de la instancia source a partir de la instancia pasada
	 */
	private void checkToCreation(BeanTester<SourceBean> bean) {
		SourceBean createdSource = Bean2Bean.getInstance().convertTo(SourceBean.class, bean);
		Assert.notNull(createdSource);
		bean.compareWithDestinationBean(createdSource);
	}

	/**
	 * Crea un bean con coleccion de otros beans
	 * 
	 * @return El bean con referencias circulares a otros beans
	 */
	private BeanTester<SourceBean> createCollectionBean() {
		CollectionBean secondBean = new CollectionBean();
		secondBean.setNombre("secondBean");
		secondBean.setListaObjetos(Arrays.asList(secondBean));

		CollectionBean rootBean = new CollectionBean();
		rootBean.setNombre("rootBean");
		rootBean.setListaObjetos(Arrays.asList(rootBean, secondBean));
		return rootBean;
	}

	/**
	 * Crea un bean con enums
	 * 
	 * @return El bean para utilizar en los testeos
	 */
	private BeanTester<SourceBean> createEnumeratedBean() {
		EnumerationBean enumerationBean = new EnumerationBean();
		enumerationBean.setEnumerado(TipoEnumerado.NORTE);
		enumerationBean.setCodigoEnum(enumerationBean.getEnumerado().getCodigo());
		enumerationBean.setEnumeradoPorName(enumerationBean.getEnumerado().name());
		enumerationBean.setEnumeradoPorOrdinal(enumerationBean.getEnumerado().ordinal());
		return enumerationBean;
	}

	/**
	 * Crea un nuevo bean plano con datos de prueba
	 * 
	 * @return El bean creado
	 */
	private PlainPrimitiveBean createPlainBean() {
		PlainPrimitiveBean plainBean = new PlainPrimitiveBean();
		plainBean.setObjetoDouble(2d);
		plainBean.setObjetoFloat(3f);
		plainBean.setObjetoInteger(4);
		plainBean.setObjetoString("5");
		plainBean.setPrimitivoDouble(6d);
		plainBean.setPrimitivoFloat(7f);
		plainBean.setPrimitivoInteger(8);
		return plainBean;
	}

	/**
	 * Crea un bean recursivo que tiene referencias circulares indirectas a sí mismo
	 * 
	 * @return El bean creado para testing
	 */
	private BeanTester<SourceBean> createRecursiveBean() {
		RecursiveBean secondBean = new RecursiveBean();
		secondBean.setNombre("secondBean");

		RecursiveBean rootBean = new RecursiveBean();
		rootBean.setNombre("rootBean");
		rootBean.setOtherBean(secondBean);
		secondBean.setOtherBean(rootBean);

		return rootBean;
	}

	/**
	 * Crea un nuevo bean source con referencia indirecta a si mismo, y todos los datos llenos
	 * 
	 * @return El bean creado
	 */
	private SourceBean createSourceBean() {
		SourceBean rootBean = new SourceBean();
		rootBean.setObjetoDouble(1d);
		rootBean.setObjetoFloat(2f);
		rootBean.setObjetoInteger(3);
		rootBean.setObjetoString("4");
		rootBean.setPrimitivoDouble(5d);
		rootBean.setPrimitivoFloat(6f);
		rootBean.setPrimitivoInteger(7);
		rootBean.setObjetoEnum(TipoEnumerado.NORTE);

		SourceBean secondBean = new SourceBean();
		secondBean.setObjetoDouble(8d);
		secondBean.setObjetoFloat(9f);
		secondBean.setObjetoInteger(10);
		secondBean.setObjetoString("11");
		secondBean.setPrimitivoDouble(12d);
		secondBean.setPrimitivoFloat(13f);
		secondBean.setPrimitivoInteger(14);
		secondBean.setObjetoEnum(TipoEnumerado.SUR);
		secondBean.setObjetoPlano(rootBean);
		secondBean.getListaObjetos().add(secondBean);

		rootBean.setObjetoPlano(secondBean);
		rootBean.getListaObjetos().add(rootBean);
		rootBean.getListaObjetos().add(secondBean);
		return rootBean;
	}

	/**
	 * Crea un bean con datos tranformados con respecto al {@link SourceBean}
	 * 
	 * @return El bean para testing
	 */
	private BeanTester<SourceBean> createTransformedBean() {
		TransformedPrimitiveBean transformedPrimitiveBean = new TransformedPrimitiveBean();
		transformedPrimitiveBean.setObjetoDouble(1d);
		// El valor de float debe coincidir con el del string por que
		// ambos asignan a la misma propiedad destino
		transformedPrimitiveBean.setObjetoFloat(4f);
		transformedPrimitiveBean.setObjetoInteger(3);
		transformedPrimitiveBean.setObjetoString("4");
		transformedPrimitiveBean.setPrimitivoDouble(5d);
		transformedPrimitiveBean.setPrimitivoFloat(6f);
		transformedPrimitiveBean.setPrimitivoInteger(7);
		transformedPrimitiveBean.setJsonString("4");
		return transformedPrimitiveBean;
	}

	/**
	 * Comprueba la copia de datos hacia el fuente desde uno que utiliza colecciones
	 */
	@Test
	public void testCollection2Source() {
		this.checkToCreation(this.createCollectionBean());
	}

	/**
	 * Comprueba la copia de datos hacia el fuente desde uno que utiliza enums
	 */
	@Test
	public void testEnumerated2Source() {
		this.checkToCreation(this.createEnumeratedBean());
	}

	/**
	 * Comprueba la copia de datos primitivos hacia un bean
	 */
	@Test
	public void testPlain2SourceCopy() {
		this.checkToCreation(this.createPlainBean());
	}

	/**
	 * Comprueba la copia de datos hacia el fuente desde uno que tiene referencias recursivas
	 */
	@Test
	public void testRecursive2Source() {
		this.checkToCreation(this.createRecursiveBean());
	}

	/**
	 * Comprueba la copia de datos desde el fuente a uno que utiliza colecciones
	 */
	@Test
	public void testSource2Collection() {
		this.checkFromCreation(CollectionBean.class);
	}

	/**
	 * Comprueba la copia de datos desde el fuente a uno que utiliza enums
	 */
	@Test
	public void testSource2Enumerated() {
		this.checkFromCreation(EnumerationBean.class);
	}

	/**
	 * Comprueba la copia de datos primitivos hacia un bean
	 */
	@Test
	public void testSource2PlainCopy() {
		this.checkFromCreation(PlainPrimitiveBean.class);
	}

	/**
	 * Comprueba la copia de datos desde el fuente a uno que tiene referenicas recursivas
	 */
	@Test
	public void testSource2Recursive() {
		this.checkFromCreation(RecursiveBean.class);
	}

	/**
	 * Comprueba la copia de datos desde el fuente a uno que tranforma los datos
	 */
	@Test
	public void testSource2Transformed() {
		this.checkFromCreation(TransformedPrimitiveBean.class);
	}

	/**
	 * Comprueba la copia de datos hacia el fuente desde uno que tranforma los datos
	 */
	@Test
	public void testTransformed2Source() {
		this.checkToCreation(this.createTransformedBean());
	}
}
