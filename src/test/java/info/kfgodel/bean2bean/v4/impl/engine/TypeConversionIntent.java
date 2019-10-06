package info.kfgodel.bean2bean.v4.impl.engine;

import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.sets.TypeBasedSet;
import info.kfgodel.bean2bean.v4.impl.vector.ConversionVector;
import info.kfgodel.bean2bean.v4.impl.vector.Vector;

/**
 * This class intends to be example on how intent differ from vectors and use it as an example for simple conversions
 * Date: 25/9/19 - 19:24
 */
public class TypeConversionIntent<O> implements ConversionIntent<O> {

  private Object input;
  private TypeBasedSet targetType;

  @Override
  public ConversionVector getVector() {
    final TypeBasedSet sourceType = TypeBasedSet.create(input.getClass());
    return Vector.create(sourceType, targetType);
  }

  @Override
  public <I> I getInput() {
    return (I) input;
  }

  public static <O> TypeConversionIntent<O> create(Object input, TypeBasedSet targetType) {
    TypeConversionIntent intent = new TypeConversionIntent();
    intent.input = input;
    intent.targetType = targetType;
    return intent;
  }

  @Override
  public String toString() {
    return "TypeConversionIntent{" +
      "input=" + input +
      ", targetType=" + targetType +
      '}';
  }
}
