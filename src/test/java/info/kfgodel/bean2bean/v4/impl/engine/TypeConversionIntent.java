package info.kfgodel.bean2bean.v4.impl.engine;

import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.vector.ConversionVector;
import info.kfgodel.bean2bean.v4.impl.vector.Vector;

import java.lang.reflect.Type;

/**
 * This class intends to be example on how intent differ from vectors and use it as an example for simple conversions
 * Date: 25/9/19 - 19:24
 */
public class TypeConversionIntent<O> implements ConversionIntent<O> {

  private Object input;
  private Type targetType;

  @Override
  public ConversionVector getVector() {
    return Vector.create(input.getClass(), targetType);
  }

  @Override
  public <I> I getInput() {
    return (I) input;
  }

  public static <O> TypeConversionIntent<O> create(Object input, Class<O> targetType) {
    TypeConversionIntent intent = new TypeConversionIntent();
    intent.input = input;
    intent.targetType = targetType;
    return intent;
  }

}
