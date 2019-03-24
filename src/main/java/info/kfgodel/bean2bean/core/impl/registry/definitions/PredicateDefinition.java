package info.kfgodel.bean2bean.core.impl.registry.definitions;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.api.registry.definitions.PredicateScopedDefinition;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This class is default impl for predicate based definitions
 * Date: 19/02/19 - 22:31
 */
public class PredicateDefinition implements PredicateScopedDefinition {

  private Predicate<DomainVector> predicate;
  private Function<Bean2beanTask, Object> converter;

  @Override
  public Predicate<DomainVector> getScopePredicate() {
    return predicate;
  }

  @Override
  public Function<Bean2beanTask, Object> getConverter() {
    return converter;
  }

  @Override
  public String toString() {
    return "PredicateDefinition{" +
      "predicate=" + predicate +
      ", converter=" + converter +
      '}';
  }

  public static PredicateDefinition create(Function<Bean2beanTask, Object> converter, Predicate<DomainVector> predicate) {
    PredicateDefinition definition = new PredicateDefinition();
    definition.predicate = predicate;
    definition.converter = converter;
    return definition;
  }

}
