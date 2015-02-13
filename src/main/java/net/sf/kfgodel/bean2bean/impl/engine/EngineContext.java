package net.sf.kfgodel.bean2bean.impl.engine;

import net.sf.kfgodel.bean2bean.impl.mappings.MappingRepository;

/**
 * This type represents the context that the engine uses to transform objects.<br>
 *     It contains all the references to involved objects
 * Created by kfgodel on 10/02/15.
 */
public interface EngineContext {
    /**
     * @return The repository of mappings under current context
     */
    MappingRepository getMappingRepository();

    /**
     * @return The object that needs to be converted
     */
    Object getSourceObject();

    /**
     * @return The object that indicates the expected result
     */
    Object getDestinationObject();
}
