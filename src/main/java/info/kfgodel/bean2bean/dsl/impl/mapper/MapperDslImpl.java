package info.kfgodel.bean2bean.dsl.impl.mapper;

import info.kfgodel.bean2bean.converters.mapping.MappingConverter;
import info.kfgodel.bean2bean.converters.mapping.PropertyMapping;
import info.kfgodel.bean2bean.dsl.api.mapper.MapperDsl;
import info.kfgodel.bean2bean.dsl.api.mapper.SourceDefinedMappingDsl;
import info.kfgodel.bean2bean.dsl.impl.scopes.ParameterizedScopeDslImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * This class implements the mapper interface for a mapping dsl
 * Date: 24/03/19 - 23:38
 */
public class MapperDslImpl<I,O> implements MapperDsl<I,O> {

  private List<PropertyMapping> mappings;
  private ParameterizedScopeDslImpl<I, O> parentDsl;

  @Override
  public <V> SourceDefinedMappingDsl<I, V, O> getFrom(Function<I, V> getter) {
    return SourceDefinedMappingDslImpl.create(getter, this);
  }

  public static<I,O> MapperDslImpl<I,O> create(ParameterizedScopeDslImpl<I, O> parentDsl) {
    MapperDslImpl<I,O> mapper = new MapperDslImpl<>();
    mapper.parentDsl = parentDsl;
    mapper.mappings = new ArrayList<>();
    return mapper;
  }

  public MapperDsl<I,O> addMapping(PropertyMapping mapping) {
    this.mappings.add(mapping);
    return this;
  }

  public MappingConverter<I, O> buildConverter() {
    MappingConverter<I, O> converter = MappingConverter.create();
    mappings.forEach(converter::addMapping);
    return converter;
  }
}
