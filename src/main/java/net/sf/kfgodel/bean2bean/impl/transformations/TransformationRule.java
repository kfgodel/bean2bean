package net.sf.kfgodel.bean2bean.impl.transformations;

import java.util.function.Function;

/**
 * This type represents a rule to transform an object using a condition to limit its usage
 * Created by kfgodel on 04/02/15.
 */
public interface TransformationRule<T> {
    /**
     * Indicates if this rule is applicable to the given object according to the condition
     * @param anObject The object to test
     * @return True if this rule can be used
     */
    boolean isApplicableTo(T anObject);

    /**
     * @return The transformation on this rule
     */
    <R> Function<T,R> getTransformation();
}
