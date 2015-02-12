package net.sf.kfgodel.bean2bean.impl.engine.impl;

import net.sf.kfgodel.bean2bean.impl.engine.EngineContext;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingRepository;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingVector;
import net.sf.kfgodel.bean2bean.impl.mappings.impl.MappingVectorImpl;

/**
 * This type implements the engine context as a reference to all the involved objects
 * Created by kfgodel on 10/02/15.
 */
public class EngineContextImpl implements EngineContext {
    
    private MappingVector vector;
    private MappingRepository repository;
    
    @Override
    public MappingVector getTransformationVector() {
        return vector;
    }

    @Override
    public MappingRepository getMappingRepository() {
        return repository;
    }

    public static EngineContextImpl create(Object source, Object destination,MappingRepository repository) {
        EngineContextImpl context = new EngineContextImpl();
        context.vector = MappingVectorImpl.create(source, destination);
        context.repository = repository;
        return context;
    }

}
