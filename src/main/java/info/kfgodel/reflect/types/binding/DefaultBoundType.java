package info.kfgodel.reflect.types.binding;

import info.kfgodel.reflect.types.descriptors.JavaTypeDescriptor;
import info.kfgodel.reflect.types.spliterators.BoundTypeSpliterator;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This class is the default implementation for a bound type
 * Date: 29/9/19 - 15:16
 */
public class DefaultBoundType implements BoundType {

  private JavaTypeDescriptor rawType;
  private Type[] typeArguments;

  @Override
  public Type getRawType() {
    return rawType.getType();
  }

  @Override
  public Type[] getTypeArguments() {
    return typeArguments;
  }

  @Override
  public Stream<BoundType> getDirectSupertypes() {
    final Map<TypeVariable, Type> typeBindings = rawType.calculateTypeVariableBindingsFor(typeArguments);
    return rawType.getGenericSupertypes()
      .map(JavaTypeDescriptor::createFor)
      .map(supertype -> {
        final Type[] superTypeArguments = supertype.getTypeArgumentsBindedWith(typeBindings);
        final Type rawType = supertype.getErasuredType()
          .map(Type.class::cast)
          .orElseGet(supertype::getType);
        return DefaultBoundType.create(rawType, superTypeArguments);
      });
  }

  @Override
  public Stream<BoundType> getUpwardHierarchy() {
    return StreamSupport.stream(BoundTypeSpliterator.create(this), false);
  }

  public static DefaultBoundType create(Type rawType, Type... typeArguments) {
    DefaultBoundType type = new DefaultBoundType();
    type.rawType = JavaTypeDescriptor.createFor(rawType);
    type.typeArguments = typeArguments;
    return type;
  }

  @Override
  public String toString() {
    return "DefaultBoundType{" +
      "rawType=" + rawType +
      ", typeArguments=" + Arrays.toString(typeArguments) +
      '}';
  }
}
