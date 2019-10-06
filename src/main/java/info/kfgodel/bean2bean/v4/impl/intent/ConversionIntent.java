package info.kfgodel.bean2bean.v4.impl.intent;

import info.kfgodel.bean2bean.v4.impl.vector.ConversionVector;

/**
 * This interface represents the user intention to transform an element into another form or type,
 * or to convert the state of the world in some form
 * Date: 23/9/19 - 16:15
 * @param <O> Expected type for the conversion result
 */
public interface ConversionIntent<O> {

  /**
   * Returns a vector that indicates the direction in which the conversion is expected to happen.<br>
   *   This vector is an abstract representation of the conversion, so the vector source and target
   *   may be different from the actual intent's input and target
   * @return The vector in which the conversion is needed
   */
  ConversionVector getVector();

  /**
   * Returns the object that is the input of this intent conversion. The input represents a value that is to be
   * transformed into the output.<br>
   * @param <I> The type of expected input
   * @return The input value
   */
  <I> I getInput();
}
