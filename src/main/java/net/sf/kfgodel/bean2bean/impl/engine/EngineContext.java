package net.sf.kfgodel.bean2bean.impl.engine;

import net.sf.kfgodel.bean2bean.impl.mappings.MappingRepository;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingVector;

/**
 * This type represents the context that the engine uses to transform objects.<br>
 *     It contains all the references to involved objects
 * Created by kfgodel on 10/02/15.
 */
public interface EngineContext {
    /**
     * @return The vector that indicates source and destination objects
     */
    MappingVector getTransformationVector();

    /**
     * @return The repository of mappings under current context
     */
    MappingRepository getMappingRepository();
}
