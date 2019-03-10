package info.kfgodel.bean2bean.converters;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;

import java.util.Optional;
import java.util.function.BiFunction;

/**
 * This class converts optionals into a plain type
 *
 * Date: 07/03/19 - 23:02
 */
public class Optional2ObjectConverter implements BiFunction<Optional, Bean2beanTask, Object> {
  @Override
  public Object apply(Optional optional, Bean2beanTask task) {
    return optional
      .map(contained -> this.convert(contained, task))
      .orElse(null);
  }

  private Object convert(Object contained, Bean2beanTask task) {
    return task.getDsl().convert().from(contained).to(task.getTargetType());
  }

  public static Optional2ObjectConverter create() {
    Optional2ObjectConverter converter = new Optional2ObjectConverter();
    return converter;
  }

}
