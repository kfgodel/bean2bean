package info.kfgodel.bean2bean.core.impl.registry.definitions;

import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.api.registry.definitions.PredicateBasedDefinition;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This class is default impl for predicate based definitions
 * Date: 19/02/19 - 22:31
 */
public class PredicateDefinition implements PredicateBasedDefinition {

  private Predicate<DomainVector> predicate;
  private Function<ObjectConversion, Object> converter;

  @Override
  public Predicate<DomainVector> getScopePredicate() {
    return predicate;
  }

  @Override
  public Function<ObjectConversion, Object> getConverter() {
    return converter;
  }

  public static PredicateDefinition create(Function<ObjectConversion, Object> converter, Predicate<DomainVector> predicate) {
    PredicateDefinition definition = new PredicateDefinition();
    definition.predicate = predicate;
    definition.converter = converter;
    return definition;
  }

}
