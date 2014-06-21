/**
 * Created on: 01/04/2011 15:35:38 by: Dario L. Garcia
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
package net.sf.kfgodel.dgarcia.lang.reflection.attributes;

import java.lang.reflect.Type;

import net.sf.kfgodel.bean2bean.exceptions.AttributeException;

/**
 * This class represents a mixed attribute where only getter or setter is available, while the other
 * operation has to be made using direct access to the field.<br>
 * This attribute is a compound of the other 2 attribute types
 * 
 * @author D. Garcia
 */
public class MixedAttribute implements Attribute {

	private Attribute getterAttribute;
	private Attribute setterAttribute;

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#getAssignableType()
	 */
	public Type getAssignableType() throws AttributeException {
		return setterAttribute.getAssignableType();
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#getReturnedType()
	 */
	public Type getReturnedType() throws AttributeException {
		return getterAttribute.getReturnedType();
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#setValueOn(java.lang.Object,
	 *      java.lang.Object)
	 */
	public void setValueOn(final Object destination, final Object value) throws AttributeException {
		setterAttribute.setValueOn(destination, value);
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#getValueFrom(java.lang.Object)
	 */
	public Object getValueFrom(final Object destination) throws AttributeException {
		return getterAttribute.getValueFrom(destination);
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#isApplicableOn(java.lang.Object)
	 */
	public boolean isApplicableOn(final Object currentObject) {
		return getterAttribute.isApplicableOn(currentObject) && setterAttribute.isApplicableOn(currentObject);
	}

	/**
	 * Creates a mixed attributes that uses two distinct access strategy, one for setter, and other
	 * for getter. One is direct field access, the other is using a property method accessor.
	 * 
	 * @param getterAttribute
	 *            Attribute to use for getting values
	 * @param setterAttribute
	 *            Attribute to use for setting values
	 * @return Created mixed attribute to access objects value
	 */
	public static MixedAttribute create(final Attribute getterAttribute, final Attribute setterAttribute) {
		if (getterAttribute == null) {
			throw new IllegalArgumentException("Getter attribute cannot be null in mixed attribute");
		}
		if (setterAttribute == null) {
			throw new IllegalArgumentException("Setter attribute cannot be null in mixed attribute");
		}
		final MixedAttribute attribute = new MixedAttribute();
		attribute.getterAttribute = getterAttribute;
		attribute.setterAttribute = setterAttribute;
		return attribute;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append("[ getter:");
		builder.append(getterAttribute);
		builder.append(", setter:");
		builder.append(setterAttribute);
		builder.append("]");
		return builder.toString();
	}
}
