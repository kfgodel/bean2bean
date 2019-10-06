package info.kfgodel.bean2bean.v3.converters.mapping;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * This class represents a single mapping from a property in one object to a property in another.<br>
 *   This mapping doesn't require type conversion. The source property value can be assigned directly to destination
 *   property
 *
 * Date: 24/03/19 - 21:21
 */
public class DirectPropertyMapping extends PropertyMappingSupport {

  @Override
  public void applyOn(Object source, Bean2beanTask task, Object target){
    Object propertyValue = getValueFrom(source, task);
    setValueOn(target, propertyValue, task);
  }

  public static<I,O,T> PropertyMapping create(Function<I, T> getterFunction, BiConsumer<O, T> setterFunction) {
    DirectPropertyMapping mapping = new DirectPropertyMapping();
    mapping.setGetterFunction(getterFunction);
    mapping.setSetterFunction(setterFunction);
    return mapping;
  }

}
