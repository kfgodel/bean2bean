package info.kfgodel.bean2bean.v4.impl.engine;

import info.kfgodel.bean2bean.v4.api.exceptions.B2bException;

/**
 * This interface represents the core processing logic for Bean2Bean.<br>
 *   On its core, every operation in b2b is a transformation that the engine needs to process to get its result
 * Date: 23/9/19 - 15:58
 */
public interface B2bEngine {

  /**
   * Interprets the given intent to find the best converter function to apply,
   * processes the conversion an returns the result
   * @param intent The intent indicating which conversion is expected
   * @param <O> The type of expected result
   * @return The result of the conversion
   * @throws B2bException If there's an error on the conversion, or a proper conversion cannot be applied
   */
  <O> O apply(ConversionIntent<O> intent) throws B2bException;
}
