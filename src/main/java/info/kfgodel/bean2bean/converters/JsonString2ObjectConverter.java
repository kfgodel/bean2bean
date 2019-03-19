package info.kfgodel.bean2bean.converters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.impl.descriptor.ObjectDescriptor;

import java.lang.reflect.Type;
import java.util.function.BiFunction;

/**
 * This class interprets a JSON string input into the expected target type.<br>
 *   For this converter to work Jackson must be in the classpath. It's not included by default<br>
 * Date: 19/03/19 - 19:49
 */
public class JsonString2ObjectConverter implements BiFunction<String, Bean2beanTask, Object> {

  private ObjectMapper jsonMapper;

  @Override
  public Object apply(String source, Bean2beanTask task) {
    Type expectedType = task.getTargetType();
    try {
      return jsonMapper.readValue(source, getJacksonTypeRefFor(expectedType));
    } catch (Exception e) {
      ObjectDescriptor objectDescriptor = ObjectDescriptor.create();
      String sourceDescription = objectDescriptor.describeInstance(source);
      String targetDescription = objectDescriptor.describeTarget(expectedType, task.getConversionVector().getTarget());
      throw new ConversionException("Failed to parse JSON from " + sourceDescription + " into " + targetDescription + ": " + e.getMessage() , task, e);
    }
  }

  private TypeReference getJacksonTypeRefFor(Type expectedType) {
    //Jackson doesn't accept Type as input, but does have its own type ref
    return new TypeReference<Object>() {
      @Override
      public Type getType() {
        return expectedType;
      }
    };
  }

  public static JsonString2ObjectConverter create() {
    JsonString2ObjectConverter converter = new JsonString2ObjectConverter();
    converter.jsonMapper = new ObjectMapper();
    return converter;
  }
}
