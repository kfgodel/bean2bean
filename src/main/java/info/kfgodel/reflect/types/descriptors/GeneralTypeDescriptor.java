package info.kfgodel.reflect.types.descriptors;

import java.lang.reflect.Type;

/**
 * This type describes {@link java.lang.reflect.TypeVariable} types or {@link java.lang.reflect.WildcardType} types
 * Date: 02/03/19 - 18:39
 */
public class GeneralTypeDescriptor extends TypeDescriptorSupport {

  private Type aType;

  @Override
  public Type getType() {
    return aType;
  }

  public static GeneralTypeDescriptor create(Type aType) {
    GeneralTypeDescriptor descriptor = new GeneralTypeDescriptor();
    descriptor.aType = aType;
    return descriptor;
  }

}
