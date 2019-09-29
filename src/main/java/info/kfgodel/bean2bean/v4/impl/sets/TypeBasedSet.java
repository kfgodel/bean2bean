package info.kfgodel.bean2bean.v4.impl.sets;

import info.kfgodel.reflect.types.binding.BoundType;
import info.kfgodel.reflect.types.binding.DefaultBoundType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents the set implicitly defined by a Java type.<br>
 *   It's elements are all the possible instances of a particular type
 *
 * Date: 26/9/19 - 23:58
 */
public class TypeBasedSet implements Set {

  private Type rawType;
  private Type[] typeArguments;


  public static TypeBasedSet create(Type rawType, Type... typeArguments) {
    TypeBasedSet set = new TypeBasedSet();
    set.rawType = rawType;
    set.typeArguments = typeArguments;
    return set;
  }

  public static TypeBasedSet create(ParameterizedType parameterizedType) {
    final Type rawType = parameterizedType.getRawType();
    Type[] typeArguments = parameterizedType.getActualTypeArguments();
    return create(rawType, typeArguments);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TypeBasedSet that = (TypeBasedSet) o;
    return rawType.equals(that.rawType) &&
      Arrays.equals(typeArguments, that.typeArguments);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(rawType);
    result = 31 * result + Arrays.hashCode(typeArguments);
    return result;
  }

  @Override
  public String toString() {
    final String rawTypeName = rawType.getTypeName();
    if(typeArguments.length == 0){
      return rawTypeName;
    }
    final String typeArgumentsName = Arrays.stream(typeArguments)
      .map(Type::getTypeName)
      .collect(Collectors.joining(", ", "<", ">"));
    return rawTypeName + typeArgumentsName;
  }

  @Override
  public Stream<Set> getSuperSets() {
    return DefaultBoundType.create(rawType, typeArguments)
      .getUpwardHierarchy()
      .flatMap(this::calculateImpliedSets);
  }

  private Stream<? extends Set> calculateImpliedSets(BoundType boundType) {
    final Type boundRawType = boundType.getRawType();
    final Type[] boundArguments = boundType.getTypeArguments();
    final TypeBasedSet typeSet = TypeBasedSet.create(boundRawType, boundArguments);
    if(boundArguments.length > 0){
      // It's a parameterized type which also implies the non parameterized type set
      return Stream.of(typeSet, TypeBasedSet.create(boundRawType));
    }else{
      return Stream.of(typeSet);
    }
  }
}
