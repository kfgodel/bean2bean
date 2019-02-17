package info.kfgodel.bean2bean.core.impl.conversion;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This type adapts a consumer function into a converter function
 * Date: 17/02/19 - 13:06
 */
public class ConsumerAdapterConverter implements Function<ObjectConversion, Object> {

  private Consumer consumer;

  @Override
  public Object apply(ObjectConversion objectConversion) {
    Object input = objectConversion.getSource();
    consumer.accept(input);
    return null;
  }

  public static ConsumerAdapterConverter create(Consumer<?> consumer) {
    ConsumerAdapterConverter converter = new ConsumerAdapterConverter();
    converter.consumer = consumer;
    return converter;
  }

}
