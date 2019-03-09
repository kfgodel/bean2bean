package info.kfgodel.bean2bean.other.types.descriptors;

import java.lang.reflect.WildcardType;
import java.util.Optional;

/**
 * This class represents the descriptor for wildcard types.<br>
 *   This types are used on parameterizable types for restricting substitutions
 *
 * Date: 09/03/19 - 16:20
 */
public class WildcardTypeDescriptor extends GeneralTypeDescriptor {

  private WildcardType wildcardType;

  @Override
  public Optional<Class> getAssignableClass() {
    return TypeVariableDescriptor.calculateAssignableClassFromUpperBounds(wildcardType.getUpperBounds(), getType());
  }

  public static WildcardTypeDescriptor create(WildcardType wildcardType) {
    WildcardTypeDescriptor descriptor = new WildcardTypeDescriptor();
    descriptor.wildcardType = wildcardType;
    return descriptor;
  }

}
