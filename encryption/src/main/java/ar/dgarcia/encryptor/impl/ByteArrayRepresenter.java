/**
 * 13/02/2012 00:19:40 Copyright (C) 2011 Darío L. García
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
package ar.dgarcia.encryptor.impl;

/**
 * Esta clase implementa un representador de array de bytes en string y viceversa que permite
 * mantener inversión de las operaciones y a la vez utilizar el conjunto ASCII de caracteres,
 * haciendolo compatible con otras plataformas
 * 
 * @author D. García
 */
public class ByteArrayRepresenter {

	private static final char[] charFromValue = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };

	public static ByteArrayRepresenter create() {
		final ByteArrayRepresenter representer = new ByteArrayRepresenter();
		return representer;
	}

	/**
	 * Devuelve la representación como string del array pasado
	 * 
	 * @param bytes
	 *            El array a representar como string
	 * @return El string que lo representa
	 */
	public String asString(final byte[] bytes) {
		final char[] stringChars = new char[bytes.length * 2];
		int decimalValue;
		for (int j = 0; j < bytes.length; j++) {
			decimalValue = bytes[j] & 0xFF;
			stringChars[j * 2] = charFromValue[decimalValue / 16];
			stringChars[j * 2 + 1] = charFromValue[decimalValue % 16];
		}
		final String stringVersion = new String(stringChars);
		return stringVersion;
	}

	/**
	 * Devuelve la representación como array a partir del String pasado
	 * 
	 * @param encrypted
	 *            El String que representa un array de bytes
	 * @return
	 */
	public byte[] asArray(final String encrypted) {
		final int charCount = encrypted.length();
		if (charCount % 2 != 0) {
			throw new IllegalArgumentException(
					"La cadena pasada[{}] no fue generada con esta instancia por no tener cantidad par de caracteres");
		}
		final byte[] bytes = new byte[charCount / 2];
		for (int i = 0; i < bytes.length; i++) {
			final int hiNibble = valueFromChar(encrypted.charAt(i * 2));
			final int lowNibble = valueFromChar(encrypted.charAt(i * 2 + 1));
			bytes[i] = (byte) (hiNibble * 16 + lowNibble);
		}
		return bytes;
	}

	/**
	 * @param hexChar
	 * @return
	 */
	private int valueFromChar(final char hexChar) {
		switch (hexChar) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case 'A':
			return 10;
		case 'B':
			return 11;
		case 'C':
			return 12;
		case 'D':
			return 13;
		case 'E':
			return 14;
		case 'F':
			return 15;
		default:
			throw new IllegalArgumentException("El caracter '" + hexChar
					+ "' de la cadena no es valido como representacion un byte");
		}
	}

}
