package net.sf.kfgodel.bean2bean.impl.transformations;

import java.util.Optional;
import java.util.function.Function;

/**
 * This type represents a rule to transform an object using a condition to limit its usage
 * Created by kfgodel on 04/02/15.
 */
public interface TransformationRule<T> {

    /**
     * Returns the transformation that's applicable to the given object (only if it's applicable)
     * @param anObject The object to transform
     * @param <R> The type of transformation result
     * @return The transformation or empty if not applicable according to this rule
     */
    <R> Optional<Function<T,R>> getTransformationIfApplicableTo(T anObject);
}
