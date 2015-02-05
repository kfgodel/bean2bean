package net.sf.kfgodel.bean2bean.impl.plans.impl;

import net.sf.kfgodel.bean2bean.impl.plans.TransformationRepository;
import net.sf.kfgodel.bean2bean.impl.plans.TransformationRule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * This type implements teh transformation repository as a sequential list of rules
 * Created by kfgodel on 05/02/15.
 */
public class SequentialRuleRepository<T> implements TransformationRepository<T> {
    
    private List<TransformationRule<T>> rules;
    
    @Override
    public TransformationRule<T> storeUnder(Predicate<? super T> aCondition, Function<? extends T, ?> aTransformation) {
        TransformationRuleImpl<T> createdRule = TransformationRuleImpl.create(aCondition, aTransformation);
        this.rules.add(createdRule);
        return createdRule;
    }

    @Override
    public <R> Function<T, R> getBestTransformationFor(T anObject) {
        for (TransformationRule<T> rule : rules) {
            if(rule.isApplicableTo(anObject)){
                return rule.getTransformation();
            }
        }
        if(rules.isEmpty()){
            // Different error to help detecting the cause
            throw new EmptyRepositoryException("Cannot get transformation for object["+anObject+"] if repository is empty");
        }
        throw new NoTransformationMatchesException("There's no applicable transformation for object["+anObject+"]");
    }

    public static<T> SequentialRuleRepository<T> create() {
        SequentialRuleRepository<T> repository = new SequentialRuleRepository<>();
        repository.rules = new ArrayList<>();
        return repository;
    }

}
