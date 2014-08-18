package net.sf.kfgodel.bean2bean.api;

import net.sf.kfgodel.bean2bean.api.partials.MapFromPartial;

/**
 * This type represents the mapper api for transfering state beween objects
 * Created by kfgodel on 14/08/14.
 */
public interface B2bMapperApi {
    /**
     * Starts the definition of mapping between two types
     * @param sourceType The source type as a class instance
     * @return The partial definition
     */
    <T> MapFromPartial<T> fromInstanceOf(Class<T> sourceType);
}
