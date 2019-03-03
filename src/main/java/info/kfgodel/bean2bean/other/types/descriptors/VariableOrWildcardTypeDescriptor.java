package info.kfgodel.bean2bean.other.types.descriptors;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This type describes {@link java.lang.reflect.TypeVariable} types or {@link java.lang.reflect.WildcardType} types
 * Date: 02/03/19 - 18:39
 */
public class VariableOrWildcardTypeDescriptor implements JavaTypeDescriptor {

  private Type aType;

  public static VariableOrWildcardTypeDescriptor create(Type aType) {
    VariableOrWildcardTypeDescriptor descriptor = new VariableOrWildcardTypeDescriptor();
    descriptor.aType = aType;
    return descriptor;
  }

  @Override
  public Type getType() {
    return aType;
  }

  @Override
  public Type[] getTypeArguments() {
    return JavaTypeDescriptor.NO_ARGUMENTS;
  }

  @Override
  public Optional<Class> getErasuredType() {
    return Optional.empty();
  }

  @Override
  public Type[] getTypeArgumentsReplacingParametersWith(Map<TypeVariable, Type> typeParameterValues) {
    return NO_ARGUMENTS;
  }

  @Override
  public Stream<Type> getGenericSupertypes() {
    return Stream.empty();
  }

  @Override
  public String toString() {
    return getType().getTypeName();
  }

}
