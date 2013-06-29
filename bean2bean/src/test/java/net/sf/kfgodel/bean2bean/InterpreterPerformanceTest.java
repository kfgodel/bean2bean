/**
 * Created on: 19/02/2010 22:07:21 by: Dario L. Garcia
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

import junit.framework.Assert;
import net.sf.kfgodel.bean2bean.exceptions.BadMappingException;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_Nested2Nested_Calls;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_Nested2Nested_FieldPrivate;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_Nested2Nested_FieldPublic;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_Nested2Nested_Mixed;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_Nested2Nested_Property;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_Nested2Non_Calls;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_Nested2Non_FieldPrivate;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_Nested2Non_FieldPublic;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_Nested2Non_Property;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_NoN2Nested_Calls;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_NoN2Nested_FieldPrivate;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_NoN2Nested_FieldPublic;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_NoN2Nested_Property;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_NoN2Non_Call;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_NoN2Non_FieldPrivate;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_NoN2Non_FieldPublic;
import net.sf.kfgodel.bean2bean.testbeans.performance.Groovy_NoN2Non_Property;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_Nested2Nested_Calls;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_Nested2Nested_FieldPrivate;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_Nested2Nested_FieldPublic;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_Nested2Nested_Mixed;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_Nested2Nested_Property;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_Nested2Non_Calls;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_Nested2Non_FieldPrivate;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_Nested2Non_FieldPublic;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_Nested2Non_Property;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_NoN2Nested_Calls;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_NoN2Nested_FieldPrivate;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_NoN2Nested_FieldPublic;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_NoN2Nested_Property;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_NoN2Non_Call;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_NoN2Non_FieldPrivate;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_NoN2Non_FieldPublic;
import net.sf.kfgodel.bean2bean.testbeans.performance.Ognl_NoN2Non_Property;
import net.sf.kfgodel.bean2bean.testbeans.performance.PerformanceTestBean;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_Nested2Nested_FieldPrivate;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_Nested2Nested_FieldPublic;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_Nested2Nested_Mixed;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_Nested2Nested_Property;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_Nested2Non_FieldPrivate;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_Nested2Non_FieldPublic;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_Nested2Non_Property;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_NoN2Nested_FieldPrivate;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_NoN2Nested_FieldPublic;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_NoN2Nested_Property;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_NoN2Non_FieldPrivate;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_NoN2Non_FieldPublic;
import net.sf.kfgodel.bean2bean.testbeans.performance.Reflection_NoN2Non_Property;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;

import org.junit.Before;
import org.junit.Test;

/**
 * Esta clase prueba la performace de las operaciones con distintos interpretes
 * 
 * @author D. Garcia
 */
public class InterpreterPerformanceTest {

	/**
	 * Number of tests to run to measure time
	 */
	private static final int TEST_COUNT = 100000;
	private Bean2Bean bean2Bean;

	@Before
	public void prepare() {
		bean2Bean = Bean2Bean.getInstance();
	}

	// --------------------
	// Non 2 Non
	// --------------------

	// OGNL no soporta privados
	@Test(expected = BadMappingException.class)
	public void testOgnl_Non2Non_fieldPrivate() {
		runSeveralTestsFor(Ognl_NoN2Non_FieldPrivate.class);
	}

	@Test
	public void testGroovy_Non2Non_fieldPrivate() {
		runSeveralTestsFor(Groovy_NoN2Non_FieldPrivate.class);
	}

	@Test
	public void testReflection_Non2Non_fieldPrivate() {
		runSeveralTestsFor(Reflection_NoN2Non_FieldPrivate.class);
	}

	// ----------- Public

	@Test
	public void testOgnl_Non2Non_fieldPublic() {
		runSeveralTestsFor(Ognl_NoN2Non_FieldPublic.class);
	}

	@Test
	public void testGroovy_Non2Non_fieldPublic() {
		runSeveralTestsFor(Groovy_NoN2Non_FieldPublic.class);
	}

	@Test
	public void testReflection_Non2Non_fieldPublic() {
		runSeveralTestsFor(Reflection_NoN2Non_FieldPublic.class);
	}

	// ----------- Property

	@Test
	public void testOgnl_Non2Non_Property() {
		runSeveralTestsFor(Ognl_NoN2Non_Property.class);
	}

	@Test
	public void testGroovy_Non2Non_Property() {
		runSeveralTestsFor(Groovy_NoN2Non_Property.class);
	}

	@Test
	public void testReflection_Non2Non_Property() {
		runSeveralTestsFor(Reflection_NoN2Non_Property.class);
	}

	// ----------- Calls

	@Test
	public void testOgnl_Non2Non_Call() {
		runSeveralTestsFor(Ognl_NoN2Non_Call.class);
	}

	@Test
	public void testGroovy_Non2Non_Call() {
		runSeveralTestsFor(Groovy_NoN2Non_Call.class);
	}

	// --------------------
	// Nested 2 Non
	// --------------------

	// OGNL no soporta privados
	@Test(expected = BadMappingException.class)
	public void testOgnl_Nested2Non_fieldPrivate() {
		runSeveralTestsFor(Ognl_Nested2Non_FieldPrivate.class);
	}

	@Test
	public void testGroovy_Nested2Non_fieldPrivate() {
		runSeveralTestsFor(Groovy_Nested2Non_FieldPrivate.class);
	}

	@Test
	public void testReflection_Nested2Non_fieldPrivate() {
		runSeveralTestsFor(Reflection_Nested2Non_FieldPrivate.class);
	}

	// ----------- Public

	@Test
	public void testOgnl_Nested2Non_fieldPublic() {
		runSeveralTestsFor(Ognl_Nested2Non_FieldPublic.class);
	}

	@Test
	public void testGroovy_Nested2Non_fieldPublic() {
		runSeveralTestsFor(Groovy_Nested2Non_FieldPublic.class);
	}

	@Test
	public void testReflection_Nested2Non_fieldPublic() {
		runSeveralTestsFor(Reflection_Nested2Non_FieldPublic.class);
	}

	// ----------- Property

	@Test
	public void testOgnl_Nested2Non_Property() {
		runSeveralTestsFor(Ognl_Nested2Non_Property.class);
	}

	@Test
	public void testGroovy_Nested2Non_Property() {
		runSeveralTestsFor(Groovy_Nested2Non_Property.class);
	}

	@Test
	public void testReflection_Nested2Non_Property() {
		runSeveralTestsFor(Reflection_Nested2Non_Property.class);
	}

	// ----------- Calls

	@Test
	public void testOgnl_Nested2Non_Calls() {
		runSeveralTestsFor(Ognl_Nested2Non_Calls.class);
	}

	@Test
	public void testGroovy_Nested2Non_Calls() {
		runSeveralTestsFor(Groovy_Nested2Non_Calls.class);
	}

	// --------------------
	// Non 2 Nested
	// --------------------

	// OGNL no soporta privados
	@Test(expected = BadMappingException.class)
	public void testOgnl_Non2Nested_fieldPrivate() {
		runSeveralTestsFor(Ognl_NoN2Nested_FieldPrivate.class);
	}

	@Test
	public void testGroovy_Non2Nested_fieldPrivate() {
		runSeveralTestsFor(Groovy_NoN2Nested_FieldPrivate.class);
	}

	@Test
	public void testReflection_Non2Nested_fieldPrivate() {
		runSeveralTestsFor(Reflection_NoN2Nested_FieldPrivate.class);
	}

	// ----------- Public

	@Test
	public void testOgnl_Non2Nested_fieldPublic() {
		runSeveralTestsFor(Ognl_NoN2Nested_FieldPublic.class);
	}

	@Test
	public void testGroovy_Non2Nested_fieldPublic() {
		runSeveralTestsFor(Groovy_NoN2Nested_FieldPublic.class);
	}

	@Test
	public void testReflection_Non2Nested_fieldPublic() {
		runSeveralTestsFor(Reflection_NoN2Nested_FieldPublic.class);
	}

	// ----------- Property

	@Test
	public void testOgnl_NoN2Nested_Property() {
		runSeveralTestsFor(Ognl_NoN2Nested_Property.class);
	}

	@Test
	public void testGroovy_Non2Nested_Property() {
		runSeveralTestsFor(Groovy_NoN2Nested_Property.class);
	}

	@Test
	public void testReflection_Non2Nested_Property() {
		runSeveralTestsFor(Reflection_NoN2Nested_Property.class);
	}

	// ----------- Calls

	@Test
	public void testOgnl_NoN2Nested_Calls() {
		runSeveralTestsFor(Ognl_NoN2Nested_Calls.class);
	}

	@Test
	public void testGroovy_NoN2Nested_Calls() {
		runSeveralTestsFor(Groovy_NoN2Nested_Calls.class);
	}

	// --------------------
	// Nested 2 Nested
	// --------------------

	// OGNL no soporta privados
	@Test(expected = BadMappingException.class)
	public void testOgnl_Nested2Nested_fieldPrivate() {
		runSeveralTestsFor(Ognl_Nested2Nested_FieldPrivate.class);
	}

	@Test
	public void testGroovy_Nested2Nested_fieldPrivate() {
		runSeveralTestsFor(Groovy_Nested2Nested_FieldPrivate.class);
	}

	@Test
	public void testReflection_Nested2Nested_fieldPrivate() {
		runSeveralTestsFor(Reflection_Nested2Nested_FieldPrivate.class);
	}

	// ----------- Public

	@Test
	public void testOgnl_Nested2Nested_fieldPublic() {
		runSeveralTestsFor(Ognl_Nested2Nested_FieldPublic.class);
	}

	@Test
	public void testGroovy_Nested2Nested_fieldPublic() {
		runSeveralTestsFor(Groovy_Nested2Nested_FieldPublic.class);
	}

	@Test
	public void testReflection_Nested2Nested_fieldPublic() {
		runSeveralTestsFor(Reflection_Nested2Nested_FieldPublic.class);
	}

	// ----------- Property

	@Test
	public void testOgnl_Nested2Nested_Property() {
		runSeveralTestsFor(Ognl_Nested2Nested_Property.class);
	}

	@Test
	public void testGroovy_Nested2Nested_Property() {
		runSeveralTestsFor(Groovy_Nested2Nested_Property.class);
	}

	@Test
	public void testReflection_Nested2Nested_Property() {
		runSeveralTestsFor(Reflection_Nested2Nested_Property.class);
	}

	// ----------- Calls

	@Test
	public void testOgnl_Nested2Nested_Calls() {
		runSeveralTestsFor(Ognl_Nested2Nested_Calls.class);
	}

	@Test
	public void testGroovy_Nested2Nested_Calls() {
		runSeveralTestsFor(Groovy_Nested2Nested_Calls.class);
	}

	// ----------- Mixed

	@Test
	public void testOgnl_Nested2Nested_Mixed() {
		runSeveralTestsFor(Ognl_Nested2Nested_Mixed.class);
	}

	@Test
	public void testGroovy_Nested2Nested_Mixed() {
		runSeveralTestsFor(Groovy_Nested2Nested_Mixed.class);
	}

	@Test
	public void testReflection_Nested2Nested_Mixed() {
		runSeveralTestsFor(Reflection_Nested2Nested_Mixed.class);
	}

	/**
	 * Runs the conversion several times to measure time for the selected class.<br>
	 * The test creates an original bean makes a conversion using CopyFrom annotations, and the
	 * converts it again to a final Bean using CopyTo annotation.<br>
	 * {@link #TEST_COUNT} determines how many times the process is done
	 */
	private void runSeveralTestsFor(final Class<? extends PerformanceTestBean> testedClass) {
		final PerformanceTestBean originalBean = ReflectionUtils.createInstance(testedClass);
		final String value = "Move me";
		originalBean.prepareValue(value);
		for (int i = 0; i < TEST_COUNT; i++) {
			final PerformanceTestBean intermediate = bean2Bean.createFrom(originalBean, testedClass);
			Assert.assertNotSame("Should be a different bean", originalBean, intermediate);
			Assert.assertEquals("Intermediate bean should have the value", value, intermediate.getDestinationValue());
		}
	}
}
