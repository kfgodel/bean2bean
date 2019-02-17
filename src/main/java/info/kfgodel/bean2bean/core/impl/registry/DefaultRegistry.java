package info.kfgodel.bean2bean.core.impl.registry;

import info.kfgodel.bean2bean.core.api.registry.Bean2BeanRegistry;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;
import info.kfgodel.bean2bean.core.impl.registry.definitions.ConverterDefinition;
import info.kfgodel.bean2bean.other.TypeVector;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * This type implements the default bean2bean registry
 * Date: 12/02/19 - 00:30
 */
public class DefaultRegistry implements Bean2BeanRegistry {

  private Map<DomainVector, Function> convertersByVector;

  public static DefaultRegistry create() {
    DefaultRegistry registry = new DefaultRegistry();
    registry.convertersByVector = new HashMap<>();
    return registry;
  }

  @Override
  public <O> Optional<Function<ObjectConversion, O>> findBestConverterFor(ObjectConversion input) {
    TypeVector typeVector = input.getConversionVector();
    DomainVector vector = typeVector.getDomains();
    Optional<Function<ObjectConversion, O>> found = Optional.ofNullable(convertersByVector.get(vector));
    return found;
  }

  @Override
  public Bean2BeanRegistry store(ConverterDefinition definition) {
    TypeVector implicitVector = definition.getConversionVector();
    Function<ObjectConversion, Object> converter = definition.getConverter();
    DomainVector vector = implicitVector.getDomains();
    this.convertersByVector.put(vector, converter);
    return this;
  }
}
