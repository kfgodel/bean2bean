package net.sf.kfgodel.bean2bean.impl.transformations.impl;

import net.sf.kfgodel.bean2bean.impl.transformations.TransformationRule;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This type represents a rule that checks a condition to verify if a transformation is applicable
 * Created by kfgodel on 05/02/15.
 */
public class ConditionedTransformationRule<T> implements TransformationRule<T> {

    private Predicate<? super T> condition;
    private Function<? extends T, ?> transformation;

    public static<T, S extends T> ConditionedTransformationRule<T> create(Predicate<? super T> condition, Function<S, ?> transformation) {
        ConditionedTransformationRule<T> rule = new ConditionedTransformationRule<>();
        rule.condition = condition;
        rule.transformation = transformation;
        return rule;
    }

    @Override
    public <R> Optional<Function<T, R>> getTransformationIfApplicableTo(T anObject) {
        if(condition.test(anObject)){
            return Optional.of((Function<T, R>)transformation);
        }
        return Optional.empty();
    }
}
