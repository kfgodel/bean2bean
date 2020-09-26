/**
 * Created on: 21/02/2010 21:47:21 by: Dario L. Garcia
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

import net.sf.kfgodel.bean2bean.exceptions.AttributeException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

/**
 * This class represents implements an attribute using the field of a class
 * 
 * @author D. Garcia
 */
public class FieldAttribute implements Attribute {

	private Field field;

	/**
	 * @param declaredField
	 * @return
	 */
	public static FieldAttribute create(Field declaredField) {
		FieldAttribute attribute = new FieldAttribute();
		if (!declaredField.isAccessible()) {
			declaredField.setAccessible(true);
		}
		attribute.field = declaredField;
		return attribute;
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#getAssignableType()
	 */
	public Type getAssignableType() {
		return field.getGenericType();
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#getReturnedType()
	 */
	public Type getReturnedType() {
		return field.getGenericType();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append("[");
		builder.append(field);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#getValueFrom(java.lang.Object)
	 */
	public Object getValueFrom(Object destination) throws AttributeException {
		try {
			Object currentValue = field.get(destination);
			return currentValue;
		}
		catch (IllegalArgumentException e) {
			throw new AttributeException("Cannot get value from field[" + field + "] on  object[" + destination + "]",
					e);
		}
		catch (IllegalAccessException e) {
			throw new AttributeException("Illegal access getting value Cfrom field[" + field + "] on  object["
					+ destination + "]", e);
		}
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#setValueOn(java.lang.Object,
	 *      java.lang.Object)
	 */
	public void setValueOn(Object destination, Object value) throws AttributeException {
		try {
			field.set(destination, value);
		}
		catch (IllegalArgumentException e) {
			throw new AttributeException("Cannot set value[" + value + "] on field[" + field + "] for object["
					+ destination + "]", e);
		}
		catch (IllegalAccessException e) {
			throw new AttributeException("Illegal access setting value[" + value + "] on field[" + field
					+ "] for object[" + destination + "]", e);
		}
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#isApplicableOn(java.lang.Object)
	 */
	public boolean isApplicableOn(Object currentObject) {
		if (currentObject == null) {
			// Only static attributes can be used over null values
			return Modifier.isStatic(field.getModifiers());
		}
		Class<? extends Object> objectClass = currentObject.getClass();
		Class<?> fieldClass = field.getDeclaringClass();
		// It's applicable if object class is the same class or subclass
		return fieldClass.equals(objectClass) || fieldClass.isAssignableFrom(objectClass);
	}
}
