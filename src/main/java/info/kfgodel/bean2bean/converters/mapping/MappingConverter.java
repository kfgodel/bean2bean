package info.kfgodel.bean2bean.converters.mapping;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.other.references.TypeRef;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This class represents a generic converter that maps properties from the source object to a destination instance
 * created out of the target type
 *
 * Date: 24/03/19 - 21:20
 */
public class MappingConverter<I,O> implements BiFunction<I, Bean2beanTask, O> {

  private List<PropertyMapping> mappings;

  @Override
  public O apply(I source, Bean2beanTask task) {
    Object target = task.getDsl().generate().anInstanceOf(task.getTargetType());
    mappings.forEach(mapping->{
      mapping.applyOn(source,task, target);
    });
    return (O) target;
  }

  public static<I,O> MappingConverter<I,O> create() {
    MappingConverter<I,O> converter = new MappingConverter<>();
    converter.mappings = new ArrayList<>();
    return converter;
  }

  public<T> MappingConverter<I,O> withMapping(Function<I,T> getter, BiConsumer<O,T> setter) {
    this.addMapping(DirectPropertyMapping.create(getter, setter));
    return this;
  }

  public<T,U> MappingConverter<I,O> withMapping(Function<I,T> getter, Class<U> expectedType, BiConsumer<O,U> setter) {
    this.addMapping(ConvertedPropertyMapping.create(getter, expectedType, setter));
    return this;
  }

  public<T,U> MappingConverter<I,O> withMapping(Function<I,T> getter, TypeRef<U> typeRef, BiConsumer<O,U> setter) {
    this.addMapping(ConvertedPropertyMapping.create(getter, typeRef.getReference(), setter));
    return this;
  }

  public void addMapping(PropertyMapping aMapping) {
    this.mappings.add(aMapping);
  }


}
