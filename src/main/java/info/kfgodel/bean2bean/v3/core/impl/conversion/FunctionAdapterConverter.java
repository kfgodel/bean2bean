package info.kfgodel.bean2bean.v3.core.impl.conversion;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;

import java.util.function.Function;

/**
 * This type represents the converter based on a simple function to do the actual conversion from input value
 * Date: 12/02/19 - 01:18
 */
public class FunctionAdapterConverter implements Function<Bean2beanTask, Object> {

  private Function<Object, Object> function;

  @Override
  public Object apply(Bean2beanTask objectConversion) {
    Object input = objectConversion.getSource();
    Object output = function.apply(input);
    return output;
  }

  @Override
  public String toString() {
    return "FunctionAdapterConverter{" +
      "function=" + function +
      '}';
  }

  public static FunctionAdapterConverter create(Function<?,?> function) {
    FunctionAdapterConverter process = new FunctionAdapterConverter();
    process.function = (Function<Object, Object>) function;
    return process;
  }

}
