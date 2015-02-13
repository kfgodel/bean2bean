package net.sf.kfgodel.bean2bean.impl.engine.impl.rules;

import net.sf.kfgodel.bean2bean.impl.engine.EngineContext;
import net.sf.kfgodel.bean2bean.impl.engine.impl.NextAbstractionSpliterator;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingRepository;
import net.sf.kfgodel.bean2bean.impl.mappings.impl.MappingVectorImpl;
import net.sf.kfgodel.bean2bean.impl.transformations.TransformationRule;

import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterators;
import java.util.function.Function;

/**
 * This type represents the rule that tries to find the mapping vector from most specific to less specific using the 
 * source and destination object types as increasing generalization sets.<br>
 *     This rule tries to find a transformation from the original objects to their most generic types
 * Created by kfgodel on 11/02/15.
 */
public class SearchByAbstractionVectorRule implements TransformationRule<EngineContext> {
    
    @Override
    public <R> Optional<Function<EngineContext, R>> getTransformationIfApplicableTo(EngineContext context) {
        MappingRepository mappingRepo = context.getMappingRepository();
        // We need to verify each possible combination of source and destination types (we do so, by iterating all the possible
        // sources with each possible destination)
        Iterator<Object> destinationIterator = Spliterators.iterator(NextAbstractionSpliterator.create(context.getDestinationObject()));
        while(destinationIterator.hasNext()){
            Object destination = destinationIterator.next();
            Iterator<Object> sourceIterator = Spliterators.iterator(NextAbstractionSpliterator.create(context.getSourceObject()));
            while(sourceIterator.hasNext()){
                Object source = sourceIterator.next();
                // Let's see if there's a transformation defined for this combination
                MappingVectorImpl vector = MappingVectorImpl.create(source, destination);
                Optional<Function<EngineContext, R>> foundTransformation = mappingRepo.getFor(vector);
                if(foundTransformation.isPresent()){
                    return foundTransformation;
                }
            }
        }
        return Optional.empty();
    }

    public static SearchByAbstractionVectorRule create() {
        SearchByAbstractionVectorRule rule = new SearchByAbstractionVectorRule();
        return rule;
    }

}
