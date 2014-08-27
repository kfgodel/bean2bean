package net.sf.kfgodel.bean2bean.api;

import net.sf.kfgodel.bean2bean.api.partials.MapFromInstancePartial;
import net.sf.kfgodel.bean2bean.api.partials.MapFromTypePartial;

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
    <T> MapFromTypePartial<T> fromInstanceOf(Class<T> sourceType);

    /**
     * Starts mapping properties from the source object to a destination
     * @param sourceObject The source object
     * @param <T> The type from source object
     * @return The partially defined mapping
     */
    <T> MapFromInstancePartial<T> propertiesFrom(T sourceObject);
}
