package info.kfgodel.reflect.types.binding;

import java.lang.reflect.Type;
import java.util.stream.Stream;

/**
 * This interface represents a type that has type variables bound to actual type values.<br>
 *   For non parameterized types the bound values is empty
 *
 * Date: 29/9/19 - 15:05
 */
public interface BoundType {

  /**
   * Returns the underlying type without its type arguments
   * @return The type that is bound to its arguments
   */
  Type getRawType();

  /**
   * Returns the actual type arguments used for replacement of its type variables
   * @return The array of type arguments or empty if this is not a parameterized type
   */
  Type[] getTypeArguments();

  /**
   * Returns a stream of all the direct super types (suprclass, interfaces) bound to the inferred type arguments
   * according to this type bounds<br>
   *   The type variables of this type used to parameterizes its supertypes are replaced with the actual type arguments
   * @return The supertypes of this, usually including the superclass as the first element. Empty if there are nor super types
   */
  Stream<BoundType> getDirectSupertypes();

  /**
   * Returns a stream of all the supertypes of this type, direct and indirect up until Object.
   * This stream doesn't contain duplicates, but it contains this type as the first type
   * @return The stream of all the supertypes that are included in this type with the type arguments replaced
   */
  Stream<BoundType> getUpwardHierarchy();
}
