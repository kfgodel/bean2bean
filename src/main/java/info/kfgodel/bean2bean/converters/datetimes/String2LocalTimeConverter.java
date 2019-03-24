package info.kfgodel.bean2bean.converters.datetimes;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;

import java.time.LocalTime;
import java.util.function.BiFunction;

/**
 * This class knows how to parse an iso string into a {@link LocalTime}
 * Date: 24/03/19 - 14:09
 */
public class String2LocalTimeConverter implements BiFunction<String, Bean2beanTask, LocalTime> {

  @Override
  public LocalTime apply(String source, Bean2beanTask task) {
    try {
      return LocalTime.parse(source);
    } catch (Exception e) {
      throw new ConversionException("Failed to parse [" + source + "] into a " + task.getTargetType().getTypeName() + ": " + e.getMessage(), task, e);
    }
  }

  public static String2LocalTimeConverter create() {
    String2LocalTimeConverter converter = new String2LocalTimeConverter();
    return converter;
  }
}