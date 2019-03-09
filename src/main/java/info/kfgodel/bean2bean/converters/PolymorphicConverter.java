package info.kfgodel.bean2bean.converters;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.other.types.descriptors.JavaTypeDescriptor;

import java.lang.reflect.Type;
import java.util.function.BiFunction;

/**
 * This class represents the converter that needs no real conversion since source object
 * is a substitution type for target type
 *
 * Date: 09/03/19 - 12:25
 */
public class PolymorphicConverter implements BiFunction<Object, Bean2beanTask, Object> {
  @Override
  public Object apply(Object source, Bean2beanTask task) {
    Class assignableClass = deduceAssignableClassFor(task, source);
    if(!assignableClass.isInstance(source)){
      throw new ConversionException("Source " + source + "{" + getClassNameOf(source) + "} is not assignable to target " +
        describeTarget(task, assignableClass),task);
    }
    return source;
  }

  private String describeTarget(Bean2beanTask task, Type assignableClass) {
    Type targetType = task.getTargetType();
    String shortDescription = "type[" + targetType + "]";
    if(targetType.equals(assignableClass)){
      return shortDescription;
    }
    // Add extra info for type variables
    return shortDescription + "{" + assignableClass.getTypeName() + "}";
  }

  private String getClassNameOf(Object source) {
    if(source == null){
      return "null";
    }
    return source.getClass().getTypeName();
  }

  private Class deduceAssignableClassFor(Bean2beanTask task, Object source) {
    JavaTypeDescriptor targetTypeDescriptor = task.getTargetTypeDescriptor();
    return targetTypeDescriptor.getAssignableClass()
      .orElseThrow(() -> new ConversionException("Cannot determine if target type[" + task.getTargetType() + "] is safely assingable from source[" + source + "]: Can't find class for target type", task));
  }

  public static PolymorphicConverter create() {
    PolymorphicConverter converter = new PolymorphicConverter();
    return converter;
  }

}
