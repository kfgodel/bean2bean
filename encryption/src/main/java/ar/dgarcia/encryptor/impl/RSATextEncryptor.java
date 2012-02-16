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

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.dgarcia.encryptor.api.CryptoKey;
import ar.dgarcia.encryptor.api.FailedCryptException;
import ar.dgarcia.encryptor.api.GeneratedKeys;
import ar.dgarcia.encryptor.api.TextEncryptor;
import ar.dgarcia.encryptor.impl.blocks.RsaBlockComposer;
import ar.dgarcia.encryptor.impl.blocks.RsaBlockDecomposer;

/**
 * Esta clase representa el encriptador de textos usando RSA para las claves publicas y privadas
 * 
 * @author D. García
 */
public class RSATextEncryptor implements TextEncryptor {
	/**
	 * Tamaño del bloque encriptado en bytes
	 */
	private static final int ENCRYPTED_BLOCK_SIZE = 256;
	/**
	 * Cantidad de bytes usables del bloque
	 */
	private static final int MAX_UNENCRYPTED_BLOCK_SIZE = ENCRYPTED_BLOCK_SIZE - 11;

	/**
	 * Caracter usado para separar los valores al serializar
	 */
	private static final String NUMBER_SEPARATOR = "|";

	/**
	 * Constante que identifica el algoritmo utilizado
	 */
	private static final String RSA_ALGORITHM = "RSA";

	private static final Logger LOG = LoggerFactory.getLogger(RSATextEncryptor.class);

	private KeyPairGenerator keyGenerator;

	private KeyFactory keyFactory;

	private ByteArrayRepresenter representer;

	public static RSATextEncryptor create() {
		LOG.debug("Creando encriptador RSA de {} bytes", ENCRYPTED_BLOCK_SIZE);
		final RSATextEncryptor encryptor = new RSATextEncryptor();
		try {
			encryptor.keyGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException("No se encontró el algoritmo RSA para encriptado", e);
		}
		final int blockSizeInBits = ENCRYPTED_BLOCK_SIZE * 8;
		encryptor.keyGenerator.initialize(blockSizeInBits);
		try {
			encryptor.keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		} catch (final NoSuchAlgorithmException e) {
			throw new RuntimeException("No se encontró el algoritmo RSA para encriptado", e);
		}
		encryptor.representer = ByteArrayRepresenter.create();
		return encryptor;
	}

	/**
	 * @see ar.dgarcia.encryptor.api.TextEncryptor#generateKeys()
	 */
	public GeneratedKeys generateKeys() {
		final KeyPair kp = keyGenerator.genKeyPair();

		// Creamos la versión propia de la clave publica
		final Key publicKey = kp.getPublic();
		RSAPublicKeySpec publicKeySpec;
		try {
			publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
		} catch (final InvalidKeySpecException e) {
			throw new RuntimeException("La keyFactory rechaza el tipo de especificacion de clave pedido", e);
		}
		final BigInteger publicModulus = publicKeySpec.getModulus();
		final BigInteger publicExponent = publicKeySpec.getPublicExponent();
		final CryptoKey encriptionKey = RsaCryptoKey.create(publicKey, publicModulus, publicExponent, true);

		// Creamos la versión propia de la clave privada
		final Key privateKey = kp.getPrivate();
		RSAPrivateKeySpec privateKeySpec;
		try {
			privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec.class);
		} catch (final InvalidKeySpecException e) {
			throw new RuntimeException("La keyFactory rechaza el tipo de especificacion de clave pedido", e);
		}
		final BigInteger privateModulus = privateKeySpec.getModulus();
		final BigInteger privateExponent = privateKeySpec.getPrivateExponent();
		final CryptoKey decriptionKey = RsaCryptoKey.create(privateKey, privateModulus, privateExponent, false);

		// Devolvemos las claves generadas
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
		final Cipher cipher = createCipher(cipherMode, rsaKey);
		int inputBlockSize;
		int ouputBlockSize;

		if (cipherMode == Cipher.ENCRYPT_MODE) {
			// El input debe ser partido en bloques de datos
			inputBlockSize = MAX_UNENCRYPTED_BLOCK_SIZE;
			ouputBlockSize = ENCRYPTED_BLOCK_SIZE;
		} else {
			// El input debe ser partido en bloques encriptados
			inputBlockSize = ENCRYPTED_BLOCK_SIZE;
			ouputBlockSize = MAX_UNENCRYPTED_BLOCK_SIZE;
		}
		final RsaBlockDecomposer arrayDecomposer = RsaBlockDecomposer.create(inputData, inputBlockSize);
		final int expectedBlocks = arrayDecomposer.getTotalBlocks();
		final RsaBlockComposer arrayComposer = RsaBlockComposer.create(expectedBlocks, ouputBlockSize);
		for (int i = 0; i < expectedBlocks; i++) {
			final byte[] inputBlock = arrayDecomposer.getNextBlock();
			final byte[] outputBlock = doCryptProcess(inputBlock, cipher);
			arrayComposer.putNextBlock(outputBlock);
		}
		final byte[] result = arrayComposer.getResult();
		return result;
	}

	/**
	 * Realiza el encriptado o desencriptado según el cipher pasado
	 * 
	 * @param inputData
	 *            Los datos de entrada
	 * @param cipher
	 *            El cipher a utilizar
	 * @return El array con los resultados
	 */
	private byte[] doCryptProcess(final byte[] inputData, final Cipher cipher) {
		byte[] outputData;
		try {
			outputData = cipher.doFinal(inputData);
		} catch (final IllegalBlockSizeException e) {
			throw new FailedCryptException(
					"El tamaño de bloque no es correcto para RSA. Probablemente el texto pasado no está encriptado", e);
		} catch (final BadPaddingException e) {
			throw new FailedCryptException(
					"No coinciden los espacios con padding. Probablemente la clave es incorrecta", e);
		}
		return outputData;
	}

	/**
	 * Crea un cipher para procesar los bytes en encriptado o desencriptado
	 * 
	 * @param cipherMode
	 *            El modo de operación del cipher
	 * @param rsaKey
	 *            La clave que debe utilizar
	 * @return El cipher creado
	 */
	private Cipher createCipher(final int cipherMode, final RsaCryptoKey rsaKey) {
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
			throw new RuntimeException("La clave usada no es aceptada por RSA", e);
		}
		return cipher;
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

	/**
	 * @see ar.dgarcia.encryptor.api.TextEncryptor#serialize(ar.dgarcia.encryptor.api.CryptoKey)
	 */
	public String serialize(final CryptoKey key) {
		if (!(key instanceof RsaCryptoKey)) {
			throw new IllegalArgumentException("Este encriptor sólo admite claves RSA");
		}
		final RsaCryptoKey rsaKey = (RsaCryptoKey) key;
		final StringBuilder builder = new StringBuilder();
		builder.append(rsaKey.isPublicKey());
		builder.append(NUMBER_SEPARATOR);
		builder.append(rsaKey.getModulus());
		builder.append(NUMBER_SEPARATOR);
		builder.append(rsaKey.getExponent());
		return builder.toString();
	}

	/**
	 * @see ar.dgarcia.encryptor.api.TextEncryptor#deserialize(java.lang.String)
	 */
	public CryptoKey deserialize(final String serializedEncriptionKey) {
		final String[] parts = serializedEncriptionKey.split("\\|");
		if (parts.length != 3) {
			throw new IllegalArgumentException("La cadena pasada no puede ser interpretada como clave serializada");
		}
		final boolean isPublic = Boolean.valueOf(parts[0]);
		final BigInteger keyModulus = new BigInteger(parts[1]);
		final BigInteger keyExponent = new BigInteger(parts[2]);

		final Key generatedKey;
		try {
			if (isPublic) {
				final RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(keyModulus, keyExponent);
				generatedKey = keyFactory.generatePublic(rsaPublicKeySpec);
			} else {
				final RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(keyModulus, keyExponent);
				generatedKey = keyFactory.generatePrivate(rsaPrivateKeySpec);
			}
		} catch (final InvalidKeySpecException e) {
			throw new RuntimeException("La keyFactory rechaza el tipo de especificacion de clave generada", e);
		}
		final CryptoKey rsaKey = RsaCryptoKey.create(generatedKey, keyModulus, keyExponent, isPublic);
		return rsaKey;
	}
}
