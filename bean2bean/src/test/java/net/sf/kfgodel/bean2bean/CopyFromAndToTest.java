/**
 * Created on: 03/03/2010 20:23:28 by: Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.annotations.CopyFromAndTo;
import net.sf.kfgodel.bean2bean.conversion.DefaultTypeConverter;
import net.sf.kfgodel.bean2bean.inv.ClaseConDatos;
import net.sf.kfgodel.bean2bean.testbeans.copyfromto.ClaseEspejo;

import org.junit.Test;

/**
 * This class tests usage of {@link CopyFromAndTo} annotation
 * 
 * @author D. Garcia
 */
public class CopyFromAndToTest {

	@Test
	public void testCreationFromAnnotation() {
		final ClaseConDatos datos = ClaseConDatos.create();
		final ClaseEspejo populable = Bean2Bean.getInstance().createFrom(datos, ClaseEspejo.class);
		populable.verificarContra(datos);
	}

	@Test
	public void testConversionFromAnnotation() {
		final ClaseConDatos datos = ClaseConDatos.create();
		final ClaseEspejo populable = DefaultTypeConverter.getInstance().convertValue(datos, ClaseEspejo.class);
		populable.verificarContra(datos);
	}

	@Test
	public void testCreationToAnnotation() {
		final ClaseEspejo populadora = ClaseEspejo.create();
		final ClaseConDatos populada = Bean2Bean.getInstance().convertTo(ClaseConDatos.class, populadora);
		populadora.verificarContra(populada);
	}

	@Test
	public void testConversionToAnnotation() {
		final ClaseEspejo populadora = ClaseEspejo.create();
		final ClaseConDatos populada = DefaultTypeConverter.getInstance().convertValue(populadora, ClaseConDatos.class);
		populadora.verificarContra(populada);
	}

}
