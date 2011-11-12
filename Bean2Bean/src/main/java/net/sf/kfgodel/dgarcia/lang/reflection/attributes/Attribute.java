/**
 * Created on: 21/02/2010 21:45:50 by: Dario L. Garcia
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

/**
 * This class represents an object attribute. A name to enclose Field or getter/setter that can be
 * used to get or set a value from an object
 * 
 * @author D. Garcia
 */
public interface Attribute {

	/**
	 * Returns the generified type that can be setted on this attribute. Can be the field type or
	 * the setter parameter type for this object attribute
	 */
	Type getAssignableType();

	/**
	 * Returns the generified type that can be returned from this attribute. Can be the field type
	 * or getter return type for this object attribute
	 */
	Type getReturnedType();

	/**
	 * Assigns the passed value to the destination object in the attribute represented by this
	 * instance
	 * 
	 * @param destination
	 *            Object whose attribute is going to change
	 * @param value
	 *            Value to assign in the property
	 */
	void setValueOn(Object destination, Object value) throws AttributeException;

	/**
	 * Gets the value for this attribute from the destination object
	 * 
	 * @param destination
	 *            Object to get the value from
	 */
	Object getValueFrom(Object destination) throws AttributeException;

	/**
	 * Indicates if this attribute can be used on the object passed
	 * 
	 * @param currentObject
	 *            Object to test this attribute on
	 * @return false is applying this attribute on the object will cause an exception
	 */
	boolean isApplicableOn(Object currentObject);

}
