package net.sf.kfgodel.bean2bean.impl.repos.condition.predicates.builder;

import net.sf.kfgodel.bean2bean.impl.repos.condition.TransformationIntention;
import net.sf.kfgodel.bean2bean.impl.repos.condition.predicates.SourceAndDestPredicate;

import java.util.function.Predicate;

/**
 * Created by kfgodel on 17/07/16.
 */
public class SourceConditionDefined {

  private Predicate<Object> sourceCondition;

  public static SourceConditionDefined create(Predicate<Object> sourceCondition) {
    SourceConditionDefined firstPart = new SourceConditionDefined();
    firstPart.sourceCondition = sourceCondition;
    return firstPart;
  }

  public Predicate<TransformationIntention> andDestination(Predicate<Object> destinationCondition) {
    return SourceAndDestPredicate.create(sourceCondition, destinationCondition);
  }

}
