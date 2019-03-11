package info.kfgodel.bean2bean.converters;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.other.types.extraction.TypeArgumentExtractor;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * This class converts objects into optional instances by wrapping them into conversions
 * Date: 07/03/19 - 23:07
 */
public class Object2OptionalConverter implements BiFunction<Object, Bean2beanTask, Optional> {

  @Override
  public Optional apply(Object source, Bean2beanTask bean2beanTask) {
    Type containedType = deduceExpectedContainedTypeFor(bean2beanTask);
    Object contained = bean2beanTask.getDsl().convert().from(source).to(containedType);
    return Optional.ofNullable(contained);
  }

  private Type deduceExpectedContainedTypeFor(Bean2beanTask task) {
    Type targetType = task.getTargetType();
    Type elementType = TypeArgumentExtractor.create().getArgumentUsedFor(Optional.class, targetType)
      .orElse(Object.class);
    return elementType;
  }

  public static Object2OptionalConverter create() {
    Object2OptionalConverter object2OptionalConverter = new Object2OptionalConverter();
    return object2OptionalConverter;
  }

}
