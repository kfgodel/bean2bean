package info.kfgodel.bean2bean.v4.impl.intent;

import info.kfgodel.bean2bean.v4.impl.vector.ConversionVector;

/**
 * This class implements the default conversion intent
 * Date: 25/9/19 - 17:43
 */
public class Intent<O> implements ConversionIntent<O> {

  private ConversionVector vector;

  public ConversionVector getVector() {
    return vector;
  }

  public static Intent create(ConversionVector vector) {
    Intent intent = new Intent();
    intent.vector = vector;
    return intent;
  }

}
