/**
 * 16/02/2012 00:01:47 Copyright (C) 2011 Darío L. García
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
package ar.dgarcia.encryptor.tests;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import ar.dgarcia.encryptor.impl.blocks.RsaBlockComposer;
import ar.dgarcia.encryptor.impl.blocks.RsaBlockDecomposer;

/**
 * Esta clase testea el funcionamiento de los partidores y ensambladores de arrays
 * 
 * @author D. García
 */
public class BlocksTests {

	@Test
	public void deberiaPermitirPartirEnArraysDeUnByte() {
		final byte[] array = new byte[] { 1, 2, 3, 4, 5, 6 };
		final RsaBlockDecomposer decomposer = RsaBlockDecomposer.create(array, 1);
		final int totalBlocks = decomposer.getTotalBlocks();
		Assert.assertEquals("Deberian haber tantos bloques como elementos", array.length, totalBlocks);

		checkOneElementBlock(decomposer, (byte) 1);
		checkOneElementBlock(decomposer, (byte) 2);
		checkOneElementBlock(decomposer, (byte) 3);
		checkOneElementBlock(decomposer, (byte) 4);
		checkOneElementBlock(decomposer, (byte) 5);
		checkOneElementBlock(decomposer, (byte) 6);

		final byte[] nonBlock = decomposer.getNextBlock();
		Assert.assertNull("Después del último debería devolver null", nonBlock);
	}

	/**
	 * Verifica que el bloque siguiente sea de tamaño 1 y tenga el elemento esperado
	 */
	private void checkOneElementBlock(final RsaBlockDecomposer decomposer, final byte expectedElement) {
		final byte[] nextBlock = decomposer.getNextBlock();
		Assert.assertEquals("El bloque debería tener tamaño de un elemento", 1, nextBlock.length);
		Assert.assertEquals("El primer elemento debería ser el 1", expectedElement, nextBlock[0]);
	}

	@Test
	public void deberiaPermitirPartirEnArraysDeDosBytes() {
		final byte[] array = new byte[] { 1, 2, 3, 4 };
		final RsaBlockDecomposer decomposer = RsaBlockDecomposer.create(array, 2);
		final int totalBlocks = decomposer.getTotalBlocks();
		Assert.assertEquals("Deberian haber dos bloques como elementos", 2, totalBlocks);

		Assert.assertTrue("El primer blqoue debería ser 1 y 2",
				Arrays.equals(new byte[] { 1, 2 }, decomposer.getNextBlock()));
		Assert.assertTrue("El segundo blqoue debería ser 3 y 4",
				Arrays.equals(new byte[] { 3, 4 }, decomposer.getNextBlock()));

		final byte[] nonBlock = decomposer.getNextBlock();
		Assert.assertNull("Después del último debería devolver null", nonBlock);
	}

	@Test
	public void deberiaDevolverElUltimoBloqueDelTamañoQueQuedeDisponible() {
		final byte[] array = new byte[] { 1, 2, 3 };
		final RsaBlockDecomposer decomposer = RsaBlockDecomposer.create(array, 2);
		final int totalBlocks = decomposer.getTotalBlocks();
		Assert.assertEquals("Deberian haber dos bloques como elementos", 2, totalBlocks);

		Assert.assertTrue("El primer bloque debería ser 1 y 2",
				Arrays.equals(new byte[] { 1, 2 }, decomposer.getNextBlock()));
		Assert.assertTrue("El segundo bloque debería ser 3", Arrays.equals(new byte[] { 3 }, decomposer.getNextBlock()));

		final byte[] nonBlock = decomposer.getNextBlock();
		Assert.assertNull("Después del último debería devolver null", nonBlock);
	}

	@Test
	public void deberiaDevolverUnSoloBloqueSiElBloqueEsMuyGrande() {
		final byte[] array = new byte[] { 1, 2, 3 };
		final RsaBlockDecomposer decomposer = RsaBlockDecomposer.create(array, 4);
		final int totalBlocks = decomposer.getTotalBlocks();
		Assert.assertEquals("Deberian haber dos bloques como elementos", 1, totalBlocks);

		Assert.assertTrue("El bloque debería ser 1, 2 y 3",
				Arrays.equals(new byte[] { 1, 2, 3 }, decomposer.getNextBlock()));

		final byte[] nonBlock = decomposer.getNextBlock();
		Assert.assertNull("Después del último debería devolver null", nonBlock);
	}

	@Test
	public void deberiaPermitirComponerBloquesDeUnByte() {
		final byte[] first = new byte[] { 1 };
		final byte[] second = new byte[] { 2 };
		final byte[] third = new byte[] { 3 };

		final RsaBlockComposer composer = RsaBlockComposer.create(3, 1);
		composer.putNextBlock(first);
		composer.putNextBlock(second);
		composer.putNextBlock(third);

		Assert.assertTrue("Deberia quedar compuesto el array 1,2,3",
				Arrays.equals(new byte[] { 1, 2, 3 }, composer.getResult()));
	}

	@Test
	public void deberiaTruncarElUltimoBloqueSiEsDemasiadoGrande() {
		final byte[] first = new byte[] { 1, 2 };
		final byte[] second = new byte[] { 3, 4 };
		final byte[] third = new byte[] { 5 };
		final RsaBlockComposer composer = RsaBlockComposer.create(3, 1);
		composer.putNextBlock(first);
		composer.putNextBlock(second);
		composer.putNextBlock(third);

		Assert.assertTrue("Deberia quedar compuesto el array 1,2,3,4,5",
				Arrays.equals(new byte[] { 1, 2, 3, 4, 5 }, composer.getResult()));
	}

}
