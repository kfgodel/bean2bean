/**
 * 12/02/2012 22:53:50 Copyright (C) 2011 Darío L. García
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

import javax.crypto.BadPaddingException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import ar.dgarcia.encryptor.api.CryptoKey;
import ar.dgarcia.encryptor.api.FailedCryptException;
import ar.dgarcia.encryptor.api.GeneratedKeys;
import ar.dgarcia.encryptor.api.TextEncryptor;
import ar.dgarcia.encryptor.impl.RSATextEncryptor;

/**
 * Esta clase prueba el funcionamiento del encriptador de textos
 * 
 * @author D. García
 */
public class EncryptorAsymmetricTests {

	private TextEncryptor encryptor;

	@Before
	public void crearEncryptor() {
		encryptor = RSATextEncryptor.create();
	}

	@Test
	public void deberiaPermitirObtenerUnaClavePublicaYUnaPrivada() {
		// Generamos las claves
		final GeneratedKeys keys = encryptor.generateKeys();

		// Verificamos que estén
		Assert.assertNotNull(keys);
		Assert.assertNotNull(keys.getDecriptionKey());
		Assert.assertNotNull(keys.getEncriptionKey());
		Assert.assertNotSame(keys.getEncriptionKey(), keys.getDecriptionKey());
	}

	@Test
	public void deberiaRechazarClavesNoCompatiblesConElEncryptor() {
		final CryptoKey claveAjena = new CryptoKey() {
		};
		try {
			encryptor.encrypt("Texto cualquiera", claveAjena);
			Assert.fail("Debería fallar cuando la clave no es compatible");
		} catch (final IllegalArgumentException e) {
			// Excepción correcta
		}
		try {
			encryptor.decrypt("Texto cualquiera", claveAjena);
			Assert.fail("Debería fallar cuando la clave no es compatible");
		} catch (final IllegalArgumentException e) {
			// Excepción correcta
		}
	}

	@Test
	public void deberiaPermitirEncriptarTextoConUnaClavePublica() {
		final GeneratedKeys keys = encryptor.generateKeys();
		final CryptoKey encriptionKey = keys.getEncriptionKey();

		// Encriptamos con la clave publica
		final String textoOrginal = "Hola mundo";
		final String encrypted = encryptor.encrypt(textoOrginal, encriptionKey);

		// Verificamos que el texto sea distinto
		Assert.assertNotNull(encrypted);
		Assert.assertFalse("No debería ser el mismo texto", textoOrginal.equals(encrypted));
	}

	@Test
	public void deberiaPermitirDesencriptarTextoConUnaClavePrivada() {
		final GeneratedKeys keys = encryptor.generateKeys();
		final CryptoKey encriptionKey = keys.getEncriptionKey();

		// Encriptamos con la publica
		final String textoOrginal = "Hola mundo";
		final String encrypted = encryptor.encrypt(textoOrginal, encriptionKey);

		// Desencriptamos con la privada
		final CryptoKey decriptionKey = keys.getDecriptionKey();
		final String decrypted = encryptor.decrypt(encrypted, decriptionKey);

		// Verificamos que sea igual el texto
		Assert.assertNotNull(decrypted);
		Assert.assertEquals(textoOrginal, decrypted);
	}

	@Test
	public void noDeberiaPermitirDesencriptarConLaClavePublica() {
		final GeneratedKeys keys = encryptor.generateKeys();
		final CryptoKey encriptionKey = keys.getEncriptionKey();

		// Encriptamos con la publica
		final String textoOrginal = "Hola mundo";
		final String encrypted = encryptor.encrypt(textoOrginal, encriptionKey);

		// Desencriptamos con la pública
		try {
			encryptor.decrypt(encrypted, encriptionKey);
			Assert.fail("No debería ejecutar normalmente");
		} catch (final FailedCryptException e) {
			// Es la excepción correcta
			Assert.assertTrue("Debería indicar badpadding como causa", e.getCause() instanceof BadPaddingException);
		}
	}

	@Test
	public void noDeberiaPermitirDesencriptarConOtraClavePrivada() {
		final GeneratedKeys keys = encryptor.generateKeys();
		final CryptoKey encriptionKey = keys.getEncriptionKey();

		// Encriptamos con la publica
		final String textoOrginal = "Hola mundo";
		final String encrypted = encryptor.encrypt(textoOrginal, encriptionKey);

		// Desencriptamos con otra clave privada
		final GeneratedKeys otherKeys = encryptor.generateKeys();
		final CryptoKey otherDecriptionKey = otherKeys.getDecriptionKey();

		try {
			encryptor.decrypt(encrypted, otherDecriptionKey);
			Assert.fail("No debería ejecutar normalmente");
		} catch (final FailedCryptException e) {
			// Es la excepción correcta
			Assert.assertTrue("Debería indicar badpadding como causa", e.getCause() instanceof BadPaddingException);
		}
	}

	@Test
	public void deberiaPermitirSerializarLaClavePublica() {
		// Generamos la clave publica original
		final GeneratedKeys keys = encryptor.generateKeys();
		final CryptoKey encriptionKey = keys.getEncriptionKey();

		// A partir de la original generamos una copia
		final String serializedEncriptionKey = encryptor.serialize(encriptionKey);
		final CryptoKey copiedEncriptionKey = encryptor.deserialize(serializedEncriptionKey);

		// Verificamos que no sea la misma instancia
		Assert.assertNotSame("Deberían ser claves distintas", encriptionKey, copiedEncriptionKey);

		// Encriptamos con la publica original
		final String textoOrginal = "Hola mundo";
		final String encrypted = encryptor.encrypt(textoOrginal, encriptionKey);

		// Encriptamos con la copia
		final String encryptedWithCopy = encryptor.encrypt(textoOrginal, copiedEncriptionKey);

		// Desencriptamos con la privada en ambos casos
		final String decrypted = encryptor.decrypt(encrypted, keys.getDecriptionKey());
		final String decryptedFromCopy = encryptor.decrypt(encryptedWithCopy, keys.getDecriptionKey());
		// Verificamos que le texto desencriptado debería ser igual
		Assert.assertEquals("Los textos deberían ser iguales", decrypted, decryptedFromCopy);

	}
}
