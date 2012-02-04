/**
 * 25/06/2011 01:52:27 Copyright (C) 2011 Darío L. García
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
package ar.dgarcia.http.simple.api;

/**
 * Esta interfaz representa el punto de salida a internet a través de requests HTTP.<br>
 * Las implementaciones de esta interfaz dependerán de la plataforma donde se ejecute pero deben
 * encargarse de hacer llegar los requests desde el código cliente al servidor y devolver su
 * respuesta en forma síncrona a la invocación de este método
 * 
 * @author D. García
 */
public interface HttpResponseProvider {

	/**
	 * Envía el request indicado, con el método HTTP que corresponda y devuelve la respuesta del
	 * servidor o un error si se produjo un error de comunicación
	 * 
	 * @param request
	 *            El request enviado
	 * @return La respuesta recibida
	 * @throws ConnectionProblemException
	 *             Si se produce un error de conectividad que impide la comunicación
	 */
	public StringResponse sendRequest(StringRequest request) throws ConnectionProblemException;

}
