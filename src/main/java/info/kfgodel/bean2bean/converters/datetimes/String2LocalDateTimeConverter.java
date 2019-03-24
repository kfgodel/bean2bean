package info.kfgodel.bean2bean.converters.datetimes;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;

import java.time.LocalDateTime;
import java.util.function.BiFunction;

/**
 * This class knows how to parse an iso string into a {@link LocalDateTime}
 * Date: 24/03/19 - 14:09
 */
public class String2LocalDateTimeConverter implements BiFunction<String, Bean2beanTask, LocalDateTime> {

  @Override
  public LocalDateTime apply(String source, Bean2beanTask task) {
    try {
      return LocalDateTime.parse(source);
    } catch (Exception e) {
      throw new ConversionException("Failed to parse [" + source + "] into a " + task.getTargetType().getTypeName() + ": " + e.getMessage(), task, e);
    }
  }

  public static String2LocalDateTimeConverter create() {
    String2LocalDateTimeConverter converter = new String2LocalDateTimeConverter();
    return converter;
  }
}