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
   * Returns the suprtypes of this types bound to the inferred type arguments of this type.<br>
   *   By following the replacement of type variables on this type the supertypes are parameterized with their actual
   *   types.<br>
   * @return The supertypes of this, usually including the superclass as the first element. Empty if there are nor super types
   */
  Stream<BoundType> getSupertypes();
}
