package net.sf.kfgodel.bean2bean.impl.engine.impl.rules;

import net.sf.kfgodel.bean2bean.impl.engine.EngineContext;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingRepository;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingVector;
import net.sf.kfgodel.bean2bean.impl.transformations.TransformationRule;

import java.util.Optional;
import java.util.function.Function;

/**
 * This type represents the rule that uses the previously stored transformation under the current mapping vector
 * Created by kfgodel on 11/02/15.
 */
public class UsePredefinedVectorRule implements TransformationRule<EngineContext> {
    
    @Override
    public <R> Optional<Function<EngineContext, R>> getTransformationIfApplicableTo(EngineContext context) {
        MappingRepository mappingRepo = context.getMappingRepository();
        MappingVector mappingVector = context.getTransformationVector();
        return mappingRepo.getFor(mappingVector);
    }

    public static UsePredefinedVectorRule create() {
        UsePredefinedVectorRule rule = new UsePredefinedVectorRule();
        return rule;
    }

}
