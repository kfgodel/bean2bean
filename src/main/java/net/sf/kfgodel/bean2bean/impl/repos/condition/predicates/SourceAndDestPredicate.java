package net.sf.kfgodel.bean2bean.impl.repos.condition.predicates;

import com.google.common.base.MoreObjects;
import net.sf.kfgodel.bean2bean.impl.repos.condition.TransformationIntention;

import java.util.function.Predicate;

/**
 * This type represents a two part predicate. One conditions the source, and the other the destination
 * Created by kfgodel on 17/07/16.
 */
public class SourceAndDestPredicate implements Predicate<TransformationIntention> {

  private Predicate<Object> sourceCondition;
  public static final String sourceCondition_FIELD = "sourceCondition";

  private Predicate<Object> destinationCondition;
  public static final String destinationCondition_FIELD = "destinationCondition";


  @Override
  public boolean test(TransformationIntention transformationIntention) {
    return sourceCondition.test(transformationIntention.source()) && destinationCondition.test(transformationIntention.destination());
  }

  public static SourceAndDestPredicate create(Predicate<Object> sourceCondition, Predicate<Object> destinationCondition) {
    SourceAndDestPredicate predicate = new SourceAndDestPredicate();
    predicate.sourceCondition = sourceCondition;
    predicate.destinationCondition = destinationCondition;
    return predicate;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add(sourceCondition_FIELD, sourceCondition)
      .add(destinationCondition_FIELD, destinationCondition)
      .toString();
  }

}
