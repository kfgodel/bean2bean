package net.sf.kfgodel.bean2bean.impl.transformations;

import java.util.function.Function;

/**
 * This type represents a repository of plans that are
 * Created by kfgodel on 04/02/15.
 */
public interface TransformationRepository<T> {

    /**
     * Based on the stored rules picks the first transformation whose condition matches the object and returns it
     * @param anObject The object to transform
     * @param <T> The type of expected transformation input
     * @return The transformation that best matches according to rules
     */
    <R> Function<T, R> getBestTransformationFor(T anObject);


    /**
     * Adds a new transformation rule to this repository
     * @param aRule The rule to add after the previous ones
     */
    void store(TransformationRule<T> aRule);
}
