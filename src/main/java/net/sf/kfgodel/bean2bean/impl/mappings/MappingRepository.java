package net.sf.kfgodel.bean2bean.impl.mappings;

import net.sf.kfgodel.bean2bean.impl.engine.EngineContext;

import java.util.Optional;
import java.util.function.Function;

/**
 * This type represents a mapping repository with all the known mapping vectors and their transformations
 * *
 * Created by kfgodel on 11/02/15.
 */
public interface MappingRepository {
    /**
     * Stores the transformation to use for the given vector
     * @param vector The vector that indicates the transformation direction
     * @param transformation The transformation to use
     */
    void storeFor(MappingVector vector, Function transformation);

    /**
     * Gets the stored transformation for the given vector
     * @param mappingVector The vector to get the transformation for
     * @param <R> The type of expected result
     * @return The function or empty if no transformation was stored for the vector
     */
    <R> Optional<Function<EngineContext,R>> getFor(MappingVector mappingVector);
}
