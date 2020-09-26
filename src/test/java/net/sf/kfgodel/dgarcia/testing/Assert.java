/**
 * Created on 09/01/2007 23:09:45 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.testing;

import java.util.Arrays;

/**
 * Esta clase modifica los metodos de asercion comunes de JUnit, incorporando algunos otros que
 * faltan
 * 
 * @version 1.0
 * @since 09/01/2007
 * @author D. Garcia
 */
public class Assert {

	/**
	 * Verifica que los numeros pasados sean iguales
	 * 
	 * @param expected
	 *            Valor esperado
	 * @param tested
	 *            Valor a comprobar
	 */
	public static void equals(double expected, double tested) {
		junit.framework.Assert.assertEquals(expected, tested);
	}

	/**
	 * Verifica que los numeros pasados sean iguales
	 * 
	 * @param expected
	 *            Valor esperado
	 * @param tested
	 *            Valor a comprobar
	 */
	public static void equals(int expected, int tested) {
		junit.framework.Assert.assertEquals(expected, tested);
	}

	/**
	 * Verifica que los dos objetos pasados sean iguales
	 * 
	 * @param expected
	 *            Objeto modelo
	 * @param tested
	 *            Objeto a comprobar
	 */
	public static void equals(Object expected, Object tested) {
		if (expected != null) {
			if (expected.getClass().isArray()) {
				equalsArrays(expected, tested);
				return;
			}
		}
		junit.framework.Assert.assertEquals(expected, tested);
	}

	/**
	 * Verifica que el objeto testeado sea un array igual al pasado. La definicion de igualdad
	 * utilizada es la misma que la de {@link Arrays}. Para los tipos primitivos dos arrays son
	 * considerados iguales si tienen el mismo tipo de componente (a diferencia de los objetos).
	 * 
	 * @param expected
	 *            Array esperado
	 * @param tested
	 *            Objeto a testear por igualdad
	 */
	private static void equalsArrays(Object expected, Object tested) {
		if (expected == null) {
			Null(tested);
		}
		Class<?> expectedClass = expected.getClass();
		if (!expectedClass.isArray()) {
			fail("An array is expected:" + expected);
		}
		Class<?> testedClass = tested.getClass();
		if (!testedClass.isArray()) {
			fail("Tested value is not an array. Expected:<" + expected + "> passed:<" + tested + ">");
		}
		Class<?> expectedComponent = expectedClass.getComponentType();
		Class<?> testedComponent = testedClass.getComponentType();
		if (expectedComponent.isPrimitive()) {
			True(testedComponent.isPrimitive());
			equals(expectedComponent, testedComponent);
			if (expectedComponent.equals(Boolean.TYPE)) {
				boolean[] expectedArray = (boolean[]) expected;
				boolean[] testedArray = (boolean[]) tested;
				True(Arrays.equals(expectedArray, testedArray));
			}
			else if (expectedComponent.equals(Byte.TYPE)) {
				byte[] expectedArray = (byte[]) expected;
				byte[] testedArray = (byte[]) tested;
				True(Arrays.equals(expectedArray, testedArray));
			}
			else if (expectedComponent.equals(Character.TYPE)) {
				char[] expectedArray = (char[]) expected;
				char[] testedArray = (char[]) tested;
				True(Arrays.equals(expectedArray, testedArray));
			}
			else if (expectedComponent.equals(Short.TYPE)) {
				short[] expectedArray = (short[]) expected;
				short[] testedArray = (short[]) tested;
				True(Arrays.equals(expectedArray, testedArray));
			}
			else if (expectedComponent.equals(Integer.TYPE)) {
				int[] expectedArray = (int[]) expected;
				int[] testedArray = (int[]) tested;
				True(Arrays.equals(expectedArray, testedArray));
			}
			else if (expectedComponent.equals(Float.TYPE)) {
				float[] expectedArray = (float[]) expected;
				float[] testedArray = (float[]) tested;
				True(Arrays.equals(expectedArray, testedArray));
			}
			else if (expectedComponent.equals(Double.TYPE)) {
				double[] expectedArray = (double[]) expected;
				double[] testedArray = (double[]) tested;
				True(Arrays.equals(expectedArray, testedArray));
			}
			else if (expectedComponent.equals(Long.TYPE)) {
				long[] expectedArray = (long[]) expected;
				long[] testedArray = (long[]) tested;
				True(Arrays.equals(expectedArray, testedArray));
			}
			else {
				fail("There's no array for this type[" + expectedComponent + "]!");
			}
		}
		else {
			Object[] expectedArray = (Object[]) expected;
			Object[] testedArray = (Object[]) tested;
			True(Arrays.equals(expectedArray, testedArray));
		}
	}

	/**
	 * Verifica que la ejecucion de un bloque de codigo falle segun el tipo de excepcion indicada.
	 * Si no sucede asi, este metodo falla
	 * 
	 * @param <E>
	 *            Tipo de excepcion esperada
	 * @param code
	 *            Codigo a ejecutar
	 * @param exceptionType
	 *            Tipo de excepcion esperada
	 * @return La excepcion ocurrida
	 */
	@SuppressWarnings("unchecked")
	public static <E extends RuntimeException> E exceptionOn(CodeThatShouldFail code, Class<E> exceptionType) {
		try {
			code.doTheFaultyThing();
			junit.framework.Assert.fail("An exception was expected: " + exceptionType);
		}
		catch (RuntimeException e) {
			if (e.getClass().equals(exceptionType)) {
				return (E) e;
			}
			throw e;
		}
		// Nunca se llega realmente a este punto
		return null;
	}

	/**
	 * Falla la comprobacion con el mensaje indicado
	 * 
	 * @param message
	 *            Mensaje para mostrar como error
	 */
	public static void fail(String message) {
		junit.framework.Assert.fail(message);
	}

	/**
	 * Verifica que el valor pasado sea igual a false La F mayuscula es necesaria ya que false es
	 * palabra reservada
	 * 
	 * @param tested
	 *            Valor a comprobar
	 */
	public static void False(boolean tested) {
		junit.framework.Assert.assertFalse(tested);
	}

	/**
	 * Verifica que el valor pasado no sea nulo
	 * 
	 * @param value
	 *            Valor a comprobar
	 */
	public static void notNull(Object value) {
		junit.framework.Assert.assertNotNull(value);
	}

	/**
	 * Verifica que el valor pasado sea null. La N mayuscula es necesaria ya que null es palabra
	 * reservada
	 * 
	 * @param value
	 *            Valor a comprobar
	 */
	public static void Null(Object value) {
		junit.framework.Assert.assertNull(value);
	}

	/**
	 * Verifica que las instancias pasadas sean el mismo objeto
	 * 
	 * @param expected
	 * @param tested
	 */
	public static void same(Object expected, Object tested) {
		junit.framework.Assert.assertSame(expected, tested);
	}

	/**
	 * Verifica que el valor pasado sea true La T mayuscula es necesaria ya que true es palabra
	 * reservada
	 * 
	 * @param tested
	 *            Valor a comprobar
	 */
	public static void True(boolean tested) {
		junit.framework.Assert.assertTrue(tested);
	}

	/**
	 * Verifica que el valor pasado sea true, mostrando un mensaje en caso contrario
	 * 
	 * @param descripcion
	 *            Descripcion a mostrar
	 * @param value
	 *            Valor a comprobar
	 */
	public static void True(String descripcion, boolean value) {
		junit.framework.Assert.assertTrue(descripcion, value);
	}
}
