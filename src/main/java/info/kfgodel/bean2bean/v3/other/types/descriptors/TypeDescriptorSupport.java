package info.kfgodel.bean2bean.v3.other.types.descriptors;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class serves as an abstract template class for subclasses so only
 * some methods need to be defined
 *
 * Date: 10/03/19 - 16:39
 */
public abstract class TypeDescriptorSupport implements JavaTypeDescriptor {

  @Override
  public Type[] getTypeArguments() {
    return JavaTypeDescriptor.NO_TYPES;
  }

  @Override
  public Optional<Class> getErasuredType() {
    return Optional.empty();
  }

  @Override
  public Type[] getTypeArgumentsBindedWith(Map<TypeVariable, Type> typeParameterBindings) {
    return NO_TYPES;
  }

  @Override
  public Stream<Type> getGenericSupertypes() {
    return Stream.empty();
  }

  @Override
  public Stream<Type> getUpperBounds() {
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
    return getAssignableClass();
  }

  @Override
  public Optional<Class> getAssignableClass() {
    return Optional.empty();
  }

  @Override
  public String toString() {
    return getType().getTypeName();
  }
}
