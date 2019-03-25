package info.kfgodel.bean2bean.dsl.api.mapper;

import info.kfgodel.bean2bean.other.references.TypeRef;

/**
 * This type represents the operation available on a mapping once the source getter is defined
 * Date: 24/03/19 - 23:14
 */
public interface SourceDefinedMappingDsl<I, V, O> extends TargetDefinerMappingDsl<I, V, O> {

  /**
   * Adds an intermediate step where a conversion is needed to transform the source property value
   * into an expected type before assigning it to the destination property
   * @param targetType The class instance that defines the type of the destination property
   * @param <W> The type of the resulting conversion
   * @return The partially defined mapping to be completed
   */
  <W> TargetDefinerMappingDsl<I,W,O> convertTo(Class<W> targetType);

  /**
   * Adds an intermediate step where a conversion is needed to transform the source property value
   * into an expected type before assigning it to the destination property
   * @param targetTypeRef The reference to the type of the destination property
   * @param <W> The type of the resulting conversion
   * @return The partially defined mapping to be completed
   */
  <W> TargetDefinerMappingDsl<I,W,O> convertTo(TypeRef<W> targetTypeRef);
}
