package info.kfgodel.bean2bean.core.impl.registry;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * This type represents a conversion direction defined by two types
 * Date: 12/02/19 - 00:31
 */
public class TypeVector {

  private Type sourceType;
  private Type destinationType;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof TypeVector)) return false;
    TypeVector that = (TypeVector) o;
    return sourceType.equals(that.sourceType) &&
      destinationType.equals(that.destinationType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(sourceType, destinationType);
  }

  public static TypeVector create(Type source, Type destination) {
    TypeVector vector = new TypeVector();
    vector.sourceType = source;
    vector.destinationType = destination;
    return vector;
  }


}
