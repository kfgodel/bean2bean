/**
 * 15/02/2012 23:43:45 Copyright (C) 2011 Darío L. García
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

import java.io.ByteArrayOutputStream;

import com.google.common.base.Objects;

/**
 * Esta clase representa un ensamblador de arrays de bytes que espera una cantidad fija de bloques
 * de tamaño predeterminado
 * 
 * @author D. García
 */
public class RsaBlockComposer {

	private ByteArrayOutputStream outputStream;

	public void putNextBlock(final byte[] nextblock) {
		outputStream.write(nextblock, 0, nextblock.length);
	}

	public byte[] getResult() {
		final byte[] result = outputStream.toByteArray();
		return result;
	}

	public static RsaBlockComposer create(final int blockCount, final int blockSize) {
		final RsaBlockComposer composer = new RsaBlockComposer();
		final int expectedSize = blockCount * blockSize;
		composer.outputStream = new ByteArrayOutputStream(expectedSize);
		return composer;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("result", getResult()).toString();
	}
}
