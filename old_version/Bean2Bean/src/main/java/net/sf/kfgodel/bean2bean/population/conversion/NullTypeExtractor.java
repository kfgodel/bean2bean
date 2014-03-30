/**
 * Created on: 25/02/2010 10:38:16 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.population.conversion;

import java.lang.reflect.Type;

/**
 * This class is a type extractor for not know types. This extractor is used when expected type is
 * not know but it seems like the user knows what he is doing
 * 
 * @author D. Garcia
 */
public class NullTypeExtractor implements ExpectedTypeExtractor {

	/**
	 * @see net.sf.kfgodel.bean2bean.population.conversion.ExpectedTypeExtractor#extractExpectedTypeFrom(java.lang.Object)
	 */
	public Type extractExpectedTypeFrom(Object destination) {
		return null;
	}

	public static NullTypeExtractor create() {
		NullTypeExtractor extractor = new NullTypeExtractor();
		return extractor;
	}
}
