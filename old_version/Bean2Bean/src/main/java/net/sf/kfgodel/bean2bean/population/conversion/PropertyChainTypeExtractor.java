/**
 * Created on: 15/01/2009 19:32:03 by: Dario L. Garcia
 * 
 * * <a rel="license" href="http://creativecommons.org/licenses/by/2.5/ar/"><img
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

import net.sf.kfgodel.bean2bean.exceptions.AttributeException;
import net.sf.kfgodel.bean2bean.exceptions.TypeExtractionFailedException;
import net.sf.kfgodel.bean2bean.instantiation.ObjectFactory;
import net.sf.kfgodel.bean2bean.interpreters.natives.PropertyChain;

/**
 * Esta clase obtiene el tipo de dato esperado, de recorrer el path de propiedades hacia la
 * propiedad destino
 * 
 * @author D.Garcia
 * @since 15/01/2009
 */
public class PropertyChainTypeExtractor implements ExpectedTypeExtractor {

	private PropertyChain propertyChain;

	/**
	 * @see net.sf.kfgodel.bean2bean.population.conversion.ExpectedTypeExtractor#extractExpectedTypeFrom(java.lang.Object)
	 */
	public Type extractExpectedTypeFrom(final Object destination) throws TypeExtractionFailedException {
		try {
			return this.propertyChain.getAssignableTypeFrom(destination);
		} catch (final AttributeException e) {
			throw new TypeExtractionFailedException("Failed to extract expected type from property chain["
					+ this.propertyChain + "] on instance[" + destination + "]", e);
		}
	}

	public static PropertyChainTypeExtractor create(final String propertyName, final ObjectFactory objectFactory,
			final boolean canCreateMissingInstances) {
		final PropertyChainTypeExtractor extractor = new PropertyChainTypeExtractor();
		extractor.propertyChain = PropertyChain.create(propertyName, objectFactory, canCreateMissingInstances);
		return extractor;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append("[ ");
		builder.append(this.propertyChain);
		builder.append(" ]");
		return builder.toString();
	}
}
