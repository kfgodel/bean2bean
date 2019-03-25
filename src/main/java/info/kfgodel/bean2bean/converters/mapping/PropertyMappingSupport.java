package info.kfgodel.bean2bean.converters.mapping;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.MappingException;
import info.kfgodel.bean2bean.core.impl.descriptor.ObjectDescriptor;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * This class defines common behavior for property mappings
 * Date: 24/03/19 - 23:46
 */
public abstract class PropertyMappingSupport implements PropertyMapping {

  private Function<Object,Object> getterFunction;
  private BiConsumer<Object,Object> setterFunction;

  protected void setValueOn(Object target, Object convertedValue, Bean2beanTask task) {
    try {
      setterFunction.accept(target, convertedValue);
    } catch (Exception e) {
      ObjectDescriptor objectDescriptor = ObjectDescriptor.create();
      String valueDescription = objectDescriptor.describeInstance(convertedValue);
      String targetDescription = objectDescriptor.describeInstance(target);
      throw new MappingException("Failed to set value["+valueDescription+"] onto ["+targetDescription+"] using setter: " + setterFunction, task, e);
    }
  }

  protected Object getValueFrom(Object source, Bean2beanTask task) {
    try {
      return getterFunction.apply(source);
    } catch (Exception e) {
      String sourceDescription = ObjectDescriptor.create().describeInstance(source);
      throw new MappingException("Failed to get value from ["+sourceDescription+"] using getter: " + getterFunction, task, e);
    }
  }

  public void setGetterFunction(Function<?, ?> getterFunction) {
    this.getterFunction = (Function<Object, Object>) getterFunction;
  }

  public void setSetterFunction(BiConsumer<?, ?> setterFunction) {
    this.setterFunction = (BiConsumer<Object, Object>) setterFunction;
  }
}
