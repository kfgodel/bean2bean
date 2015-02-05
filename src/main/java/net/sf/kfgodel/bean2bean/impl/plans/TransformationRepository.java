package net.sf.kfgodel.bean2bean.impl.plans;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This type represents a repository of plans that are
 * Created by kfgodel on 04/02/15.
 */
public interface TransformationRepository<T> {

    /**
     * Stores in this repo the transformation to be used only under the given condition.<br>
     *     This transformation will be added after previous ones, so it has the least precedence
     * @param aCondition The condition that must be true to use the transformation
     * @param aTransformation The transformation to use if the condition is true
     * @return The stored rule that represents the union of the transformation and the condition
     */
    TransformationRule<T> storeUnder(Predicate<? super T> aCondition, Function<? extends T, ?> aTransformation);

    /**
     * Based on the stored rules picks the first transformation whose condition matches the object and returns it
     * @param anObject The object to transform
     * @param <T> The type of expected transformation input
     * @return The transformation that best matches according to rules
     */
    <R> Function<T, R> getBestTransformationFor(T anObject);
}
