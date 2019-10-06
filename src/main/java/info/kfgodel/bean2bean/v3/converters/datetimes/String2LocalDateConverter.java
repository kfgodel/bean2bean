package info.kfgodel.bean2bean.v3.converters.datetimes;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.core.api.exceptions.ConversionException;

import java.time.LocalDate;
import java.util.function.BiFunction;

/**
 * This class knows how to parse an iso string into a {@link LocalDate}
 * Date: 24/03/19 - 14:09
 */
public class String2LocalDateConverter implements BiFunction<String, Bean2beanTask, LocalDate> {

  @Override
  public LocalDate apply(String source, Bean2beanTask task) {
    try {
      return LocalDate.parse(source);
    } catch (Exception e) {
      throw new ConversionException("Failed to parse [" + source + "] into a " + task.getTargetType().getTypeName() + ": " + e.getMessage(), task, e);
    }
  }

  public static String2LocalDateConverter create() {
    String2LocalDateConverter converter = new String2LocalDateConverter();
    return converter;
  }
}