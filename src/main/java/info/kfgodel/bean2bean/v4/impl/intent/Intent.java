package info.kfgodel.bean2bean.v4.impl.intent;

import info.kfgodel.bean2bean.v4.impl.vector.ConversionVector;

/**
 * This class implements a default conversion intent in which there's no difference between the conversion vector
 * and the input value
 * Date: 25/9/19 - 17:43
 */
public class Intent<O> implements ConversionIntent<O> {

  private ConversionVector vector;
  private Object input;

  public ConversionVector getVector() {
    return vector;
  }

  @Override
  public <I> I getInput() {
    return (I) input;
  }

  public static Intent create(ConversionVector vector) {
    return create(vector.getSource(), vector);
  }

  public static Intent create(Object input, ConversionVector vector) {
    Intent intent = new Intent();
    intent.vector = vector;
    intent.input = input;
    return intent;
  }

}
