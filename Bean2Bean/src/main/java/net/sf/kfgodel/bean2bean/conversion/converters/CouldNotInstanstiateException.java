/**
 * Created on: 08/08/2008 16:15:05 by: Dario L. Garcia
 * 
 * <a rel="license" href="http://creativecommons.org/licenses/by/2.5/ar/"><img
 * alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/by/2.5/ar/88x31.png" /></a><br />
 * <span xmlns:dc="http://purl.org/dc/elements/1.1/"
 * href="http://purl.org/dc/dcmitype/InteractiveResource" property="dc:title"
 * rel="dc:type">Bean2Bean</span> by <a xmlns:cc="http://creativecommons.org/ns#"
 * href="https://sourceforge.net/projects/bean2bean/" property="cc:attributionName"
 * rel="cc:attributionURL">Dar&#237;o Garc&#237;a</a> is licensed under a <a rel="license"
 * href="http://creativecommons.org/licenses/by/2.5/ar/">Creative Commons Attribution 2.5 Argentina
 * License</a>.<br />
 * Based on a work at <a xmlns:dc="http://purl.org/dc/elements/1.1/"
 * href="https://bean2bean.svn.sourceforge.net/svnroot/bean2bean"
 * rel="dc:source">bean2bean.svn.sourceforge.net</a>
 */
package net.sf.kfgodel.bean2bean.conversion.converters;

/**
 * Esta excepcion indica un error en la instanciacion que impidio la ejecucion
 * 
 * @author Dario Garcia
 */
public class CouldNotInstanstiateException extends Exception {
	private static final long serialVersionUID = 5823458605810456890L;

	/**
	 * Crea la excepcion a partir del mensaje de error original y la excepcion producida
	 * 
	 * @param errorMessage
	 * @param error
	 */
	public CouldNotInstanstiateException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}
}
