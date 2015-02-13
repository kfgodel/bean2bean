package net.sf.kfgodel.bean2bean.impl.engine.impl.trans;

import ar.com.kfgodel.diamond.api.Diamond;
import ar.com.kfgodel.diamond.api.types.TypeInstance;
import net.sf.kfgodel.bean2bean.impl.engine.EngineContext;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingRepository;
import net.sf.kfgodel.bean2bean.impl.mappings.impl.MappingVectorImpl;

import java.util.function.Function;

/**
 * This type represents the transformation that creates new instances from expected types
 * Created by kfgodel on 13/02/15.
 */
public class InstantiateTransformation implements Function<EngineContext, Object> {
    @Override
    public Object apply(EngineContext engineContext) {
        TypeInstance expectedType = (TypeInstance) engineContext.getDestinationObject();
        Object newInstance = expectedType.newInstance();
        return newInstance;
    }

    /**
     * Registers the transformation used to instantiate objects generically using reflection and the niladic constructor
     * @param mappingRepo The repo to store the transformation from void to a typeInstance
     */
    public static void registerIn(MappingRepository mappingRepo) {
        mappingRepo.storeFor(MappingVectorImpl.create(Diamond.of(void.class), Diamond.of(TypeInstance.class)), new InstantiateTransformation());
    }
}
