package net.sf.kfgodel.bean2bean.impl.mappings.impl;

import net.sf.kfgodel.bean2bean.impl.engine.EngineContext;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingRepository;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * This type implements the mapping repo with a map
 * Created by kfgodel on 11/02/15.
 */
public class MappingRepositoryImpl implements MappingRepository {
    public static Logger LOG = LoggerFactory.getLogger(MappingRepositoryImpl.class);

    private Map<MappingVector, Function> transformationByVector;
    
    public static MappingRepositoryImpl create() {
        MappingRepositoryImpl repository = new MappingRepositoryImpl();
        repository.transformationByVector = new HashMap<>();
        return repository;
    }

    @Override
    public void storeFor(MappingVector vector, Function transformation) {
        Function previous = transformationByVector.put(vector, transformation);
        if(previous != null){
            LOG.warn("A transformation was overridden for {}",vector);
        }
    }

    @Override
    public <R> Optional<Function<EngineContext, R>> getFor(MappingVector mappingVector) {
        Function transformation = transformationByVector.get(mappingVector);
        if(transformation == null){
            return Optional.empty();
        }
        return Optional.of(transformation);
    }
}
