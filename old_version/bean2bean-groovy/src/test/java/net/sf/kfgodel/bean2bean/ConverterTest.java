/**
 * Created on 24/12/2007 18:45:28 Copyright (C) 2007 Dario L. Garcia
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

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.kfgodel.bean2bean.conversion.DefaultTypeConverter;
import net.sf.kfgodel.bean2bean.exceptions.CannotConvertException;
import net.sf.kfgodel.bean2bean.testbeans.BeanTester;
import net.sf.kfgodel.bean2bean.testbeans.ConverterBeanTemplate;
import net.sf.kfgodel.bean2bean.testbeans.PlainPrimitiveBean;
import net.sf.kfgodel.bean2bean.testbeans.SourceBean;
import net.sf.kfgodel.bean2bean.testbeans.TipoEnumerado;
import net.sf.kfgodel.bean2bean.testbeans.TransformedPrimitiveBean;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;
import net.sf.kfgodel.dgarcia.testing.Assert;
import net.sf.kfgodel.dgarcia.testing.CodeThatShouldFail;

import org.junit.Ignore;
import org.junit.Test;


/**
 * Esta clase testea la conversion de objetos
 * 
 * @version 1.0
 * @since 24/12/2007
 * @author D. Garcia
 */
public class ConverterTest {
	/**
	 * Verifica la conversion esperada
	 * 
	 * @param sourceValue
	 *            Valor a convertir
	 * @param expectedType
	 *            Tipo de la conversion
	 * @param expectedValue
	 *            Valor esperado de la conversion
	 */
	private void checkConversion(Object sourceValue, Type expectedType, Object expectedValue) {
		Object converted = DefaultTypeConverter.getInstance().convertValue(sourceValue, expectedType);
		Assert.equals(expectedValue, converted);
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
	 * Devuelve el tipo correspondiente al atributo de la clase template
	 * 
	 * @param nombreAtributo
	 *            nombre con el que se identifica el atributo
	 * @return EL tipo generico del atributo
	 */
	private Type getTypeForFieldOfTemplate(String nombreAtributo) {
		try {
			Field declaredField = ConverterBeanTemplate.class.getDeclaredField(nombreAtributo);
			return declaredField.getGenericType();
		}
		catch (SecurityException e) {
			throw new RuntimeException(e);
		}
		catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testArray2ArrayConversion() {
		Integer[] integers = new Integer[] { 1, -1, 0, 8 };
		Integer[] converted = DefaultTypeConverter.getInstance().convertValueToClass(Integer[].class, integers);
		Assert.same(integers, converted);

		Object[] objetos = DefaultTypeConverter.getInstance().convertValueToClass(Object[].class, integers);
		Assert.same(integers, objetos);

		int[] esperados = new int[] { 1, -1, 0, 8 };
		checkConversion(integers, int[].class, esperados);

		String[] cadenas = new String[] { "1", "-1", "0", "8" };
		checkConversion(integers, String[].class, cadenas);

		objetos = new Object[] { createTransformedBean() };
		cadenas = new String[] { "{\"formateado\":null,\"jsonString\":\"4\",\"objetoDouble\":1.0,\"objetoFloat\":4.0,\"objetoInteger\":3,\"objetoString\":\"4\",\"primitivoDouble\":5.0,\"primitivoFloat\":6.0,\"primitivoInteger\":7}" };
		checkConversion(objetos, String[].class, cadenas);

		int[][] arrayDeArraysDeEnteros = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
		String[] arrayDeStrings = new String[] { "[1,2,3]", "[4,5,6]" };
		checkConversion(arrayDeArraysDeEnteros, String[].class, arrayDeStrings);
	}

	@Test
	public void testArray2ListConversion() {
		Integer[] integers = new Integer[] { 1, -1, 0, 8 };
		List<Integer> collection = Arrays.asList(integers);
		checkConversion(integers, List.class, collection);
	}

	@Test
	public void testArray2StringConversion() {
		checkConversion(new Integer[] { 2, 4, 5 }, String.class, "[2,4,5]");
		checkConversion(new Number[] { 1, 3.14d, Long.MAX_VALUE }, String.class, "[1,3.14,9223372036854775807]");
		checkConversion(new String[] { "a", "4", "b" }, String.class, "[\"a\",\"4\",\"b\"]");
	}

	@Test
	public void testBadInteger2EnumConversion() {
		CodeThatShouldFail codigo = new CodeThatShouldFail() {
			public void doTheFaultyThing() {
				checkConversion(new Integer(-1), TipoEnumerado.class, null);
			}
		};
		Assert.exceptionOn(codigo, CannotConvertException.class);
	}

	@Test
	public void testBadString2ArrayConversion() {
		CodeThatShouldFail code = new CodeThatShouldFail() {
			public void doTheFaultyThing() {
				checkConversion("[1,3.14,9223372036854775807]", Double[].class,
						new Number[] { 1, 3.14d, Long.MAX_VALUE });
			}
		};
		Assert.exceptionOn(code, CannotConvertException.class);
	}

	@Test
	public void testBadString2EnumConversion() {
		CodeThatShouldFail codigo = new CodeThatShouldFail() {
			public void doTheFaultyThing() {
				checkConversion("Lala", TipoEnumerado.class, null);
			}
		};
		Assert.exceptionOn(codigo, CannotConvertException.class);
	}

	@Test
	public void testBadString2IntegerConversion() {
		CodeThatShouldFail code = new CodeThatShouldFail() {
			public void doTheFaultyThing() {
				checkConversion("3.14", Integer.class, 3);
			}
		};
		Assert.exceptionOn(code, CannotConvertException.class);
	}

	@Test
	public void testDouble2IntegerConversion() {
		checkConversion(2d, Integer.class, 2);
	}

	@Test
	public void testEnum2IntegerConversion() {
		checkConversion(TipoEnumerado.NORTE, Integer.class, new Integer(0));
	}

	@Test
	public void testEnum2StringConversion() {
		checkConversion(TipoEnumerado.NORTE, String.class, "NORTE");
	}

	@Test
	public void testInteger2DoubleConversion() {
		checkConversion(2, Double.class, 2d);
	}

	@Test
	public void testInteger2EnumConversion() {
		checkConversion(new Integer(0), TipoEnumerado.class, TipoEnumerado.NORTE);
	}

	@Test
	public void testInteger2IntegerConversion() {
		Integer entero = new Integer(267);
		Object converted = DefaultTypeConverter.getInstance().convertValue(entero, Integer.class);
		Assert.same(entero, converted);
	}

	@Test
	public void testInteger2StringConversion() {
		checkConversion(123412, String.class, "123412");
	}

	@Test
	public void testList2ArrayConversion() {
		ArrayList<String> listaStrings = new ArrayList<String>();
		listaStrings.add("1");
		listaStrings.add("2");
		listaStrings.add("3");
		listaStrings.add("4");
		String[] arrayStrings = new String[] { "1", "2", "3", "4" };
		checkConversion(listaStrings, String[].class, arrayStrings);
		int[] arrayInts = new int[] { 1, 2, 3, 4 };
		checkConversion(listaStrings, int[].class, arrayInts);
	}

	@Test
	public void testList2ListConversion() {
		ArrayList<String> listaStrings = new ArrayList<String>();
		listaStrings.add("1");
		listaStrings.add("22");
		listaStrings.add("3");
		listaStrings.add("922337203");
		Type expectedType = ReflectionUtils.getParametricType(List.class, String.class);
		Object converted = DefaultTypeConverter.getInstance().convertValue(listaStrings, expectedType);
		Assert.same(listaStrings, converted);

		expectedType = ReflectionUtils.getParametricType(List.class, Object.class);
		converted = DefaultTypeConverter.getInstance().convertValue(listaStrings, expectedType);
		Assert.same(listaStrings, converted);

		LinkedList<Integer> listaEnteros = new LinkedList<Integer>();
		listaEnteros.add(1);
		listaEnteros.add(22);
		listaEnteros.add(3);
		listaEnteros.add(922337203);
		expectedType = ReflectionUtils.getParametricType(List.class, Integer.class);
		checkConversion(listaStrings, expectedType, listaEnteros);

		expectedType = getTypeForFieldOfTemplate("listaTipoVariable");
		checkConversion(listaStrings, expectedType, listaEnteros);
	}

	@Test
	public void testList2SetConversion() {
		ArrayList<String> listaStrings = new ArrayList<String>();
		listaStrings.add("A");
		listaStrings.add("B");
		listaStrings.add("B");
		listaStrings.add("A");
		HashSet<String> setEsperado = new HashSet<String>(listaStrings);
		checkConversion(listaStrings, Set.class, setEsperado);

		Type expectedType = getTypeForFieldOfTemplate("setAny");
		checkConversion(listaStrings, expectedType, setEsperado);
	}

	@Test
	public void testMap2StringConversion() {
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		map.put("1", 1);
		map.put("3", 3);
		map.put("8", 9);
		checkConversion(map, String.class, "{\"1\":1,\"3\":3,\"8\":9}");
	}

	@Test
	public void testNullityConversion() {
		checkConversion(null, String.class, null);
		checkConversion(null, Integer.class, null);
		checkConversion(null, TipoEnumerado.class, null);
		checkConversion(null, Object.class, null);
		checkConversion(null, Map.class, null);
	}

	@Ignore("Depends on OGNL")
	@Test
	public void testPlain2SourceConversion() {
		BeanTester<SourceBean> transformedBean = createTransformedBean();
		SourceBean converted = DefaultTypeConverter.getInstance()
				.convertValueToClass(SourceBean.class, transformedBean);
		transformedBean.compareWithDestinationBean(converted);
	}

	@Test
	public void testSet2ListConversion() {
		HashSet<String> set = new HashSet<String>();
		set.add("A");
		set.add("B");
		set.add("B");
		set.add("A");
		ArrayList<String> lista = new ArrayList<String>(set);
		checkConversion(set, List.class, lista);
	}

	@Test
	public void testSource2PlainConversion() {
		SourceBean sourceBean = createSourceBean();
		PlainPrimitiveBean converted = DefaultTypeConverter.getInstance().convertValueToClass(PlainPrimitiveBean.class,
				sourceBean);
		converted.compareWithDestinationBean(sourceBean);
	}

	@Test
	public void testString2ArrayConversion() {
		checkConversion("[1, 4, 6]", Integer[].class, new Integer[] { 1, 4, 6 });
		checkConversion("[\"a\",\"4\",\"b\"]", String[].class, new String[] { "a", "4", "b" });
	}

	@Test
	public void testString2EnumConversion() {
		checkConversion("NORTE", TipoEnumerado.class, TipoEnumerado.NORTE);
	}

	@Test
	public void testString2IntegerConversion() {
		checkConversion("23421", Integer.class, 23421);
		checkConversion("23421", Number.class, 23421);
	}

	@Test
	public void testString2MapConversion() {
		LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
		map.put("1", 1);
		map.put("3", 3);
		map.put("8", 9);
		Type expectedType = ReflectionUtils.getParametricType(LinkedHashMap.class, String.class, Integer.class);
		checkConversion("{\"1\":1,\"3\":3,\"8\":9}", expectedType, map);
	}

	@Test
	public void testString2StringConversion() {
		String cadena = "c:\\c";
		checkConversion(cadena, String.class, cadena);
	}

	@Test
	public void testDouble2StringConversion() {
		checkConversion(3d, String.class, "3.0");
	}
}
