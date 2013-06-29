/**
 * Created on: 25/02/2010 10:58:25 by: Dario L. Garcia
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

import java.lang.reflect.Type;

import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;


/**
 * This class uses reflection and the empty (niladic) constructor to create new instances for the
 * expected ttype
 * 
 * @author D. Garcia
 */
public class EmptyConstructorObjectFactory implements ObjectFactory {

	/**
	 * @see net.sf.kfgodel.bean2bean.instantiation.ObjectFactory#instantiate(java.lang.reflect.Type)
	 */
	public <T> T instantiate(Type expectedType) {
		if (expectedType == null) {
			throw new IllegalArgumentException("Cannot create an instance for expected type[null]");
		}
		@SuppressWarnings("unchecked")
		Class<T> expectedClass = (Class<T>) ReflectionUtils.degenerify(expectedType);
		if (expectedClass == null) {
			throw new RuntimeException("Cannot determine concrete class for expected type[" + expectedClass + "]");
		}
		T created = ReflectionUtils.createInstance(expectedClass);
		return created;
	}

	public static EmptyConstructorObjectFactory create() {
		EmptyConstructorObjectFactory factory = new EmptyConstructorObjectFactory();
		return factory;
	}
}
