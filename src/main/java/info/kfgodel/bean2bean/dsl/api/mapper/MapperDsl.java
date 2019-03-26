package info.kfgodel.bean2bean.dsl.api.mapper;

import java.util.function.Function;

/**
 * This type defines the mapping dsl operations available to move property values from one object to another
 * Date: 24/03/19 - 23:05
 */
public interface MapperDsl<I,O> {

  /**
   * Defines the getter lambda to use on the source object to get a single property value.<br>
   *   After calling this method a destination for the value needs to be defined
   * @param getter The lambda getter to use on the source object
   * @param <V> The type of value
   * @return The partially defined mapping
   */
  <V> SourceDefinedMappingDsl<I,V,O> getFrom(Function<I,V> getter);
}
