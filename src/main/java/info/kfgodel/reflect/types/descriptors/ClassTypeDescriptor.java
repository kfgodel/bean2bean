package info.kfgodel.reflect.types.descriptors;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This type describes instances of Class
 * Date: 02/03/19 - 18:34
 */
public class ClassTypeDescriptor extends GeneralTypeDescriptor {

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
  public Map<TypeVariable, Type> calculateTypeVariableBindingsFor(Type[] actualArguments) {
    TypeVariable[] typeParameters = aClass.getTypeParameters();
    if(actualArguments.length != typeParameters.length){
      throw new IllegalArgumentException(
        "The class["+aClass+"] can't bind its parameters " + Arrays.toString(typeParameters)
          + " to the arguments " + Arrays.toString(actualArguments) + ". Arrays don't match"
      );
    }
    Map<TypeVariable, Type> parameterValues = new LinkedHashMap<>();
    for (int i = 0; i < typeParameters.length; i++) {
      TypeVariable typeParameter = typeParameters[i];
      Type typeArgument = actualArguments[i];
      parameterValues.put(typeParameter, typeArgument);
    }
    return parameterValues;
  }

  @Override
  public Optional<Class> getComponentType() {
    if(aClass.isArray()){
      return Optional.ofNullable(aClass.getComponentType());
    }
    return Optional.empty();
  }

  @Override
  public Optional<Class> getInstantiableClass() {
    if(isNonInstantiable()){
      // We try to prevent most of the non instantiable types
      return Optional.empty();
    }
    return Optional.of(aClass);
  }

  @Override
  public Optional<Class> getAssignableClass() {
    return Optional.of(aClass);
  }

  private boolean isNonInstantiable() {
    return aClass.isInterface()
      || aClass.isPrimitive()
      || aClass.isArray()
      || aClass.isAnnotation()
      || Modifier.isAbstract(aClass.getModifiers());
  }


}
