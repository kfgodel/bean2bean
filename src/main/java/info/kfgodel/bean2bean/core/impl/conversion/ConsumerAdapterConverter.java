package info.kfgodel.bean2bean.core.impl.conversion;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This type adapts a consumer function into a converter function
 * Date: 17/02/19 - 13:06
 */
public class ConsumerAdapterConverter implements Function<Bean2beanTask, Object> {

  private Consumer<Object> consumer;

  @Override
  public Object apply(Bean2beanTask objectConversion) {
    Object input = objectConversion.getSource();
    consumer.accept(input);
    return null;
  }

  @Override
  public String toString() {
    return "ConsumerAdapterConverter{" +
      "consumer=" + consumer +
      '}';
  }

  public static ConsumerAdapterConverter create(Consumer<?> consumer) {
    ConsumerAdapterConverter converter = new ConsumerAdapterConverter();
    converter.consumer = (Consumer<Object>) consumer;
    return converter;
  }

}
