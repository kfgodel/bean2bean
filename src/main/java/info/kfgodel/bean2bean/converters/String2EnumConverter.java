package info.kfgodel.bean2bean.converters;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.impl.descriptor.ObjectDescriptor;

import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * This class converts strings into enum instances by using them as the name of the enum value
 * Date: 12/03/19 - 21:49
 */
public class String2EnumConverter implements BiFunction<String, Bean2beanTask, Enum> {
  @Override
  public Enum apply(String source, Bean2beanTask task) {
    Object[] enumValues = getAllEnumValuesFromTargetClass(task);
    Enum enumValue = findValueIn(enumValues, source, task);
    return enumValue;
  }

  private Enum findValueIn(Object[] enumValues, String source, Bean2beanTask task) {
    return Arrays.stream(enumValues)
        .map(Enum.class::cast)
        .filter(enumValue -> source.equals(enumValue.name()))
        .findFirst()
        .orElseThrow(() -> missingEnumValue(source, task));
  }

  private ConversionException missingEnumValue(String source, Bean2beanTask task) {
    return new ConversionException("No enum value named \"" + source + "\" found in " + task.getTargetType(), task);
  }

  private Object[] getAllEnumValuesFromTargetClass(Bean2beanTask task) {
    return task.getTargetTypeDescriptor()
        .getAssignableClass()
        .map(Class::getEnumConstants)
        .orElseThrow(() -> notAnEnumException(task));
  }

  private ConversionException notAnEnumException(Bean2beanTask task) {
    String targetDescription = ObjectDescriptor.create().describeTarget(task.getTargetType(), task.getConversionVector().getTarget());
    return new ConversionException("Target type["+ targetDescription+"] does not have enum values", task);
  }

  public static String2EnumConverter create() {
    String2EnumConverter converter = new String2EnumConverter();
    return converter;
  }

}
