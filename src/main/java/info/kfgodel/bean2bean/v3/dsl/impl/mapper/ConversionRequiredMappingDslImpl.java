package info.kfgodel.bean2bean.v3.dsl.impl.mapper;

import info.kfgodel.bean2bean.v3.converters.mapping.ConvertedPropertyMapping;
import info.kfgodel.bean2bean.v3.converters.mapping.PropertyMapping;
import info.kfgodel.bean2bean.v3.dsl.api.mapper.MapperDsl;
import info.kfgodel.bean2bean.v3.dsl.api.mapper.TargetDefinerMappingDsl;

import java.lang.reflect.Type;
import java.util.function.BiConsumer;

/**
 * This class implements mapping dsl once a conversion is defined
 * Date: 24/03/19 - 23:57
 */
public class ConversionRequiredMappingDslImpl<I, W, O> implements TargetDefinerMappingDsl<I, W, O> {

  private Type expectedType;
  private SourceDefinedMappingDslImpl<I, ?, O> parentDsl;

  @Override
  public MapperDsl<I, O> setInto(BiConsumer<O, W> setter) {
    PropertyMapping mapping = ConvertedPropertyMapping.create(parentDsl.getGetter(), expectedType, setter);
    return parentDsl.addMapping(mapping);
  }

  public static<I, W, O> ConversionRequiredMappingDslImpl<I, W, O> create(Type expectedType, SourceDefinedMappingDslImpl<I, ?, O> parentDsl) {
    ConversionRequiredMappingDslImpl<I, W, O> mappingDsl = new ConversionRequiredMappingDslImpl<>();
    mappingDsl.expectedType = expectedType;
    mappingDsl.parentDsl = parentDsl;
    return mappingDsl;
  }

}
