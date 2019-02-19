package info.kfgodel.bean2bean.core.impl.registry;

import info.kfgodel.bean2bean.core.api.registry.Bean2BeanRegistry;
import info.kfgodel.bean2bean.core.api.registry.ConverterDefinition;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * This type implements the default bean2bean registry
 * Date: 12/02/19 - 00:30
 */
public class DefaultRegistry implements Bean2BeanRegistry {

  private Map<DomainVector, ConverterDefinition> convertersByVector;

  public static DefaultRegistry create() {
    DefaultRegistry registry = new DefaultRegistry();
    registry.convertersByVector = new HashMap<>();
    return registry;
  }

  @Override
  public <O> Optional<Function<ObjectConversion, O>> findBestConverterFor(DomainVector vector) {
    Optional<ConverterDefinition> foundDefinition = lookupConverterFor(vector);
    return foundDefinition
      .map(ConverterDefinition::getConverter);
  }

  private Optional<ConverterDefinition> lookupConverterFor(DomainVector vector) {
    return findExactConverterFor(vector);
  }

  private Optional<ConverterDefinition> findExactConverterFor(DomainVector vector) {
    return Optional.ofNullable(convertersByVector.get(vector));
  }

  @Override
  public Bean2BeanRegistry store(ConverterDefinition definition) {
    DomainVector implicitVector = definition.getConversionVector();
    this.convertersByVector.put(implicitVector, definition);
    return this;
  }
}
