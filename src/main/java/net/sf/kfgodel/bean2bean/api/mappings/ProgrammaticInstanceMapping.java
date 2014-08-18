package net.sf.kfgodel.bean2bean.api.mappings;

/**
 * This type represents a simple mapping between two instances that moves the properties when executed.<br>
 *     This type of mapping doesn't allow cyclic references that need conversion
 * Created by kfgodel on 17/08/14.
 */
@FunctionalInterface
public interface ProgrammaticInstanceMapping<S,D> {
    /**
     * Maps the properties of the source object to the destination object obtained from the mapping context
     * @param context The context that allows access to the objects being mapped
     */
    void mapInstances(InstanceMappingContext<S, D> context);
}
