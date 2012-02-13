/**
 * 12/02/2012 22:57:31 Copyright (C) 2011 Darío L. García
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

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.dgarcia.encryptor.api.CryptoKey;
import ar.dgarcia.encryptor.api.GeneratedKeys;
import ar.dgarcia.encryptor.api.TextEncryptor;

/**
 * Esta clase representa el encriptador de textos usando RSA para las claves publicas y privadas
 * 
 * @author D. García
 */
public class RSATextEncryptor implements TextEncryptor {
	/**
	 * Constante que identifica el algoritmo utilizado
	 */
	private static final String RSA_ALGORITHM = "RSA";

	private static final Logger LOG = LoggerFactory.getLogger(RSATextEncryptor.class);

	private KeyPairGenerator keyGenerator;

	private ByteArrayRepresenter representer;

	public static RSATextEncryptor create() {
		LOG.debug("Creando encriptador RSA de 2048 bits");
		final RSATextEncryptor encryptor = new RSATextEncryptor();
		try {
			encryptor.keyGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException("No se encontró el algoritmo RSA para encriptado");
		}
		encryptor.keyGenerator.initialize(2048);
		encryptor.representer = ByteArrayRepresenter.create();
		return encryptor;
	}

	/**
	 * @see ar.dgarcia.encryptor.api.TextEncryptor#generateKeys()
	 */
	public GeneratedKeys generateKeys() {
		final KeyPair kp = keyGenerator.genKeyPair();

		final Key publicKey = kp.getPublic();
		final CryptoKey encriptionKey = RsaCryptoKey.create(publicKey);

		final Key privateKey = kp.getPrivate();
		final CryptoKey decriptionKey = RsaCryptoKey.create(privateKey);
		final GeneratedKeys clavesGeneradas = GeneratedKeys.create(encriptionKey, decriptionKey);
		LOG.debug("Generadas nuevas keys[{}]", clavesGeneradas);
		return clavesGeneradas;
	}

	/**
	 * @see ar.dgarcia.encryptor.api.TextEncryptor#encrypt(java.lang.String,
	 *      ar.dgarcia.encryptor.api.CryptoKey)
	 */
	public String encrypt(final String unencrypted, final CryptoKey encriptionKey) {
		if (!(encriptionKey instanceof RsaCryptoKey)) {
			throw new IllegalArgumentException("Este encriptor sólo admite claves RSA");
		}
		final RsaCryptoKey rsaKey = (RsaCryptoKey) encriptionKey;
		final byte[] unencryptedData = unencrypted.getBytes();
		final byte[] encryptedData = processWithCipher(Cipher.ENCRYPT_MODE, unencryptedData, rsaKey);
		final String encryptedString = representer.asString(encryptedData);
		LOG.debug("Encriptado de [{}] en [{}]", unencrypted, encryptedString);
		return encryptedString;
	}

	/**
	 * Procesa el texto pasado como array de bytes utilizando un cipher en el modo indicado
	 * 
	 * @param cipherMode
	 *            EL modo del cipher (encriptado/desencriptado)
	 * @param unencrypted
	 *            El texto a procesar
	 * @param rsaKey
	 *            La clave para la operación
	 * @return El resultado como array de bytes
	 */
	private byte[] processWithCipher(final int cipherMode, final byte[] inputData, final RsaCryptoKey rsaKey) {
		final Key javaKey = rsaKey.getInternalKey();
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(RSA_ALGORITHM);
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException("Java no reconoce a RSA como algoritmo de encriptado ", e);
		} catch (final NoSuchPaddingException e) {
			throw new RuntimeException("No se acepta el esquema de padding de RSA?", e);
		}
		try {
			cipher.init(cipherMode, javaKey);
		} catch (final InvalidKeyException e) {
			throw new RuntimeException("La clave generada no es aceptada por RSA", e);
		}
		byte[] outputData;
		try {
			outputData = cipher.doFinal(inputData);
		} catch (final IllegalBlockSizeException e) {
			throw new RuntimeException("RSA rechaza el tamaño de bloque. WTF?!", e);
		} catch (final BadPaddingException e) {
			throw new RuntimeException("Hay un problema con el padding", e);
		}
		return outputData;
	}

	/**
	 * @see ar.dgarcia.encryptor.api.TextEncryptor#decrypt(java.lang.String,
	 *      ar.dgarcia.encryptor.api.CryptoKey)
	 */
	public String decrypt(final String encrypted, final CryptoKey decriptionKey) {
		if (!(decriptionKey instanceof RsaCryptoKey)) {
			throw new IllegalArgumentException("Este encriptor sólo admite claves RSA");
		}
		final RsaCryptoKey rsaKey = (RsaCryptoKey) decriptionKey;
		final byte[] encryptedData = representer.asArray(encrypted);
		final byte[] decryptedData = processWithCipher(Cipher.DECRYPT_MODE, encryptedData, rsaKey);
		final String decryptedString = new String(decryptedData);
		LOG.debug("Desencriptado de [{}] en [{}]", encrypted, decryptedString);
		return decryptedString;
	}
}
