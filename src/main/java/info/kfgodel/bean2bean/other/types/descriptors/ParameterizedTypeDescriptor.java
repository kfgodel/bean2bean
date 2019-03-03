package info.kfgodel.bean2bean.other.types.descriptors;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This type describes instances of {@link java.lang.reflect.ParameterizedType}
 * Date: 02/03/19 - 18:35
 */
public class ParameterizedTypeDescriptor implements JavaTypeDescriptor {

  private ParameterizedType aType;

  public static ParameterizedTypeDescriptor create(ParameterizedType aType) {
    ParameterizedTypeDescriptor descriptor = new ParameterizedTypeDescriptor();
    descriptor.aType = aType;
    return descriptor;
  }

  @Override
  public Type getType() {
    return aType;
  }

  @Override
  public Type[] getTypeArguments() {
    return aType.getActualTypeArguments();
  }

  @Override
  public Optional<Class> getErasuredType() {
    Type rawType = aType.getRawType();
    Class rawClass = (Class) rawType;
    return Optional.of(rawClass);
  }

  @Override
  public Type[] getTypeArgumentsReplacingParametersWith(Map<TypeVariable, Type> typeParameterValues) {
    Type[] declaredArguments = getTypeArguments();
    Type[] replacedArguments = new Type[declaredArguments.length];
    for (int i = 0; i < declaredArguments.length; i++) {
      Type declared = declaredArguments[i];
      Optional<Type> replacement = Optional.ofNullable(typeParameterValues.get(declared));
      replacedArguments[i] = replacement.orElse(declared);
    }
    return replacedArguments;
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
