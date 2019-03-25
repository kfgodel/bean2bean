package info.kfgodel.bean2bean.converters.mapping;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.Bean2BeanException;
import info.kfgodel.bean2bean.core.api.exceptions.MappingException;
import info.kfgodel.bean2bean.core.impl.descriptor.ObjectDescriptor;

import java.lang.reflect.Type;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * This class represents a single mapping from a property in one object to a property in another.<br>
 *   This mapping requires a value conversion before assignment on the destination property
 *
 * Date: 24/03/19 - 21:21
 */
public class ConvertedPropertyMapping extends PropertyMappingSupport{

  private Type targetType;

  public void applyOn(Object source, Bean2beanTask task, Object target){
    Object propertyValue = getValueFrom(source, task);
    Object convertedValue = convertToNeededTypeUsing(task, propertyValue);
    setValueOn(target, convertedValue, task);
  }

  private Object convertToNeededTypeUsing(Bean2beanTask task, Object propertyValue) {
    try {
      return task.getDsl().convert().from(propertyValue).to(targetType);
    } catch (Bean2BeanException e) {
      ObjectDescriptor objectDescriptor = ObjectDescriptor.create();
      String valueDescription = objectDescriptor.describeInstance(propertyValue);
      String typeDescription = objectDescriptor.describeType(targetType);
      throw new MappingException("Failed to convert property value["+valueDescription+"] to expected type: " + typeDescription, task, e);
    }
  }

  public static ConvertedPropertyMapping create(Function<?, ?> getterFunction, Type expectedType, BiConsumer<?, ?> setterFunction) {
    ConvertedPropertyMapping mapping = new ConvertedPropertyMapping();
    mapping.setGetterFunction(getterFunction);
    mapping.targetType = expectedType;
    mapping.setSetterFunction(setterFunction);
    return mapping;
  }

}
