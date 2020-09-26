/**
 * Created on: 21/02/2010 21:13:15 by: Dario L. Garcia
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
package net.sf.kfgodel.bean2bean.interpreters.natives;

import net.sf.kfgodel.bean2bean.exceptions.AttributeException;
import net.sf.kfgodel.bean2bean.exceptions.FailedAssumptionException;
import net.sf.kfgodel.bean2bean.exceptions.FailedInstantiationException;
import net.sf.kfgodel.bean2bean.exceptions.MissingPropertyException;
import net.sf.kfgodel.bean2bean.instantiation.ObjectFactory;
import net.sf.kfgodel.dgarcia.java.lang.ParOrdenado;
import net.sf.kfgodel.dgarcia.lang.reflection.ReflectionUtils;
import net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute;

import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * This class represents an property chain expression to access a value or to set one
 * 
 * @author D. Garcia
 */
public class PropertyChain {

	private String[] propertyNames;
	// TODO: Usar softreference si hay problemas de memoria
	private Attribute[] cachedAttributes;

	private ObjectFactory objectFactory;

	private boolean canCreateMissingInstances;

	/**
	 * Creates a new PropertyChan object based on the expression passed
	 * 
	 * @param originalExpression
	 *            "." delimited properties
	 * @return Created property chain
	 * @throws IllegalArgumentException
	 *             If the expression used is not a valid property chain
	 */
	public static PropertyChain create(final String originalExpression, final ObjectFactory objectFactory,
			final boolean canCreateMissingInstances) {
		if (!ReflectionUtils.isPropertyChain(originalExpression)) {
			throw new IllegalArgumentException("[" + originalExpression + "] is not a property chain sequence");
		}
		final PropertyChain expression = new PropertyChain();
		expression.propertyNames = originalExpression.split("\\.");
		expression.objectFactory = objectFactory;
		expression.canCreateMissingInstances = canCreateMissingInstances;
		return expression;
	}

	/**
	 * Assigns passed value to destination object using the sequence of properties represented by
	 * this property chain
	 * 
	 * @param rootObject
	 *            Object to change
	 * @param value
	 *            Value to assign
	 * @throws MissingPropertyException
	 *             If a property is not found on passed object or a value is null in middle chain
	 * @throws AttributeException
	 *             If there's a security restriction or invalid instance sequence
	 */
	public void setValueOn(final Object rootObject, final Object value) throws MissingPropertyException,
			AttributeException {
		if (rootObject == null) {
			throw new IllegalArgumentException("Destination object shouldn't be null");
		}
		final ParOrdenado<Object, Attribute> lastChainLink = getLastPropertyLinkFrom(rootObject);
		final Object currentObject = lastChainLink.getPrimero();
		final Attribute attribute = lastChainLink.getSegundo();
		attribute.setValueOn(currentObject, value);
	}

	/**
	 * Searches for the last part of the property chain and returns the host object, and the
	 * attribute for the last property in the chain
	 * 
	 * @param rootObject
	 *            Object to start from
	 * @return The last property chain link
	 * @throws AttributeException
	 *             If there's a security restriction or invalid value while traversing the property
	 *             chain
	 */
	private ParOrdenado<Object, Attribute> getLastPropertyLinkFrom(final Object rootObject) throws AttributeException,
			MissingPropertyException, FailedInstantiationException {
		Object currentObject = rootObject;
		Attribute attribute = null;
		for (int i = 0; i < propertyNames.length; i++) {
			attribute = obtainAttributeForIndex(i, currentObject, rootObject);
			if (i == propertyNames.length - 1) {
				// End of travel. Last property is the attribute we are looking for!
				break;
			}
			final Object nextObject = obtainValueForIndex(i, attribute, currentObject, rootObject);
			currentObject = nextObject;
		}
		final ParOrdenado<Object, Attribute> lastChainLink = ParOrdenado.create(currentObject, attribute);
		return lastChainLink;
	}

	/**
	 * Obtiene el valor de la propiedad indicada como atributo, bajo el indice indicado
	 * 
	 * @param propertyIndex
	 *            El atributo del indice en la cadena de propiedades
	 * @param attribute
	 *            El atributo usado para acceder al valor
	 * @param currentObject
	 *            El objeto sobre el que se aplica el atributo
	 * @param rootObject
	 *            El objeto raíz de la cadena
	 * @return El valor obtenido aplicando el atributo sobre el objeto
	 */
	private Object obtainValueForIndex(final int propertyIndex, final Attribute attribute, final Object currentObject,
			final Object rootObject) throws AttributeException, MissingPropertyException, FailedInstantiationException {
		final Object existingValue = attribute.getValueFrom(currentObject);
		if (existingValue != null) {
			// Ya existe un valor para devolver
			return existingValue;
		}
		// There's a null value in the chain. Can we complete the chain it?
		if (!canCreateMissingInstances()) {
			throw new MissingPropertyException("Property[" + propertyNames[propertyIndex] + "] is null in class["
					+ currentObject.getClass() + "] from object[" + rootObject + "] while traversing property chain"
					+ Arrays.toString(propertyNames));
		}
		final Object createdValue = createMissingObjectFor(attribute, currentObject, propertyIndex, rootObject);
		return createdValue;
	}

	/**
	 * Crea la instancia faltante para el atributo indicado, sobre el objeto pasado como
	 * currentObject. Modificando el estado de ese objeto
	 * 
	 * @param attribute
	 *            El atributo cuyo valor está faltando
	 * @param currentObject
	 *            El objeto en el cual se aplicará el atributo
	 * @param propertyIndex
	 *            El indice de la propiedad
	 * @param rootObject
	 *            El objeto raiz de la cadena
	 * @return El valor creado para completar la cadena
	 */
	private Object createMissingObjectFor(final Attribute attribute, final Object currentObject,
			final int propertyIndex, final Object rootObject) throws AttributeException, FailedInstantiationException {
		if (objectFactory == null) {
			throw new FailedAssumptionException("There's no ObjectFactory on property chain"
					+ Arrays.toString(propertyNames) + "and we need one to create missing instances");
		}
		final Type nextObjectType = attribute.getAssignableType();
		Object createdValue;
		try {
			createdValue = objectFactory.instantiate(nextObjectType);
		} catch (final FailedInstantiationException e) {
			throw new FailedInstantiationException("Couldn't instantiate missing object for property["
					+ propertyNames[propertyIndex] + "] in class[" + currentObject.getClass() + "] from object["
					+ rootObject + "] while traversing property chain" + Arrays.toString(propertyNames), e);
		}
		// We complete the chain with the missing object
		attribute.setValueOn(currentObject, createdValue);
		return createdValue;
	}

	/**
	 * Indica si la estrategia de acceso a los objetos de esta cadena permite crear instancias
	 * faltantes
	 * 
	 * @return true si se pueden completar las instancias faltantes
	 */
	private boolean canCreateMissingInstances() {
		return canCreateMissingInstances;
	}

	/**
	 * Obtiene el atributo para poder acceder a los valores de la propiedad que corresponden a la
	 * posición indicada como propertyIndex
	 * 
	 * @param propertyIndex
	 *            El indice de la propiedad pedida
	 * @param currentObject
	 *            El objeto en el cual se aplicará el atributo
	 * @param rootObject
	 *            El objeto raiz de la cadena de propiedades
	 * 
	 * @return El atributo a aplicar sobre el objeto para obtener el valor de la propiedad indicada
	 *         por propertyIndex
	 */
	private Attribute obtainAttributeForIndex(final int propertyIndex, final Object currentObject,
			final Object rootObject) {
		Attribute attribute = getCachedAttributes()[propertyIndex];
		if (attribute == null || !attribute.isApplicableOn(currentObject)) {
			final String propertyName = propertyNames[propertyIndex];
			final Class<? extends Object> currentClass = currentObject.getClass();
			attribute = ReflectionUtils.lookupAttribute(propertyName, currentClass);
			getCachedAttributes()[propertyIndex] = attribute;
		}
		if (attribute == null) {
			throw new MissingPropertyException("There's no property[" + propertyNames[propertyIndex] + "] in class["
					+ currentObject.getClass() + "] from object[" + rootObject + "] while traversing the "
					+ propertyIndex + "° element of property chain" + Arrays.toString(propertyNames));
		}
		return attribute;
	}

	/**
	 * Obtains the value on las property of this chain from the passed object
	 * 
	 * @param rootObject
	 *            Object to start navigation
	 * @return The last value of the property chain
	 * @throws MissingPropertyException
	 *             If a property is not found on passed object or a value is null in middle chain
	 * @throws AttributeException
	 *             If there's a security restriction or the object instance is invalid for this
	 *             attribute
	 */
	public Object getValueFrom(final Object rootObject) throws MissingPropertyException, AttributeException {
		if (rootObject == null) {
			throw new IllegalArgumentException("Source object shouldn't be null");
		}
		final ParOrdenado<Object, Attribute> lastChainLink = getLastPropertyLinkFrom(rootObject);
		final Object currentObject = lastChainLink.getPrimero();
		final Attribute attribute = lastChainLink.getSegundo();
		final Object lastValue = attribute.getValueFrom(currentObject);
		return lastValue;
	}

	private Attribute[] getCachedAttributes() {
		if (cachedAttributes == null) {
			cachedAttributes = new Attribute[propertyNames.length];
		}
		return cachedAttributes;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());
		builder.append(Arrays.toString(this.propertyNames));
		builder.append(", factory: ");
		builder.append(this.objectFactory);
		return builder.toString();
	}

	/**
	 * Returns the assignable type to the property chain on the last property using passed object as
	 * root
	 * 
	 * @param rootObject
	 *            Object to use as the root of the property chain
	 * @throws AttributeException
	 *             If there's a security restriction or invalid instance in the chain sequence
	 */
	public Type getAssignableTypeFrom(final Object rootObject) throws AttributeException {
		final ParOrdenado<Object, Attribute> lastPropertyLink = getLastPropertyLinkFrom(rootObject);
		final Attribute lastAttribute = lastPropertyLink.getSegundo();
		final Type lastAssignableType = lastAttribute.getAssignableType();
		return lastAssignableType;
	}
}
