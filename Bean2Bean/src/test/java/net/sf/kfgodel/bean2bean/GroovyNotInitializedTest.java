/**
 * 06/03/2012 14:03:58 Copyright (C) 2011 Darío L. García
 * 
 * <a rel="license" href="http://creativecommons.org/licenses/by/3.0/"><img
 * alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/by/3.0/88x31.png" /></a><br />
 * <span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/Text"
 * property="dct:title" rel="dct:type">Software</span> by <span
 * xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Darío García</span> is
 * licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/3.0/">Creative
 * Commons Attribution 3.0 Unported License</a>.
 */
package net.sf.kfgodel.bean2bean;

import junit.framework.Assert;
import net.sf.kfgodel.bean2bean.testbeans.GroovyInitBean;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests how groovy engine slows down conversion when not initialized
 * 
 * @author D. García
 */
public class GroovyNotInitializedTest {
	private static final Logger logger = Logger.getLogger(GroovyInitializedTest.class);

	private Bean2Bean bean2Bean;

	@Before
	public void createBean2Bean() {
		bean2Bean = Bean2Bean.createDefaultInstance();
	}

	/**
	 * This test cannot be run with others as it relies on groovy engine not beeing initialized
	 */
	@Test
	public void itShouldTakeLongerTheFirstTimeIfNotInitialized() {
		final GroovyInitBean source = GroovyInitBean.create("un texto");

		// Medimos cuanto tarda en resolver la expresión
		final long startTime = System.currentTimeMillis();
		bean2Bean.createFrom(source, GroovyInitBean.class);
		final long endTime = System.currentTimeMillis();
		final long elapsed = endTime - startTime;

		// Verificamos que lleva mucho
		Assert.assertTrue(elapsed > 1000);
		logger.debug("Convertido en: " + elapsed + " ms. con groovy sin inicializar");
	}

}
