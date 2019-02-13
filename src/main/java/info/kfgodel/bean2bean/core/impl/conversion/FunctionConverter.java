package info.kfgodel.bean2bean.core.impl.conversion;

import java.util.function.Function;

/**
 * This type represents the converter based on a simple function to do the actual conversion from input value
 * Date: 12/02/19 - 01:18
 */
public class FunctionConverter implements Function<ObjectConversion, Object> {

  private Function converter;

  @Override
  public Object apply(ObjectConversion objectConversion) {
    Object input = objectConversion.getSource();
    Object output = converter.apply(input);
    return output;
  }

  public static FunctionConverter create(Function converter) {
    FunctionConverter process = new FunctionConverter();
    process.converter = converter;
    return process;
  }

}
