/**
 * Created on: 25/02/2010 10:54:59 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.instantiation;

import net.sf.kfgodel.bean2bean.exceptions.FailedInstantiationException;

import java.lang.reflect.Type;

/**
 * This interface serves as an extension point to create instances for needed types
 * 
 * @author D. Garcia
 */
public interface ObjectFactory {

	/**
	 * Creates a new instance for expected type
	 * 
	 * @param expectedType
	 *            Type for the new instance
	 * @return Created instance, ready to use
	 * @throws FailedInstantiationException
	 *             If there's an error creating a new instance from the type
	 */
	public <T> T instantiate(Type expectedType) throws FailedInstantiationException;

}