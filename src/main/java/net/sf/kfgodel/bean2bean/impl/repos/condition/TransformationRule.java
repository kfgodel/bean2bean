package net.sf.kfgodel.bean2bean.impl.repos.condition;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This type represents a transformation indication that binds a condition and a transfunction to be appliable only
 * on certain conditions
 *
 * Created by kfgodel on 16/07/16.
 */
public class TransformationRule {

  private Predicate<TransformationIntention> condition;
  private Function<?,?> transfunction;

  public Predicate<TransformationIntention> getCondition() {
    return condition;
  }

  public Function<?, ?> getTransfunction() {
    return transfunction;
  }

  public static TransformationRule create(Predicate<TransformationIntention> condition, Function<?,?> transfunction) {
    TransformationRule rule = new TransformationRule();
    rule.condition = condition;
    rule.transfunction = transfunction;
    return rule;
  }

  public boolean isApplicableTo(TransformationIntention transformationIntention) {
    return condition.test(transformationIntention);
  }
}
