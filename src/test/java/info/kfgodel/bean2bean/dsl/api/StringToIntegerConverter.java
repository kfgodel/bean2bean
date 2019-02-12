package info.kfgodel.bean2bean.dsl.api;

import java.util.function.Function;

/**
 * Example function for converting strings to integers
 * Date: 12/02/19 - 01:02
 */
public class StringToIntegerConverter implements Function<String, Integer> {

  @Override
  public Integer apply(String s) {
    return Integer.parseInt(s);
  }

  public static StringToIntegerConverter create() {
    StringToIntegerConverter converter = new StringToIntegerConverter();
    return converter;
  }

}
