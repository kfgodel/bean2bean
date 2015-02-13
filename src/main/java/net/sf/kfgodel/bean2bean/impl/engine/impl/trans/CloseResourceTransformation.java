package net.sf.kfgodel.bean2bean.impl.engine.impl.trans;

import ar.com.kfgodel.diamond.api.Diamond;
import ar.com.kfgodel.diamond.api.exceptions.DiamondException;
import net.sf.kfgodel.bean2bean.impl.engine.EngineContext;
import net.sf.kfgodel.bean2bean.impl.mappings.MappingRepository;
import net.sf.kfgodel.bean2bean.impl.mappings.impl.MappingVectorImpl;

import java.io.Closeable;
import java.io.IOException;
import java.util.function.Function;

/**
 * This type represents the transformation that closes resources when used
 * Created by kfgodel on 13/02/15.
 */
public class CloseResourceTransformation implements Function<EngineContext,Object> {
    @Override
    public Object apply(EngineContext engineContext) {
        Object source = engineContext.getSourceObject();
        if(source instanceof Closeable){
            Closeable resource = (Closeable) source;
            try {
                resource.close();
            } catch (IOException e) {
                throw new DiamondException("Problem closing a resource: " + resource,e);
            }
        }
        return source;
    }


    /**
     * Registers this transformation from closeable to void to close instances
     * @param mappingRepo THe repo to register the transformation
     */
    public static void registerIn(MappingRepository mappingRepo) {
        mappingRepo.storeFor(MappingVectorImpl.create(Diamond.of(Closeable.class), Diamond.of(void.class)), new CloseResourceTransformation());
    }
}
