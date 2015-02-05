package net.sf.kfgodel.bean2bean.impl.transformations.impl;

import net.sf.kfgodel.bean2bean.impl.transformations.TransformationRule;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This type represents a rule for transforming objects
 * Created by kfgodel on 05/02/15.
 */
public class TransformationRuleImpl<T> implements TransformationRule<T> {


    private Predicate<? super T> condition;
    private Function<? extends T, ?> transformation;

    public static<T> TransformationRuleImpl<T> create(Predicate<? super T> condition, Function<? extends T, ?> transformation) {
        TransformationRuleImpl<T> rule = new TransformationRuleImpl<>();
        rule.condition = condition;
        rule.transformation = transformation;
        return rule;
    }

    @Override
    public boolean isApplicableTo(T anObject) {
        return condition.test(anObject);
    }

    @Override
    public <R> Function<T, R> getTransformation() {
        return (Function<T, R>) this.transformation;
    }
}
