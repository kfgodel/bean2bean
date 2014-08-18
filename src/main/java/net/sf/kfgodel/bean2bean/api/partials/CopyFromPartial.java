package net.sf.kfgodel.bean2bean.api.partials;

/**
 * This type represents a partial definition of a copy operation
 * Created by kfgodel on 18/08/14.
 */
public interface CopyFromPartial {
    /**
     * Defines the destination property of the copy operation
     * @param propertyNameOnDestType The name of the property in the destination type
     */
    void toProperty(String propertyNameOnDestType);
}
