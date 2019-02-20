package info.kfgodel.bean2bean.core.impl.registry;

import info.kfgodel.bean2bean.core.api.registry.Bean2BeanRegistry;
import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.api.registry.definitions.ConverterDefinition;
import info.kfgodel.bean2bean.core.api.registry.definitions.PredicateBasedDefinition;
import info.kfgodel.bean2bean.core.api.registry.definitions.VectorBasedDefinition;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * This type implements the default bean2bean registry
 * Date: 12/02/19 - 00:30
 */
public class DefaultRegistry implements Bean2BeanRegistry {

  private Map<DomainVector, ConverterDefinition> convertersByVector;
  private List<PredicateBasedDefinition> predicateDefinitions;
  private LookupCache cache;

  public static DefaultRegistry create() {
    DefaultRegistry registry = new DefaultRegistry();
    registry.convertersByVector = new HashMap<>();
    registry.predicateDefinitions = new ArrayList<>();
    registry.cache = LookupCache.create();
    return registry;
  }

  @Override
  public <O> Optional<Function<ObjectConversion, O>> findBestConverterFor(DomainVector vector) {
    return this.cache.retrieveCachedOrProduceFor(vector, this::calculateBestConverterFor);
  }

  private <O> Optional<Function<ObjectConversion, O>> calculateBestConverterFor(DomainVector vector) {
    Optional<ConverterDefinition> foundDefinition = this.lookForVectorBasedMatchingHiearchiesOf(vector);
    if(!foundDefinition.isPresent()){
      // I can't find a better way to express this with native optionals
      foundDefinition = this.lookForPredicateBasedMatching(vector);
    }
    return foundDefinition
      .map(ConverterDefinition::getConverter);
  }

  private Optional<ConverterDefinition> lookForVectorBasedMatchingHiearchiesOf(DomainVector conversionVector) {
    Iterator<Domain> targetDomainHierarchy = conversionVector.getTarget().getHierarchy().iterator();
    while(targetDomainHierarchy.hasNext()){
      Domain targetDomain = targetDomainHierarchy.next();

      Iterator<Domain> sourceDomainHierarchy = conversionVector.getSource().getHierarchy().iterator();
      while(sourceDomainHierarchy.hasNext()){
        Domain sourceDomain = sourceDomainHierarchy.next();

        DomainVector exploredVector = DomainVector.create(sourceDomain, targetDomain);
        Optional<ConverterDefinition> found = findExactConverterFor(exploredVector);
        if(found.isPresent()){
          return found;
        }
      }
    }
    return Optional.empty();
  }

  private Optional<ConverterDefinition> lookForPredicateBasedMatching(DomainVector vector) {
    return this.predicateDefinitions.stream()
      .filter((definition)-> definition.getScopePredicate().test(vector))
      .map(ConverterDefinition.class::cast)
      .findFirst();
  }


  private Optional<ConverterDefinition> findExactConverterFor(DomainVector vector) {
    return Optional.ofNullable(convertersByVector.get(vector));
  }

  @Override
  public Bean2BeanRegistry store(VectorBasedDefinition definition) {
    DomainVector implicitVector = definition.getConversionVector();
    this.convertersByVector.put(implicitVector, definition);
    this.cache.invalidate();
    return this;
  }

  @Override
  public Bean2BeanRegistry store(PredicateBasedDefinition definition) {
    this.predicateDefinitions.add(definition);
    this.cache.invalidate();
    return this;
  }
}
