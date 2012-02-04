/**
 * 25/06/2011 20:36:55 Copyright (C) 2011 Darío García
 */
package ar.dgarcia.http.simple.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Esta clase reune algunas constantes de valores definidos en el protocolo HTTP
 * 
 * @author D. García
 */
public class HttpProtocol {

	/**
	 * Caractér utilizado para indicar el inicio de los parámetros adicionales en la URL
	 */
	public static final String PARAMS_START_DELIMITER = "?";
	/**
	 * Caractér utilizado para delimitar las ocurrencias
	 */
	public static final String PARAM_DELIMITER = "&";
	/**
	 * Caracter utilizado para definir el valor de un parámetro
	 */
	public static final String PARAM_VALUE_DELIMITER = "=";

	/**
	 * Código de status usado para indicar que el request fue procesado correctamente
	 */
	public static final int HTTP_OK_STATUS = 200;
	/**
	 * Código de status usado para indicar que las credenciales del usuario son rechazadas
	 */
	public static final int HTTP_FORBIDEN_STATUS = 403;

	/**
	 * Modifica el string pasado para que sea compatible con el formato de caracteres de HTTP. Para
	 * el encoding se utiliza UTF-8
	 * 
	 * @param text
	 *            El texto a normalizar
	 * @return El texto expresado con caracteres de escape donde es necesario
	 */
	public static String normalize(final String text) {
		String encoded;
		try {
			encoded = URLEncoder.encode(text, "UTF-8");
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException("El encoding 'UTF-8' fue rechazado al generar una url");
		}
		return encoded;
	}
}
