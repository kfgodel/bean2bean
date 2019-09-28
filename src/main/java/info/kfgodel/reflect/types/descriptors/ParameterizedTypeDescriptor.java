package info.kfgodel.reflect.types.descriptors;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;
import java.util.Optional;

/**
 * This type describes instances of {@link java.lang.reflect.ParameterizedType}
 * Date: 02/03/19 - 18:35
 */
public class ParameterizedTypeDescriptor extends GeneralTypeDescriptor {

  private ParameterizedType aType;

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
    return Optional.of(rawType)
      .filter(Class.class::isInstance)
      .map(Class.class::cast);
  }

  @Override
  public Type[] getTypeArgumentsBindedWith(Map<TypeVariable, Type> typeParameterBindings) {
    Type[] declaredArguments = getTypeArguments();
    Type[] replacedArguments = new Type[declaredArguments.length];
    for (int i = 0; i < declaredArguments.length; i++) {
      Type declaredArgument = declaredArguments[i];
      Optional<Type> replacementArgument = Optional.ofNullable(typeParameterBindings.get(declaredArgument));
      replacedArguments[i] = replacementArgument.orElse(declaredArgument);
    }
    return replacedArguments;
  }

  @Override
  public Optional<Class> getInstantiableClass() {
    return getErasuredType();
  }

  @Override
  public Optional<Class> getAssignableClass() {
    return getErasuredType();
  }

  public static ParameterizedTypeDescriptor create(ParameterizedType aType) {
    ParameterizedTypeDescriptor descriptor = new ParameterizedTypeDescriptor();
    descriptor.aType = aType;
    return descriptor;
  }

}
