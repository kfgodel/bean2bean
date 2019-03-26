package info.kfgodel.bean2bean.dsl.impl.mapper;

import info.kfgodel.bean2bean.converters.mapping.DirectPropertyMapping;
import info.kfgodel.bean2bean.converters.mapping.PropertyMapping;
import info.kfgodel.bean2bean.dsl.api.mapper.MapperDsl;
import info.kfgodel.bean2bean.dsl.api.mapper.SourceDefinedMappingDsl;
import info.kfgodel.bean2bean.dsl.api.mapper.TargetDefinerMappingDsl;
import info.kfgodel.bean2bean.other.references.TypeRef;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * This class implements the partial mapping dsl
 * Date: 24/03/19 - 23:41
 */
public class SourceDefinedMappingDslImpl<I, V, O> implements SourceDefinedMappingDsl<I, V, O> {

  private Function<I,V> getter;
  private MapperDslImpl<I, O> parentDsl;

  @Override
  public <W> TargetDefinerMappingDsl<I, W, O> convertTo(Class<W> targetType) {
    return ConversionRequiredMappingDslImpl.create(targetType, this);
  }

  @Override
  public <W> TargetDefinerMappingDsl<I, W, O> convertTo(TypeRef<W> targetTypeRef) {
    return ConversionRequiredMappingDslImpl.create(targetTypeRef.getReference(), this);
  }

  @Override
  public MapperDsl<I, O> setInto(BiConsumer<O, V> setter) {
    PropertyMapping mapping = DirectPropertyMapping.create(getter, setter);
    return addMapping(mapping);
  }

  public Function<I, V> getGetter() {
    return getter;
  }

  public MapperDsl<I, O> addMapping(PropertyMapping mapping) {
    parentDsl.addMapping(mapping);
    return parentDsl;
  }

  public static<I,V,O> SourceDefinedMappingDslImpl<I,V,O> create(Function<I, V> getter, MapperDslImpl<I,O> parentDsl) {
    SourceDefinedMappingDslImpl<I,V,O> mappingDsl = new SourceDefinedMappingDslImpl<>();
    mappingDsl.getter = getter;
    mappingDsl.parentDsl = parentDsl;
    return mappingDsl;
  }

}
