package net.sf.kfgodel.bean2bean.impl.mappings;

/**
 * This type represents an abstract transition from one object to other.<br>
 *     This type is used to express the source and destination as a pair, indicating direction
 * Created by kfgodel on 10/02/15.
 */
public interface MappingVector {

    /**
     * @return The object that it's the source of the expected transformation
     */
    Object getSourceObject();

    /**
     * @return The object that's the destination of the expected transformation
     */
    Object getDestinationObject();
}
