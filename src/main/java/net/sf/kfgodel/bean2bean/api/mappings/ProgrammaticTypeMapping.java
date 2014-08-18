package net.sf.kfgodel.bean2bean.api.mappings;

/**
 * This type represents a mapping that expresses instructions for mapping the properties between the two types
 * Created by kfgodel on 18/08/14.
 */
public interface ProgrammaticTypeMapping<S,D> {
    /**
     * Maps the properties of the source type to the destination type using instructions
     * @param context The context that allows access to the types being mapped
     */
    void mapInstances(TypeMappingContext<S, D> context);

}
