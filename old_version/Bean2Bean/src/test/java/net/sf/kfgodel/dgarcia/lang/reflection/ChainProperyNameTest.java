/**
 * Created on: 16/02/2010 16:48:21 by: Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.lang.reflection;

import java.util.regex.Matcher;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Esta clase solo controla los casos conocidos de nombres de variable (por si aparecen nuevos)
 * 
 * @author D. Garcia
 */
public class ChainProperyNameTest {

	@Test
	public void testProperyChains() {
		checkValidChain("enigma");
		checkValidChain("enigma.resuelto");
		checkValidChain("_$nombreMedio.raro_y_conCarac.a");
		checkValidChain("$$");
		checkValidChain("ASDASD_ASDAS");
		checkValidChain("getEnvoltorio");
		checkValidChain("a.b.c.d.e.f.g.h.y");
		checkValidChain("_._._._");
		checkValidChain("_1._2._3._4");

		// Los acentos son falsos negativos!
		checkInvalidChain("enigma.sin.resolver.todavía");
		checkInvalidChain("_.");
		checkInvalidChain("");
		checkInvalidChain("getEnvoltorio()");
		checkInvalidChain("_1.2._3._4");
	}

	/**
	 * Prueba si la cadean de propiedades pasadas es valida
	 */
	private void checkValidChain(String testChain) {
		checkExpression(testChain, true);
	}

	/**
	 * Chequea y muestra la expresion
	 */
	private void checkExpression(String testChain, boolean expected) {
		Matcher matcher = ReflectionUtils.propertyChainPattern.matcher(testChain);
		boolean found = false;
		while (matcher.find()) {
			System.out.format("Matched:\"%s\" %d-%d.%n", matcher.group(), matcher.start(), matcher.end());
			found = expected;
		}
		if (!found) {
			System.out.format("Sin match.%n");
		}

		String mensaje = (expected ? "debería dar OK" : "debería fallar");
		Assert.assertEquals("[" + testChain + "] " + mensaje, expected, matcher.matches());
	}

	/**
	 * Prueba si la cadean de propiedades pasadas es valida
	 */
	private void checkInvalidChain(String testChain) {
		checkExpression(testChain, false);
	}

}
