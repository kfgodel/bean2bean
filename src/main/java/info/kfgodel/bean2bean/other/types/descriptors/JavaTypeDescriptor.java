package info.kfgodel.bean2bean.other.types.descriptors;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This type defines a common protocol for the different kinds of type representation on the java language
 * so we can extract the type information regardless of the specific type.<br>
 *   This can be seen as adding the methods that are missing on {@link java.lang.reflect.Type}
 *
 * Date: 02/03/19 - 18:32
 */
public interface JavaTypeDescriptor {
  /**
   * Used for types with no arguments
   */
  Type[] NO_ARGUMENTS = new Type[0];

  /**
   * @return The type that is described
   */
  Type getType();

  /**
   * Returns the type arguments used on this type if any. An empty array is returned for types
   * that don't have type parameters, or are not parameterized with actual arguments
   * @return The type arguments for the described type
   */
  Type[] getTypeArguments();

  /**
   * Returns the type that results from stripping this type arguments.<br>
   *   This is only applicable for types that have arguments, for the rest it results on
   *   an empty optional
   * @return The raw type for this type or empty
   */
  Optional<Class> getErasuredType();

  /**
   * Returns the type arguments of this type binding the type variables with the given bindings, so
   * the end result has type variables replaced with their actual type value
   * @param typeParameterBindings The type value for each type variable on this type
   * @return The type arguments solved after replacing the variables on the bindings
   */
  Type[] getTypeArgumentsBindedWith(Map<TypeVariable, Type> typeParameterBindings);

  /**
   * Returns the types that are declared as generic supertypes of this instance.<br>
   *   Only instances of classes have generic supertypes
   * @return The generic superclass and interfaces or empty for non class types
   */
  Stream<Type> getGenericSupertypes();

  /**
   * Calculates the map of bindings required on this type to replace each type variable with the given argument.<br>
   *   The correspondence between variable and variable is done by position of the arrays (parameters and arguments).<br>
   *   For types that don't have parameters and empty map is returned
   * @param typeArguments The arguments to bind each variable to
   * @return The map of bindings between this type parameters and the given arguments
   */
  Map<TypeVariable, Type> calculateTypeVariableBindingsFor(Type[] typeArguments);

  /**
   * Creates the descriptor that best describes the given type instance
   * @param type The type to describe
   * @return The descriptor specific for that type
   */
  static JavaTypeDescriptor createFor(Type type) {
    if(type instanceof Class){
      return ClassTypeDescriptor.create((Class) type);
    }else if(type instanceof ParameterizedType){
      return ParameterizedTypeDescriptor.create((ParameterizedType)type);
    }
    return VariableOrWildcardTypeDescriptor.create(type);
  }


}