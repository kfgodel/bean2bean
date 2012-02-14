/**
 * 12/02/2012 22:56:53 Copyright (C) 2011 Darío L. García
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

/**
 * Esta interfaz representa la API del encriptador de textos
 * 
 * @author D. García
 */
public interface TextEncryptor {

	/**
	 * Genera un par de claves con las cuales encriptar y desencriptar los textos
	 * 
	 * @return Las claves generadas para encriptar y desencriptar los textos
	 */
	GeneratedKeys generateKeys();

	/**
	 * Crea una versión encriptada del texto pasado
	 * 
	 * @param unencrypted
	 *            Texto sin encriptar
	 * @return El texto encriptado con el algoritmo de este {@link TextEncryptor} y la clave pasada
	 * @throws FailedCryptException
	 *             Si se produjo un error con la encriptación (probablemente una clave incorrecta)
	 */
	String encrypt(String unencrypted, CryptoKey encriptionKey) throws FailedCryptException;

	/**
	 * Crea una versión desencriptada del texto pasado
	 * 
	 * @param encrypted
	 *            El texto a desencriptar
	 * @param decriptionKey
	 *            La clave para utilizar en la desencripción
	 * @return El texto desencriptado utilizando el algoritmo de este {@link TextEncryptor} y la
	 *         clave pasada
	 * @throws FailedCryptException
	 *             Si se produjo un error con la encriptación (probablemente una clave incorrecta)
	 */
	String decrypt(String encrypted, CryptoKey decriptionKey) throws FailedCryptException;

	/**
	 * Crea una versión persistible de la clave pasada
	 * 
	 * @param encriptionKey
	 *            La clave a serializar como String
	 * @return El texto que representa y permite obtener la clave pasada
	 */
	String serialize(CryptoKey encriptionKey);

	/**
	 * Crea una versión deserializada de la clave pasada
	 * 
	 * @param serializedEncriptionKey
	 *            El texto que representa la clave
	 * @return La clave regenerada
	 */
	CryptoKey deserialize(String serializedEncriptionKey);

}
