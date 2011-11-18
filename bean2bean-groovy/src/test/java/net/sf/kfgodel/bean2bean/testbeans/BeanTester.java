/**
 * Created on 20/12/2007 21:32:44 Copyright (C) 2007 Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.testbeans;

import net.sf.kfgodel.dgarcia.testing.Assert;

/**
 * Esta interfaz representa un objeto que puede testear el estado de otros. Generalmente el
 * implementador de esta interfaz puede verificar que su estado sea correctamente copiado al del
 * bean pasado
 * 
 * @version 1.0
 * @since 20/12/2007
 * @author D. Garcia
 * @param <E>
 *            El tipo del bean que esta clase puede testear
 */
public interface BeanTester<E> {
	/**
	 * Comprueba el estado propio contra el bean pasado, el cual deberia tener el estado
	 * transeferido
	 * 
	 * @param aBean
	 *            El bean al que se le copiaron los datos y deberia estar como indica esta clase
	 */
	public void compareWithDestinationBean(E aBean);

	/**
	 * Comprueba el estado propio comparandose con el bean pasado, desde el cual se tomaron los
	 * datos para el estado actual {@link Assert}
	 * 
	 * @param aBean
	 *            El bean que define el estado original
	 */
	public void compareWithSourceBean(E aBean);

}
