package net.sf.kfgodel.bean2bean.impl.engine.impl.rules;

import net.sf.kfgodel.bean2bean.api.exceptions.Bean2beanException;
import net.sf.kfgodel.bean2bean.impl.engine.EngineContext;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingVector;
import net.sf.kfgodel.bean2bean.impl.transformations.TransformationRule;

import java.util.Optional;
import java.util.function.Function;

/**
 * This type represents the rule that's applied when no other rule matches the transformation and it's used to create
 * a descriptive error
 * Created by kfgodel on 10/02/15.
 */
public class FailTransformationRule implements TransformationRule<EngineContext> {
    
    @Override
    public <R> Optional<Function<EngineContext, R>> getTransformationIfApplicableTo(EngineContext context) {
        MappingVector vector = context.getTransformationVector();
        Object sourceRepresentation = represent(vector.getSourceObject());
        Object destinationRepresentation = represent(vector.getDestinationObject());
        throw new Bean2beanException("There's no transformation defined from["+sourceRepresentation+"] to["+destinationRepresentation+"]");
    }

    /**
     * Creates a slightly modified representation in order to add extra information for the failed conversion
     * @param sourceObject The object to represent
     * @return The representation with extra information
     */
    private String represent(Object sourceObject) {
        String representation = String.valueOf(sourceObject);
        if(sourceObject instanceof CharSequence){
            //Add quotes to discriminate strings values
            representation = "\"" + representation + "\"";
        }
        return representation;
    }

    public static FailTransformationRule create() {
        FailTransformationRule rule = new FailTransformationRule();
        return rule;
    }

}
