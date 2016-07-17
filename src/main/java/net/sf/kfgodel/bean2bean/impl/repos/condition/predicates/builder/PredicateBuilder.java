package net.sf.kfgodel.bean2bean.impl.repos.condition.predicates.builder;

import java.util.function.Predicate;

/**
 * This type helps on building predicates without knowing each specific predicate subclass
 * Created by kfgodel on 17/07/16.
 */
public class PredicateBuilder {

  public static PredicateBuilder where() {
    PredicateBuilder builder = new PredicateBuilder();
    return builder;
  }

  public SourceConditionDefined source(Predicate<Object> sourceCondition) {
    return SourceConditionDefined.create(sourceCondition);
  }
}
