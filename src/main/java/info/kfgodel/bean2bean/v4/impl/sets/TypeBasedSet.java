package info.kfgodel.bean2bean.v4.impl.sets;

import info.kfgodel.reflect.types.SupertypeSpliterator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
    if(rawType instanceof ParameterizedType){
      // TODO: take actual type arguments into consideration
      return create((ParameterizedType) rawType);
    }
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
    return StreamSupport.stream(SupertypeSpliterator.create(rawType), false)
      .map(javaType -> TypeBasedSet.create(javaType));
  }
}
