package info.kfgodel.bean2bean.converters.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.impl.descriptor.ObjectDescriptor;

import java.util.function.BiFunction;

/**
 * This class converts any object into its JSON string representation.<br>
 *    For this converter to work Jackson must be in the classpath. It's not included by default<br>
 * Date: 19/03/19 - 19:50
 */
public class Object2JsonStringConverter implements BiFunction<Object, Bean2beanTask, String> {
  private ObjectMapper jsonMapper;

  @Override
  public String apply(Object source, Bean2beanTask task) {
    try {
      return jsonMapper.writeValueAsString(source);
    } catch (Exception e) {
      ObjectDescriptor objectDescriptor = ObjectDescriptor.create();
      String sourceDescription = objectDescriptor.describeSource(source, task.getConversionVector().getSource());
      throw new ConversionException("Failed to generate JSON for " + sourceDescription+ ": " + e.getMessage(), task, e);
    }
  }

  public static Object2JsonStringConverter create() {
    Object2JsonStringConverter converter = new Object2JsonStringConverter();
    converter.jsonMapper = new ObjectMapper();
    return converter;
  }

}
