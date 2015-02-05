package net.sf.kfgodel.bean2bean.impl.transformations.impl;

import net.sf.kfgodel.bean2bean.impl.transformations.TransformationRepository;
import net.sf.kfgodel.bean2bean.impl.transformations.TransformationRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * This type implements teh transformation repository as a sequential list of rules
 * Created by kfgodel on 05/02/15.
 */
public class SequentialRuleRepository<T> implements TransformationRepository<T> {
    
    private List<TransformationRule<T>> rules;
    
    @Override
    public void store(TransformationRule<T> aRule) {
        this.rules.add(aRule);
    }

    @Override
    public <R> Function<T, R> getBestTransformationFor(T anObject) {
        for (TransformationRule<T> rule : rules) {
            Optional<Function<T, R>> transformation = rule.getTransformationIfApplicableTo(anObject);
            if(transformation.isPresent()){
                return transformation.get();
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
