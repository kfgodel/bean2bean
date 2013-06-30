/**
 * Created on: 21/02/2010 21:47:30 by: Dario L. Garcia
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import net.sf.kfgodel.bean2bean.exceptions.AttributeException;

/**
 * This class implements the attribute using the getter/setter method pair for a class
 * 
 * @author D. Garcia
 */
public class PropertyAttribute implements Attribute {

	private Method getter;
	private Method setter;

	/**
	 * Creates an attribute that is based on setter and getter. The setter is optional, but will
	 * raise an exception when trying to access destination type or trying to make an assigments
	 * 
	 * @param getterMethod
	 *            Method to use as getter
	 * @param setterMethod
	 *            Method to use as setter
	 * @return The created property
	 * @throws IllegalArgumentException
	 *             if getter is null
	 */
	public static PropertyAttribute create(final Method getterMethod, final Method setterMethod) {
		if (getterMethod == null && setterMethod == null) {
			throw new IllegalArgumentException("Getter and setter cannot be both null");
		}

		final PropertyAttribute attribute = new PropertyAttribute();
		attribute.getter = getterMethod;
		if (setterMethod != null && setterMethod.getGenericParameterTypes().length != 1) {
			throw new IllegalArgumentException("Setter method[" + setterMethod + "] should have only one argument");
		}
		attribute.setter = setterMethod;
		return attribute;
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#getAssignableType()
	 */
	public Type getAssignableType() {
		if (setter == null) {
			throw new AttributeException("Cannot determine assignable Type for bean property[" + getter
					+ "]. It doesn't have a setter method to infer Type");
		}
		final Type[] genericParameterTypes = setter.getGenericParameterTypes();
		return genericParameterTypes[0];
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#getReturnedType()
	 */
	public Type getReturnedType() throws AttributeException {
		if (getter == null) {
			throw new AttributeException("Cannot determine returned Type for bean property[" + setter
					+ "]. It doesn't have a getter method to infer Type");
		}
		return getter.getGenericReturnType();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append("[ getter:");
		builder.append(getter);
		builder.append(", setter:");
		builder.append(setter);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#getValueFrom(java.lang.Object)
	 */
	public Object getValueFrom(final Object destination) throws AttributeException {
		if (getter == null) {
			throw new AttributeException("Attribute[" + setter + "] doesn't have a getter method");
		}
		try {
			final Object currentValue = getter.invoke(destination);
			return currentValue;
		} catch (final IllegalArgumentException e) {
			throw new AttributeException("Cannot get value from[" + destination + "] using getter[" + getter + "]", e);
		} catch (final IllegalAccessException e) {
			throw new AttributeException("Illegal access to get value from[" + destination + "] using getter[" + getter
					+ "]", e);
		} catch (final InvocationTargetException e) {
			throw new AttributeException("Internal error while getting value from[" + destination + "] on getter["
					+ getter + "]", e);
		}
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#setValueOn(java.lang.Object,
	 *      java.lang.Object)
	 */
	public void setValueOn(final Object destination, final Object value) throws AttributeException {
		if (setter == null) {
			throw new AttributeException("Attribute[" + getter + "] doesn't have a setter method");
		}
		try {
			setter.invoke(destination, value);
		} catch (final IllegalArgumentException e) {
			throw new AttributeException("Cannot set value[" + value + "] on[" + destination + "] using setter["
					+ setter + "]", e);
		} catch (final IllegalAccessException e) {
			throw new AttributeException("Illegal access to set value on[" + destination + "] using setter[" + setter
					+ "]", e);
		} catch (final InvocationTargetException e) {
			throw new AttributeException("Internal error while setting value[" + value + "] to[" + destination
					+ "] on setter[" + setter + "]", e);
		}
	}

	/**
	 * @see net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute#isApplicableOn(java.lang.Object)
	 */
	public boolean isApplicableOn(final Object currentObject) {
		if (currentObject == null) {
			// Only static methods can be used with null
			return isApplicableAsStaticAttribute();
		}
		final Class<? extends Object> objectClass = currentObject.getClass();
		final boolean isApplicable = isApplicableFromGetterAndSetter(objectClass);
		return isApplicable;
	}

	/**
	 * Indica si este atributo es aplicable al tipo del objeto pasado desde el setter y getter de
	 * este atributo. Si el getter no es aplicable ya no se evalua el getter
	 * 
	 * @param objectClass
	 *            E ltipo a evaluar
	 * @return false si el getter no es aplicable, o si el setter existe y no es aplicable
	 */
	private boolean isApplicableFromGetterAndSetter(final Class<? extends Object> objectClass) {
		if (getter != null) {
			final Class<?> getterClass = getter.getDeclaringClass();
			final boolean isGetterApplicable = getterClass.equals(objectClass)
					|| getterClass.isAssignableFrom(objectClass);
			if (!isGetterApplicable) {
				// Cannot use getter on other instance types
				return false;
			}
		}
		// If setter doesn't exist then this is applicable. Otherwise classes should be checked
		if (setter != null) {
			final Class<?> setterClass = setter.getDeclaringClass();
			final boolean isSetterApplicable = setterClass.equals(objectClass)
					|| setterClass.isAssignableFrom(objectClass);
			if (!isSetterApplicable) {
				// Cannot use setter on other instance types
				return false;
			}
		}
		return true;
	}

	/**
	 * Indica si este atributo es aplicable como un atributo estático que no requiere instancia
	 * concreta
	 * 
	 * @return true si este atributo representa un valor estático
	 */
	private boolean isApplicableAsStaticAttribute() {
		if (getter != null) {
			final boolean isStaticGetter = Modifier.isStatic(getter.getModifiers());
			if (!isStaticGetter) {
				// Cannot use instance getter with null instance
				return false;
			}
		}
		// If setter does not exist, then is applicable. Otherwise must be static too
		if (setter != null) {
			final boolean isStaticSetter = Modifier.isStatic(setter.getModifiers());
			if (!isStaticSetter) {
				// Cannot use instance setter with null instance
				return false;
			}
			return isStaticSetter;
		}
		return true;
	}

	/**
	 * Indica si esta instancia puede acceder al valor del atributo. Podrá si tiene definido un
	 * getter
	 * 
	 * @return true si esta instancia puede obtener el valor desde un objeto
	 */
	public boolean canGetValue() {
		return this.getter != null;
	}

	/**
	 * Indica si esta instancia puede cambiar el valor del atributo. Podrá si tiene definido un
	 * setter
	 * 
	 * @return true si esta instancia puede usarse para establecer el valor del atributo en un
	 *         objeto
	 */
	public boolean canSetValue() {
		return this.setter != null;
	}

}
