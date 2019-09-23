package info.kfgodel.bean2bean.v3.dsl.api.mapper;

import java.util.function.BiConsumer;

/**
 * This type defines the operations to complete a mapping definition
 * Date: 24/03/19 - 23:34
 */
public interface TargetDefinerMappingDsl<I, V, O> {
  /**
   * Defines the destination for the value to be assigned on (through a setter lambda).<br>
   *   This method completes the mapping and returns the mapper for more mapping definitions
   * @param setter The setter lambda
   * @return The mapper to continue defining a new mapping
   */
  MapperDsl<I, O> setInto(BiConsumer<O, V> setter);
}
