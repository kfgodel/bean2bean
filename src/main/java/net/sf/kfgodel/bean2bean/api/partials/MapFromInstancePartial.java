package net.sf.kfgodel.bean2bean.api.partials;

/**
 * This type represents a partial mapping operation with a source object defined
 * Created by kfgodel on 20/08/14.
 */
public interface MapFromInstancePartial<S> {
    /**
     * Maps the properties from the source object to the given destination object.<br>
     *     Properties are mapped according to defined mappings
     * @param destinationObject The object to move state to
     */
    public void to(Object destinationObject);
}
