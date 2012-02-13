/**
 * 12/02/2012 22:58:53 Copyright (C) 2011 Darío L. García
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
package ar.dgarcia.encryptor.api;

import com.google.common.base.Objects;

/**
 * Esta clase es sólo un contenedor de las claves generadas
 * 
 * @author D. García
 */
public class GeneratedKeys {

	private CryptoKey encriptionKey;
	public static final String encriptionKey_FIELD = "encriptionKey";
	private CryptoKey decriptionKey;
	public static final String decriptionKey_FIELD = "decriptionKey";

	public CryptoKey getEncriptionKey() {
		return encriptionKey;
	}

	public void setEncriptionKey(final CryptoKey encriptionKey) {
		this.encriptionKey = encriptionKey;
	}

	public CryptoKey getDecriptionKey() {
		return decriptionKey;
	}

	public void setDecriptionKey(final CryptoKey decriptionKey) {
		this.decriptionKey = decriptionKey;
	}

	public static GeneratedKeys create(final CryptoKey encriptionKey, final CryptoKey decriptionKey) {
		final GeneratedKeys generated = new GeneratedKeys();
		generated.encriptionKey = encriptionKey;
		generated.decriptionKey = decriptionKey;
		return generated;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add(encriptionKey_FIELD, encriptionKey)
				.add(decriptionKey_FIELD, decriptionKey).toString();
	}
}
