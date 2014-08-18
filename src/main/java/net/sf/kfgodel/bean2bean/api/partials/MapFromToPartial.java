package net.sf.kfgodel.bean2bean.api.partials;

import net.sf.kfgodel.bean2bean.api.mappings.ProgrammaticInstanceMapping;
import net.sf.kfgodel.bean2bean.api.mappings.ProgrammaticTypeMapping;

/**
 * This type represes a partial definition of a mapping between types
 * Created by kfgodel on 17/08/14.
 */
public interface MapFromToPartial<S,D> {
    /**
     * Creates a new mapping between source and destination type that will execute the given mapping
     * to map state from one instance to the other.<br>
     *     This type of mapping doesn't support cyclic references between properties that need conversion
     * @param mapping The mapping object to use between the mapped types
     */
    void using(ProgrammaticInstanceMapping<S, D> mapping);

    /**
     * Creates a new mapping between source and destination types using the instructions expressed
     * in the given mapping.<br>
     *     This allows resolution of cyclic references between properties that need conversion
     * @param mapping
     */
    void  instructedAs(ProgrammaticTypeMapping<S,D> mapping);
}
