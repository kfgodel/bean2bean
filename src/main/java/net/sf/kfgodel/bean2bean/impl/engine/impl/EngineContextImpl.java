package net.sf.kfgodel.bean2bean.impl.engine.impl;

import net.sf.kfgodel.bean2bean.impl.engine.EngineContext;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingRepository;

/**
 * This type implements the engine context as a reference to all the involved objects
 * Created by kfgodel on 10/02/15.
 */
public class EngineContextImpl implements EngineContext {
    
    private Object source;
    private Object destination;
    private MappingRepository repository;
    
    @Override
    public MappingRepository getMappingRepository() {
        return repository;
    }

    @Override
    public Object getSourceObject() {
        return source;
    }

    @Override
    public Object getDestinationObject() {
        return destination;
    }

    public static EngineContextImpl create(Object source, Object destination,MappingRepository repository) {
        EngineContextImpl context = new EngineContextImpl();
        context.source = source;
        context.destination = destination;
        context.repository = repository;
        return context;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + source + " -> " + destination + "]";
    }
}
