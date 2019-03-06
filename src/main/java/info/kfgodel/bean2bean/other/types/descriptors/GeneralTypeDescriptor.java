package info.kfgodel.bean2bean.other.types.descriptors;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This type describes {@link java.lang.reflect.TypeVariable} types or {@link java.lang.reflect.WildcardType} types
 * Date: 02/03/19 - 18:39
 */
public class GeneralTypeDescriptor implements JavaTypeDescriptor {

  private Type aType;

  public static GeneralTypeDescriptor create(Type aType) {
    GeneralTypeDescriptor descriptor = new GeneralTypeDescriptor();
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
  public Type[] getTypeArgumentsBindedWith(Map<TypeVariable, Type> typeParameterBindings) {
    return NO_ARGUMENTS;
  }

  @Override
  public Stream<Type> getGenericSupertypes() {
    return Stream.empty();
  }

  @Override
  public Map<TypeVariable, Type> calculateTypeVariableBindingsFor(Type[] typeArguments) {
    return Collections.emptyMap();
  }

  @Override
  public Optional<Class> getComponentType() {
    return Optional.empty();
  }

  @Override
  public Optional<Class> getInstantiableClass() {
    return Optional.empty();
  }

  @Override
  public String toString() {
    return getType().getTypeName();
  }

}
