package net.sf.kfgodel.bean2bean.impl.transfunctions.primitives;

import net.sf.kfgodel.bean2bean.api.exceptions.Bean2beanException;
import net.sf.kfgodel.bean2bean.api.exceptions.FailedToConvertException;

/**
 * This class serves as a namespace for primitive String conversion transfunctions
 *
 * Created by kfgodel on 05/07/16.
 */
public class StringFunction {

  /**
   * Converts a string to a long object using the default language conversion.<br>
   *   It improves the default errors by adding some context
   *   @throws IllegalArgumentException If null is passed as String
   *   @throws FailedToConvertException If the string doesn't represent an integer number
   */
  public static Long toLong(String input) throws IllegalArgumentException, Bean2beanException {
    if(input == null){
      throw new IllegalArgumentException("This transfuction doesn't support the String[null] as input. There's no default conversion to Long");
    }
    try {
      return Long.valueOf(input);
    } catch (NumberFormatException e) {
      throw new FailedToConvertException("Cannot convert the String [" + input + "] to Long: Unable to parse it", e);
    }
  }
}
