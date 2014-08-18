package net.sf.kfgodel.bean2bean.api.mappings;

/**
 * This type represents a simplemapping between two types that is expresses as a block of code.<br>
 *     Usually as a manual copy of  properties between types
 * Created by kfgodel on 17/08/14.
 */
@FunctionalInterface
public interface ProgrammaticTypeMapping<S,D> {
    /**
     * Maps the properties of the source object to the destination object obtained from the mapping context
     * @param context The context that allows access to the objects being mapped
     */
    void mapTypes(MappingContext<S,D> context);
}
