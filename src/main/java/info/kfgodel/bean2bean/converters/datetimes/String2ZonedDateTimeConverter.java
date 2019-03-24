package info.kfgodel.bean2bean.converters.datetimes;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;

import java.time.ZonedDateTime;
import java.util.function.BiFunction;

/**
 * This class knows how to parse a string into a {@link java.time.ZonedDateTime}
 * Date: 23/03/19 - 18:40
 */
public class String2ZonedDateTimeConverter implements BiFunction<String, Bean2beanTask, ZonedDateTime> {

  @Override
  public ZonedDateTime apply(String source, Bean2beanTask task) {
    try {
      return ZonedDateTime.parse(source);
    } catch (Exception e) {
      throw new ConversionException("Failed to parse [" + source + "] into a " + task.getTargetType().getTypeName() + ": " + e.getMessage(), task, e);
    }
  }

  public static String2ZonedDateTimeConverter create() {
    String2ZonedDateTimeConverter converter = new String2ZonedDateTimeConverter();
    return converter;
  }

}
