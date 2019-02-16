package info.kfgodel.bean2bean.core.impl.conversion;

import java.util.function.Function;

/**
 * This type represents the converter based on a simple function to do the actual conversion from input value
 * Date: 12/02/19 - 01:18
 */
public class FunctionAdapterConverter implements Function<ObjectConversion, Object> {

  private Function function;

  @Override
  public Object apply(ObjectConversion objectConversion) {
    Object input = objectConversion.getSource();
    Object output = function.apply(input);
    return output;
  }

  public static FunctionAdapterConverter create(Function function) {
    FunctionAdapterConverter process = new FunctionAdapterConverter();
    process.function = function;
    return process;
  }

}
