package info.kfgodel.bean2bean.core.impl.registry;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.Bean2BeanRegistry;
import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.api.registry.definitions.ConverterDefinition;
import info.kfgodel.bean2bean.core.api.registry.definitions.PredicateScopedDefinition;
import info.kfgodel.bean2bean.core.api.registry.definitions.VectorScopedDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  public static Logger LOG = LoggerFactory.getLogger(DefaultRegistry.class);

  private Map<DomainVector, ConverterDefinition> convertersByVector;
  private List<PredicateScopedDefinition> predicateDefinitions;
  private LookupCache cache;

  public static DefaultRegistry create() {
    DefaultRegistry registry = new DefaultRegistry();
    registry.convertersByVector = new HashMap<>();
    registry.predicateDefinitions = new ArrayList<>();
    registry.cache = LookupCache.create();
    return registry;
  }

  @Override
  public <O> Optional<Function<Bean2beanTask, O>> findBestConverterFor(DomainVector vector) {
    return this.cache.retrieveCachedOrProduceFor(vector, this::calculateBestConverterFor);
  }

  @Override
  public Bean2BeanRegistry store(ConverterDefinition definition) {
    if(definition instanceof VectorScopedDefinition){
      return this.store((VectorScopedDefinition)definition);
    } else if(definition instanceof PredicateScopedDefinition){
      return this.store((PredicateScopedDefinition) definition);
    }
    throw new IllegalArgumentException("The given definition["+definition+"] is not supported on this registry");
  }

  private <O> Optional<Function<Bean2beanTask, O>> calculateBestConverterFor(DomainVector vector) {
    Optional<ConverterDefinition> foundDefinition = this.lookForVectorBasedMatchingHierarchiesOf(vector);
    if (!foundDefinition.isPresent()) {
      // I can't find a better way to express this with native optionals
      foundDefinition = this.lookForPredicateBasedMatching(vector);
    }
    foundDefinition.ifPresent(converterDefinition -> LOG.debug("Found for[{}]: {}", vector, converterDefinition));
    return foundDefinition
      .map(ConverterDefinition::getConverter);
  }

  private Optional<ConverterDefinition> lookForVectorBasedMatchingHierarchiesOf(DomainVector conversionVector) {
    Iterator<Domain> targetDomainHierarchy = conversionVector.getTarget().getHierarchy().iterator();
    while (targetDomainHierarchy.hasNext()) {
      Domain targetDomain = targetDomainHierarchy.next();

      Iterator<Domain> sourceDomainHierarchy = conversionVector.getSource().getHierarchy().iterator();
      while (sourceDomainHierarchy.hasNext()) {
        Domain sourceDomain = sourceDomainHierarchy.next();

        DomainVector exploredVector = DomainVector.create(sourceDomain, targetDomain);
        Optional<ConverterDefinition> found = findExactConverterFor(exploredVector);
        if (found.isPresent()) {
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
  public Bean2BeanRegistry store(VectorScopedDefinition definition) {
    DomainVector implicitVector = definition.getConversionVector();
    this.convertersByVector.put(implicitVector, definition);
    this.cache.invalidate();
    return this;
  }

  @Override
  public Bean2BeanRegistry store(PredicateScopedDefinition definition) {
    this.predicateDefinitions.add(definition);
    this.cache.invalidate();
    return this;
  }
}
