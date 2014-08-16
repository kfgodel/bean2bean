package net.sf.kfgodel.bean2bean.api;

import net.sf.kfgodel.bean2bean.api.partials.ManipulateCreationPartial;
import net.sf.kfgodel.bean2bean.api.partials.ManipulateDisposalPartial;

import java.lang.reflect.Type;

/**
 * This type represents the API interface for bean2bean as a bean manipulator
 * Created by kfgodel on 14/08/14.
 */
public interface B2bManipulatorApi {
    /**
     * Starts the definition of a creation operation to create objects
     * @param type The expected type
     * @return The partial definition to complete
     */
    ManipulateCreationPartial<Object> creationOf(Type type);

    /**
     * Starts the definition of a creation operation to create objects
     * @param type The expected type to be returned
     * @param <T> The type that should be returned
     * @return The partial definition
     */
    <T> ManipulateCreationPartial<T> creationOf(Class<T> type);

    /**
     * Creates a new instance of expected type using pre-configured object creators for that type
     * @param expectedType The class instance that represents the expected type
     * @param <T> The type of the expected result
     */
    <T> T createInstanceOf(Class<T> expectedType);

    /**
     * Starts the definition of a disposal operation to eliminate objects
     * @param type The instance that represents the disposable type
     * @param <T> The type of objects to be disposed
     * @return The partial definition
     */
    <T> ManipulateDisposalPartial<T> disposalOf(Class<T> type);

    /**
     * Gets rid of the given object calling the disposer pre-configured for its type.<br>
     * @param disposable The object to be disposed of
     */
    void dispose(Object disposable);

    /**
     * Starts the manipulation of the given instance returning a partial definition of operation to be done
     * @param instance the instance to be manipulated
     * @return The partial operation definition
     */
    <T> ManipulateAttributePartial<T> from(T instance);
}
