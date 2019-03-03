package info.kfgodel.bean2bean.other.types.descriptors;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This type describes instances of Class
 * Date: 02/03/19 - 18:34
 */
public class ClassTypeDescriptor implements JavaTypeDescriptor {

  private Class aClass;

  public static ClassTypeDescriptor create(Class aClass) {
    ClassTypeDescriptor descriptor = new ClassTypeDescriptor();
    descriptor.aClass = aClass;
    return descriptor;
  }

  @Override
  public Type getType() {
    return aClass;
  }

  @Override
  public Type[] getTypeArguments() {
    return NO_ARGUMENTS;
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
    Stream<Type> genericSuperclass = Optional.ofNullable(aClass.getGenericSuperclass())
      .map(Stream::of)
      .orElse(Stream.empty());
    Stream<Type> genericInterfaces = Optional.ofNullable(aClass.getGenericInterfaces())
      .map(Arrays::stream)
      .orElse(Stream.empty());
    return Stream.concat(genericSuperclass, genericInterfaces);
  }

  @Override
  public String toString() {
    return getType().getTypeName();
  }
}
