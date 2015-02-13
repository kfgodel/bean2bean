package net.sf.kfgodel.bean2bean.impl.engine.impl;

import net.sf.kfgodel.bean2bean.api.exceptions.Bean2beanException;
import net.sf.kfgodel.bean2bean.impl.engine.EngineContext;
import net.sf.kfgodel.bean2bean.impl.engine.TransformationEngine;
import net.sf.kfgodel.bean2bean.impl.engine.impl.rules.FailTransformationRule;
import net.sf.kfgodel.bean2bean.impl.engine.impl.rules.SearchByAbstractionVectorRule;
import net.sf.kfgodel.bean2bean.impl.transformations.RuleRepository;
import net.sf.kfgodel.bean2bean.impl.transformations.impl.SequentialRuleRepository;

import java.util.function.Function;

/**
 * This type implements the transformation engine
 * Created by kfgodel on 10/02/15.
 */
public class TransformationEngineImpl implements TransformationEngine {

    private RuleRepository<EngineContext> ruleRepository;
    
    public static TransformationEngineImpl create() {
        TransformationEngineImpl engine = new TransformationEngineImpl();
        engine.ruleRepository = SequentialRuleRepository.create();
        engine.initialize();
        return engine;
    }

    /**
     * Creates the basic rules for this engine to work
     */
    private void initialize() {
        this.ruleRepository.store(SearchByAbstractionVectorRule.create());
        //After all the other rules, then fail with an exception
        this.ruleRepository.store(FailTransformationRule.create());
    }

    @Override
    public <R> Function<EngineContext, R> getBestTransformationFor(EngineContext aContext) throws Bean2beanException {
        return ruleRepository.getBestTransformationFor(aContext);
    }
}
