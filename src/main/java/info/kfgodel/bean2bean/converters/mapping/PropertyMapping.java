package info.kfgodel.bean2bean.converters.mapping;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.Bean2BeanException;
import info.kfgodel.bean2bean.core.api.exceptions.MappingException;
import info.kfgodel.bean2bean.core.impl.descriptor.ObjectDescriptor;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * This class represents a single mapping from a property in one object to a property in another.<br>
 *   The mapping can involve a type conversion if source and destination types don't match
 *
 * Date: 24/03/19 - 21:21
 */
public class PropertyMapping {

  private Function<Object,Object> getterFunction;
  private Optional<Type> targetType;
  private BiConsumer<Object,Object> setterFunction;

  public void applyOn(Object source, Bean2beanTask task, Object target){
    Object propertyValue = getValueFrom(source, task);
    Object convertedValue = convertToNeededTypeUsing(task, propertyValue);
    setValueOn(target, convertedValue, task);
  }

  private void setValueOn(Object target, Object convertedValue, Bean2beanTask task) {
    try {
      setterFunction.accept(target, convertedValue);
    } catch (Exception e) {
      ObjectDescriptor objectDescriptor = ObjectDescriptor.create();
      String valueDescription = objectDescriptor.describeInstance(convertedValue);
      String targetDescription = objectDescriptor.describeInstance(target);
      throw new MappingException("Failed to set value["+valueDescription+"] onto ["+targetDescription+"] using setter: " + setterFunction, task, e);
    }
  }

  private Object convertToNeededTypeUsing(Bean2beanTask task, Object propertyValue) {
    return targetType
        .map(type -> {
          try {
            return task.getDsl().convert().from(propertyValue).to(type);
          } catch (Bean2BeanException e) {
            ObjectDescriptor objectDescriptor = ObjectDescriptor.create();
            String valueDescription = objectDescriptor.describeInstance(propertyValue);
            String typeDescription = objectDescriptor.describeType(type);
            throw new MappingException("Failed to convert property value["+valueDescription+"] to expected type: " + typeDescription, task, e);
          }
        })
        .orElse(propertyValue);
  }

  private Object getValueFrom(Object source, Bean2beanTask task) {
    try {
      return getterFunction.apply(source);
    } catch (Exception e) {
      String sourceDescription = ObjectDescriptor.create().describeInstance(source);
      throw new MappingException("Failed to get value from ["+sourceDescription+"] using getter: " + getterFunction, task, e);
    }
  }

  public static<I,O,T> PropertyMapping create(Function<I, T> getterFunction, BiConsumer<O, T> setterFunction) {
    return create(getterFunction, Optional.empty(), setterFunction);
  }

  public static PropertyMapping create(Function<?, ?> getterFunction, Type expectedType, BiConsumer<?, ?> setterFunction) {
    return create(getterFunction, Optional.of(expectedType), setterFunction);
  }

  public static PropertyMapping create(Function<?, ?> getterFunction, Optional<Type> targetType, BiConsumer<?, ?> setterFunction) {
    PropertyMapping mapping = new PropertyMapping();
    mapping.getterFunction = (Function<Object, Object>) getterFunction;
    mapping.targetType = targetType;
    mapping.setterFunction = (BiConsumer<Object, Object>) setterFunction;
    return mapping;
  }


}
