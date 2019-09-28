package info.kfgodel.reflect.types.descriptors;

import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class represents the descriptor for wildcard types.<br>
 *   This types are used on parameterizable types for restricting substitutions
 *
 * Date: 09/03/19 - 16:20
 */
public class WildcardTypeDescriptor extends GeneralTypeDescriptor {

  private WildcardType wildcardType;

  @Override
  public Type getType() {
    return wildcardType;
  }

  @Override
  public Stream<Type> getUpperBounds() {
    return Arrays.stream(wildcardType.getUpperBounds());
  }

  @Override
  public Optional<Class> getAssignableClass() {
    return TypeVariableDescriptor.calculateAssignableClassFromUpperBounds(getUpperBounds(), getType());
  }

  public static WildcardTypeDescriptor create(WildcardType wildcardType) {
    WildcardTypeDescriptor descriptor = new WildcardTypeDescriptor();
    descriptor.wildcardType = wildcardType;
    return descriptor;
  }

}
