package net.sf.kfgodel.bean2bean.impl.repos.condition;

import ar.com.kfgodel.nary.api.optionals.Optional;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This type represents a transfunction repo based on conditions applied over a transformation intention to detect
 * the best transfunction
 *
 * Created by kfgodel on 16/07/16.
 */
public interface ConditionBasedTransfunctionRepo {

  /**
   * Looks for a transfunction on this repo whose condition matches the intention.<br>
   *   Returns the first transfunction whose condition is true
   *
   * @param transformationIntention the intention that describes the expected transformation
   * @param <I> The type of input object
   * @param <O> The type od output object
   * @return The optional transfunction if found. Empty if none match the intention
   */
  <O> Optional<Function<TransformationIntention,O>> findTransfunctionFor(TransformationIntention transformationIntention);

  /**
   * Stores on this repo the given transfunction, conditioned to the given predicate.
   * The transfunction will only be selected if the predicate is true for a given intention
   * @param condition The condition that determines when is the transfunction applicable
   * @param transfunction The transformation function to use for the matching intention
   * @return This instance to chain declaration
   */
  ConditionBasedTransfunctionRepo store(Predicate<TransformationIntention> condition, Function<TransformationIntention,?> transfunction);
}
