package info.kfgodel.bean2bean.core.impl.conversion;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This type represents the converter that delegates on a bifunction to do the actual conversion
 * and passes a b2b dsl instance to it
 *
 * Date: 16/02/19 - 14:30
 */
public class BiFunctionAdapterConverter implements Function<Bean2beanTask, Object> {

  private BiFunction<Object,Bean2beanTask,Object> biFunction;

  @Override
  public Object apply(Bean2beanTask task) {
    Object input = task.getSource();
    Object output = biFunction.apply(input, task);
    return output;
  }

  public static BiFunctionAdapterConverter create(BiFunction<?, Bean2beanTask, ?> function) {
    BiFunctionAdapterConverter converter = new BiFunctionAdapterConverter();
    converter.biFunction = (BiFunction<Object, Bean2beanTask, Object>) function;
    return converter;
  }

}
