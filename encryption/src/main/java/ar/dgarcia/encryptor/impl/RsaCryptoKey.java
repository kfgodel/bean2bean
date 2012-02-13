/**
 * 12/02/2012 23:24:33 Copyright (C) 2011 Darío L. García
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

import java.security.Key;

import ar.dgarcia.encryptor.api.CryptoKey;

import com.google.common.base.Objects;

/**
 * Esta clase representa una clave utilizada con el algoritmo RSA para encriptado
 * 
 * @author D. García
 */
public class RsaCryptoKey implements CryptoKey {

	private Key internalKey;
	public static final String internalKey_FIELD = "internalKey";

	public Key getInternalKey() {
		return internalKey;
	}

	public static RsaCryptoKey create(final Key javaKey) {
		final RsaCryptoKey key = new RsaCryptoKey();
		key.internalKey = javaKey;
		return key;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Objects.toStringHelper(this).add(internalKey_FIELD, internalKey).toString();
	}
}
