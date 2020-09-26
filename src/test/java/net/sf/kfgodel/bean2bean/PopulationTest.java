/**
 * Created on: 14/01/2009 22:58:26 by: Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.exceptions.BadMappingException;
import net.sf.kfgodel.bean2bean.inv.ClaseConDatos;
import net.sf.kfgodel.bean2bean.inv.ClasePopulable;
import net.sf.kfgodel.bean2bean.inv.ClasePopulada;
import net.sf.kfgodel.bean2bean.inv.ClasePopuladora;
import net.sf.kfgodel.bean2bean.testbeans.TypeMismatchUsingOngl;
import net.sf.kfgodel.dgarcia.testing.Assert;
import net.sf.kfgodel.dgarcia.testing.CodeThatShouldFail;
import org.junit.Before;
import org.junit.Test;

/**
 * Esta clase testea los diferentes usos de la API para indicar como popular los beans
 * 
 * @author D.Garcia
 */
public class PopulationTest {

	private Bean2Bean bean2Bean;

	@Before
	public void createBean2Bean() {
		bean2Bean = Bean2Bean.createDefaultInstance();
	}

	@Test
	public void testCopyFromAnnotation() {
		final ClaseConDatos datos = ClaseConDatos.create();
		final ClasePopulable populable = bean2Bean.createFrom(datos, ClasePopulable.class);
		populable.verificarContra(datos);
	}

	@Test
	public void testCopyToAnnotation() {
		final ClasePopuladora populadora = ClasePopuladora.create();
		final ClasePopulada populada = bean2Bean.convertTo(ClasePopulada.class, populadora);
		populadora.verificarContra(populada);
	}

	@Test
	public void testErroneousMappings() {
		Assert.exceptionOn(new CodeThatShouldFail() {
			public void doTheFaultyThing() {
				final TypeMismatchUsingOngl testBean = new TypeMismatchUsingOngl();
				testBean.setCadena("Not a number");
				bean2Bean.createFrom(testBean, TypeMismatchUsingOngl.class);
			}
		}, BadMappingException.class);
	}
}
