package info.kfgodel.bean2bean.dsl.api.converters;

import java.util.function.Function;

/**
 * This class serves as test example for array creation
 * Date: 16/02/19 - 19:28
 */
public class StringArrayGenerator implements Function<Integer, String[]> {

  @Override
  public String[] apply(Integer arraySize) {
    return new String[arraySize];
  }

  public static StringArrayGenerator create() {
    StringArrayGenerator generator = new StringArrayGenerator();
    return generator;
  }

}
