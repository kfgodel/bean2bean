package net.sf.kfgodel.bean2bean.api.mappings;

import net.sf.kfgodel.bean2bean.api.B2bApi;

/**
 * This type represents the context of a instance mapping operation
 * Created by kfgodel on 17/08/14.
 */
public interface InstanceMappingContext<S,D> {

    /**
     * The object that has the state to be mapped from
     * @return The source instance
     */
    S getSourceObject();

    /**
     * The object to map state to
     * @return The destination instance
     */
    D getDestinationObject();

    /**
     * @return The instance of the b2b used for this mapping
     */
    B2bApi b2b();
}
