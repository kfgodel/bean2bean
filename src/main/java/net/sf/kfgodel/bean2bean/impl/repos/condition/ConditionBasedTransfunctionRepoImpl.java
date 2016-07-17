package net.sf.kfgodel.bean2bean.impl.repos.condition;

import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.api.optionals.Optional;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * Created by kfgodel on 16/07/16.
 */
public class ConditionBasedTransfunctionRepoImpl implements ConditionBasedTransfunctionRepo {

  private List<TransformationRule> rules;

  public static ConditionBasedTransfunctionRepoImpl create() {
    ConditionBasedTransfunctionRepoImpl repo = new ConditionBasedTransfunctionRepoImpl();
    repo.rules = new LinkedList<>();
    return repo;
  }

  @Override
  public <O> Optional<Function<TransformationIntention, O>> findTransfunctionFor(TransformationIntention transformationIntention) {
    return (Optional)Nary.create(rules)
      .filterNary((rule)-> rule.isApplicableTo(transformationIntention))
      .mapNary((rule)-> rule.getTransfunction())
      .findFirstNary();
  }

  @Override
  public ConditionBasedTransfunctionRepo store(Predicate<TransformationIntention> condition, Function<TransformationIntention, ?> transfunction) {
    TransformationRule newRule = TransformationRule.create(condition, transfunction);
    this.rules.add(newRule);
    return this;
  }
}
