/**
 * 15/02/2012 23:28:52 Copyright (C) 2011 Darío L. García
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
package ar.dgarcia.encryptor.impl.blocks;

import java.io.ByteArrayInputStream;

import com.google.common.base.Objects;

/**
 * Esta interfaz representa un fragmentador de arrays de bytes que permite recorrerlo en bloques de
 * tamaño definido
 * 
 * @author D. García
 */
public class RsaBlockDecomposer {

	private int blockSize;
	private ByteArrayInputStream inputStream;
	private int totalBlocks;

	public int getTotalBlocks() {
		return totalBlocks;
	}

	/**
	 * Devuelve el próximo bloque disponible de bytes para ser leído.<br>
	 * El último array de bytes probablemente sea de menor tamaño si la cantidad de bytes es menor
	 * al tamaño de bloque
	 * 
	 * @return El próximo bloque de bytes disponible o null si no hay más
	 */
	public byte[] getNextBlock() {
		int readSize = blockSize;

		final int availables = inputStream.available();
		if (availables == 0) {
			return null;
		}
		if (availables < blockSize) {
			readSize = availables;
		}
		final byte[] readbytes = new byte[readSize];
		inputStream.read(readbytes, 0, readSize);
		return readbytes;
	}

	public static RsaBlockDecomposer create(final byte[] originalArray, final int blockSize) {
		final RsaBlockDecomposer decomposer = new RsaBlockDecomposer();
		decomposer.inputStream = new ByteArrayInputStream(originalArray);
		decomposer.blockSize = blockSize;
		decomposer.totalBlocks = (int) Math.ceil(originalArray.length / (double) blockSize);
		return decomposer;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("availables", inputStream.available()).toString();
	}
}
