/**
 * 22/10/2005 18:59:35
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
package net.sf.kfgodel.dgarcia.lang.reflection;

import net.sf.kfgodel.bean2bean.exceptions.AttributeException;
import net.sf.kfgodel.bean2bean.exceptions.FailedInstantiationException;
import net.sf.kfgodel.dgarcia.lang.closures.Condition;
import net.sf.kfgodel.dgarcia.lang.closures.Expression;
import net.sf.kfgodel.dgarcia.lang.iterators.basic.ConditionalIterator;
import net.sf.kfgodel.dgarcia.lang.reflection.attributes.Attribute;
import net.sf.kfgodel.dgarcia.lang.reflection.attributes.FieldAttribute;
import net.sf.kfgodel.dgarcia.lang.reflection.attributes.MixedAttribute;
import net.sf.kfgodel.dgarcia.lang.reflection.attributes.PropertyAttribute;
import net.sf.kfgodel.dgarcia.lang.reflection.iterators.ClassMemberIterator;
import net.sf.kfgodel.dgarcia.lang.reflection.iterators.SuperClassIterator;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase reune metodos utiles para trabajar sobre los objetos sus clases y otras hierbas
 * reflexivas
 * 
 * @version 1.0
 * @since 18/01/2007
 * @author D. Garcia
 */
@SuppressWarnings("restriction")
public class ReflectionUtils {

	public static final Pattern propertyChainPattern = Pattern
			.compile("^(?:([\\p{Alpha}|_|$][\\p{Alnum}|_|$]*)(?:\\.([\\p{Alpha}|_|$][\\p{Alnum}|_|$]*))*)$");

	/**
	 * Este enum permite definir el tipo de miembro sobre el que se operara
	 * 
	 * @author D. Garcia
	 */
	public enum MemberType implements Expression<Class<?>, Member[]> {
		/**
		 * Identifica los constructores de una clase
		 */
		CONSTRUCTOR {
			public Constructor<?>[] evaluateOn(final Class<?> element) {
				return element.getDeclaredConstructors();
			}
		},
		/**
		 * Identifica los atributos de una clase
		 */
		FIELD {
			public Field[] evaluateOn(final Class<?> element) {
				return element.getDeclaredFields();
			}
		},
		/**
		 * Identifica al tipo de los metodos
		 */
		METHOD {
			public Method[] evaluateOn(final Class<?> element) {
				return element.getDeclaredMethods();
			}
		};

		/**
		 * @param <T>
		 *            Tipo de la clase y constructores
		 * @return Devuelve la expresion que permite extraer los metodos de una clase
		 */
		@SuppressWarnings("unchecked")
		public static <T> Expression<Class<T>, Constructor<T>[]> getConstructorExtractor() {
			return (Expression) CONSTRUCTOR;
		}

		/**
		 * @return Devuelve la expresion que permite extraer los metodos de una clase
		 */
		@SuppressWarnings("unchecked")
		public static Expression<Class<?>, Field[]> getFieldExtractor() {
			return (Expression) FIELD;
		}

		/**
		 * @return Devuelve la expresion que permite extraer los metodos de una clase
		 */
		@SuppressWarnings("unchecked")
		public static Expression<Class<?>, Method[]> getMethodExtractor() {
			return (Expression) METHOD;
		}
	};

	/**
	 * Crea una nueva instancia de la clase pasada utilizando el constructor niladico
	 * 
	 * @param <T>
	 *            Tipo del objeto a crear
	 * @param clase
	 *            Clase de la instancia creada
	 * @return La nueva instancia
	 */
	public static <T> T createInstance(final Class<T> clase) throws FailedInstantiationException {
		try {
			final T instancia = clase.newInstance();
			return instancia;
		} catch (final InstantiationException e) {
			throw new FailedInstantiationException("An error occured while instantiating", e);
		} catch (final IllegalAccessException e) {
			throw new FailedInstantiationException("Empty constructor is not accesible from this code", e);
		}
	}

	/**
	 * Devuelve la clase que representa el tipo basico del tipo pasado sin los parametros genericos
	 * 
	 * @param genericType
	 *            Tipo generificado
	 * @return La instancia de clase que representa el tipo pasado o null si no se pudo obtener un
	 *         tipo concreto del tipo pasado
	 */
	public static Class<?> degenerify(final Type genericType) {
		if (genericType instanceof Class<?>) {
			return (Class<?>) genericType;
		}
		if (genericType instanceof ParameterizedType) {
			final ParameterizedType parameterized = (ParameterizedType) genericType;
			return (Class<?>) parameterized.getRawType();
		}
		if (genericType instanceof TypeVariable<?>) {
			final TypeVariable<?> typeVariable = (TypeVariable<?>) genericType;
			return (Class<?>) typeVariable.getBounds()[0];
		}
		if (genericType instanceof WildcardType) {
			final WildcardType wildcard = (WildcardType) genericType;
			final Type[] upperBounds = wildcard.getUpperBounds();
			if (upperBounds.length > 0) {
				return degenerify(upperBounds[0]);
			}
			final Type[] lowerBounds = wildcard.getLowerBounds();
			if (lowerBounds.length > 0) {
				return degenerify(lowerBounds[0]);
			}
		}
		return null;
	}

	/**
	 * Genera y devuelve un iterador que permite recorrer todos los constructores de la clase
	 * pasada, incluidos los heredados
	 * 
	 * @param <T>
	 *            Tipo de la clase y constructores
	 * @param clase
	 *            Clase a partir de la cual se obtendran todos los constructores
	 * @return Un iterador con todos los constructores
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<Constructor<T>> getAllConstructorsOf(final Class<T> clase) {
		final Expression<Class<T>, Constructor<T>[]> constructorExtractor = MemberType.getConstructorExtractor();
		final ClassMemberIterator<Constructor<T>> constructorIterator = ClassMemberIterator.create(clase,
				(Expression) constructorExtractor);
		return constructorIterator;
	}

	/**
	 * Genera y devuelve un iterador que permite recorrer todos los atributos de la clase pasada,
	 * incluidos los heredados
	 * 
	 * @param clase
	 *            Clase a partir de la cual se obtendran todos los atributos
	 * @return Un iterador con todos los atributos
	 */
	public static Iterator<Field> getAllFieldsOf(final Class<?> clase) {
		final Expression<Class<?>, Field[]> fieldExtractor = MemberType.getFieldExtractor();
		final ClassMemberIterator<Field> fieldIterator = ClassMemberIterator.create(clase, fieldExtractor);
		return fieldIterator;
	}

	/**
	 * Genera y devuelve un iterador que permite recorrer todos los metodos de la clase pasada,
	 * incluidos los heredados
	 * 
	 * @param clase
	 *            Clase a partir de la cual se obtendran todos los metodos
	 * @return Un iterador con todos los métodos
	 */
	public static Iterator<Method> getAllMethodsOf(final Class<?> clase) {
		final Expression<Class<?>, Method[]> methodExtractor = MemberType.getMethodExtractor();
		final ClassMemberIterator<Method> methodIterator = ClassMemberIterator.create(clase, methodExtractor);
		return methodIterator;
	}

	/**
	 * Busca en la clase pasada los constructores que cumplen la condicion dada
	 * 
	 * @param condition
	 *            Condicion que deben cumplir los constructores
	 * @param clazz
	 *            Clase de la que obtendran los constructores
	 * @return Una lista con todos los constructores (tanto privados como heredados tambien) de la
	 *         clase que cumplan la condicion pasada
	 */
	public static Iterator<Constructor<?>> getConstructorsThatMeet(final Condition<? super Constructor<?>> condition,
			final Class<?> clazz) {
		return ReflectionUtils.getMembersThatMeet(condition, MemberType.CONSTRUCTOR, clazz);
	}

	/**
	 * Devuelve el tipo que corresponde al primer parametro del tipo pasado.<br>
	 * 
	 * @param genericType
	 *            Un tipo parametrizado del que se obtendra el primer parametro
	 * @return El tipo correspondiente al primer tipo o null si no existe ninguno, el tipo no es
	 *         parametrizado
	 */
	public static Type getElementTypeParameterFrom(final Type genericType) {
		final Class<?> concreteClass = degenerify(genericType);
		if (concreteClass == null) {
			return null;
		}
		if (concreteClass.isArray()) {
			return concreteClass.getComponentType();
		}
		if (!(genericType instanceof ParameterizedType)) {
			return null;
		}
		final ParameterizedType parameterizedType = (ParameterizedType) genericType;
		final Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		if (actualTypeArguments.length < 1) {
			return null;
		}
		final Type elementType = actualTypeArguments[0];
		return elementType;

	}

	/**
	 * Busca en la clase pasada los atributos que cumplen la condicion dada
	 * 
	 * @param condition
	 *            Condicion que deben cumplir los atributos
	 * @param clazz
	 *            Clase de la que obtendran los atributos
	 * @return Una lista con todos los atributos (tanto privados como heredados tambien) de la clase
	 *         que cumplan la condicion pasada
	 */
	public static Iterator<Field> getFieldsThatMeet(final Condition<? super Field> condition, final Class<?> clazz) {
		return ReflectionUtils.getMembersThatMeet(condition, MemberType.FIELD, clazz);
	}

	/**
	 * Busca en la clase pasada los miembros que cumplen la condicion dada buscando hacia arriba en
	 * la jerarquia de clases
	 * 
	 * @param <T>
	 *            Tipo de miembro buscado
	 * @param condition
	 *            Condicion que deben cumplir los miembros
	 * @param memberType
	 *            Tipo de miembro buscado
	 * @param clazz
	 *            Clase de la que obtendran los miembros
	 * @return Una lista con todos los miembros (tanto privados como heredados tambien) de la clase
	 *         que cumplan la condicion pasada
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Member> Iterator<T> getMembersThatMeet(final Condition<? super T> condition,
			final MemberType memberType, final Class clazz) {
		final Iterator<T> memberIterator = ClassMemberIterator.create(clazz, memberType);
		final ConditionalIterator<T> matchedMembersIterator = ConditionalIterator.createFrom(condition, memberIterator);
		return matchedMembersIterator;
	}

	/**
	 * Busca en la clase pasada los metodos que cumplen la condicion dada
	 * 
	 * @param condition
	 *            Condicion que deben cumplir los metodos
	 * @param clazz
	 *            Clase de la que obtendran los metodos
	 * @return Una lista con todos los metodos (tanto privados como heredados tambien) de la clase
	 *         que cumplan la condicion pasada
	 */
	public static Iterator<Method> getMethodsThatMeet(final Condition<? super Method> condition, final Class<?> clazz) {
		return ReflectionUtils.getMembersThatMeet(condition, MemberType.METHOD, clazz);
	}

	/**
	 * Gets a parametric type instance using SUN internal classes. This classes are the same as
	 * returned by reflection code
	 * 
	 * @param rawClass
	 *            Parametrizable class
	 * @param typeParameters
	 *            Classes used as parameters for the rawClass
	 * @return A {@link Type} instance that represents the complex generic type
	 */
	public static ParameterizedType getParametricType(final Class<?> rawClass, final Type... typeParameters) {
		final ParameterizedTypeImpl parameterizedTypeImpl = ParameterizedTypeImpl.make(rawClass, typeParameters, null);
		return parameterizedTypeImpl;
	}

	/**
	 * Busca un atributo por su nombre, partiendo desde la clase pasada y subiendo a la superclase
	 * si no lo encuentra
	 * 
	 * @param atributo
	 *            Nombre del atributo buscado
	 * @param clase
	 *            Clase a partir de la que se buscara
	 * @return El primer atributo encontrado o null si no hay ninguno con ese nombre
	 */
	public static Field lookupField(final String atributo, final Class<? extends Object> clase)
			throws AttributeException {
		@SuppressWarnings("unchecked")
		final Iterator<Class<?>> classes = (Iterator) SuperClassIterator.createFrom(clase);
		while (classes.hasNext()) {
			final Class<?> currentClass = classes.next();
			Field declaredField;
			try {
				declaredField = currentClass.getDeclaredField(atributo);
				return declaredField;
			} catch (final SecurityException e) {
				throw new AttributeException("Cannot access field because of a security constraint", e);
			} catch (final NoSuchFieldException e) {
				// Se continua con el bucle
			}
		}
		return null;
	}

	/**
	 * Busca un metodo por su nombre, partiendo desde la clase pasada y subiendo a la superclase si
	 * no lo encuentra
	 * 
	 * @param methodName
	 *            Nombre del atributo buscado
	 * @param clase
	 *            Clase a partir de la que se buscará
	 * @return El primer metodo encontrado o null si no hay ninguno con ese nombre
	 */
	public static Method lookupMethod(final String methodName, final Class<? extends Object> clase) {
		final Condition<Method> hasSameName = new Condition<Method>() {
			public boolean isMetBy(final Method metodo) {
				return metodo.getName().equals(methodName);
			}
		};
		final Iterator<Method> methodsThatMeet = getMethodsThatMeet(hasSameName, clase);
		while (methodsThatMeet.hasNext()) {
			return methodsThatMeet.next();
		}
		return null;
	}

	/**
	 * Indica si el tipo indicado como fuente es asignable en el tipo destino
	 * 
	 * @param sourceType
	 *            Tipo de dato que representa el objeto a asignar
	 * @param destinationType
	 *            Tipo de dato que representa el tipo de la variable asignada
	 * @return true si la asignacion es segura
	 */
	public static boolean isAssignableFrom(final Type sourceType, final Type destinationType) {
		// TODO: Incluir casos de generics y otras yerbas
		final Class<?> destinationClass = degenerify(destinationType);
		final Class<?> sourceClass = degenerify(sourceType);
		return destinationClass.isAssignableFrom(sourceClass);
	}

	/**
	 * Busca un atributo de la clase (Field, getter/setter, o mezcla) con el nombre dado. Se le da
	 * prioridad al getter/setter sobre el field de la clase. <br>
	 * Se busca en toda la jerarquía de la clase setter, getter, y field dando prioridad a las
	 * declaraciones que están más cerca de la clase pasada
	 * 
	 * @param attributeName
	 *            Nombre de la propiedad a buscar
	 * @param rootClass
	 *            Tipo en el cual buscar el atributo
	 * @return Un Method que es getter de la propiedad con el nombre dado o un Field de la
	 *         propiedad. Se devuelve null si no existe ninguna
	 * @throws IllegalArgumentException
	 *             If destinationProperty is empty or null, o rootClass is null
	 */
	public static Attribute lookupAttribute(final String attributeName, final Class<?> rootClass) {
		if (attributeName == null || attributeName.length() < 1) {
			throw new IllegalArgumentException("Attribute name cannot be empty or null");
		}
		if (rootClass == null) {
			throw new IllegalArgumentException("Root class cannot be null");
		}
		final String camelizedName = Character.toUpperCase(attributeName.charAt(0)) + attributeName.substring(1);

		final String getterName = "get" + camelizedName;
		final Method getterMethod = lookupMethod(getterName, rootClass);

		final String setterName = "set" + camelizedName;
		final Method setterMethod = lookupMethod(setterName, rootClass);
		// Si es un bean property, cortamos acá, podemos usar getter y setter
		if (getterMethod != null && setterMethod != null) {
			// No verificamos el field ya que podemos usar la property
			return PropertyAttribute.create(getterMethod, setterMethod);
		}

		PropertyAttribute partialProperty = null;
		if (getterMethod != null || setterMethod != null) {
			// Al menos podemos usar uno de los dos
			partialProperty = PropertyAttribute.create(getterMethod, setterMethod);
		}

		final Field field = lookupField(attributeName, rootClass);
		if (field == null) {
			// En el peor de los casos podemos llegar a ofrecer un atributo parcial (sólo getter o
			// setter)
			if (partialProperty != null) {
				return partialProperty;
			}
			// No hay caso no existe atributo directo ni accessor methods parciales
			return null;
		}
		// Sabemos que puede accederse al atributo
		final FieldAttribute fieldAttribute = FieldAttribute.create(field);

		// Intentamos usar la propiedad parcial si es posible
		if (partialProperty != null && partialProperty.canGetValue()) {
			// Tiene definido getter pero no setter
			return MixedAttribute.create(partialProperty, fieldAttribute);
		}
		if (partialProperty != null && partialProperty.canSetValue()) {
			// Tiene definido getter pero no setter
			return MixedAttribute.create(fieldAttribute, partialProperty);
		}

		// No hubo caso, usamos el acceso directo al field
		return fieldAttribute;
	}

	/**
	 * Devuelve el atributo final de la cadena de propiedades separadas por puntos. El atributo es
	 * un Field o un Method de acuerdo al que se encuentre primero. Dandose prioridad al getter.
	 * 
	 * @param propertyChain
	 *            Secuencia de propiedades a buscar
	 * @param currentType
	 *            Clases por la que se comienza la busqueda
	 * @return El field o method que corresponde con la propiedad final o null si no se encontró
	 *         ninguna
	 * @throws Si
	 *             se produce un error al navegar los atributos
	 */
	public static Attribute lookupNestedAttribute(final String propertyChain, final Class<?> startingType)
			throws AttributeException {
		final String[] propertyNames = propertyChain.split("\\.");
		Attribute foundAttribute = null;
		Class<?> currentType = startingType;
		for (final String propertyName : propertyNames) {
			foundAttribute = lookupAttribute(propertyName, currentType);
			if (foundAttribute == null) {
				// No podemos seguir avanzando
				return null;
			}
			final Type genericType = foundAttribute.getReturnedType();
			currentType = degenerify(genericType);
		}
		return foundAttribute;
	}

	/**
	 * Devuelve el tipo de retorno de un getter o el tipo de la variable en un field que mantiene la
	 * info de generics. Se produce una excepcion en runtime si el atributo pasado no es un Field o
	 * Method
	 * 
	 * @param attribute
	 *            El metodo getter o el Field a evaluar
	 * @return El tipo generico del atributo
	 */
	public static Type getGenericReturnedTypeFromAttribute(final Object attribute) {
		if (attribute instanceof Field) {
			final Field destinationField = (Field) attribute;
			destinationField.setAccessible(true);
			return destinationField.getGenericType();
		} else if (attribute instanceof Method) {
			final Method destinationMethod = (Method) attribute;
			destinationMethod.setAccessible(true);
			return destinationMethod.getGenericReturnType();
		} else {
			throw new IllegalArgumentException("Attribute is not a Field nor a Method[" + attribute + "]");
		}
	}

	/**
	 * Indica si la expresion pasada como cadena representa una cadena de propiedades separadas por
	 * puntos
	 * 
	 * @param propertyChain
	 * @return Devuelve true si la cadena pasada puede ser tomada como cadena de propiedades
	 *         separadas por puntos
	 */
	public static boolean isPropertyChain(final String propertyChain) {
		if (propertyChain == null) {
			return false;
		}
		final Matcher matcher = propertyChainPattern.matcher(propertyChain);
		return matcher.matches();
	}
}
