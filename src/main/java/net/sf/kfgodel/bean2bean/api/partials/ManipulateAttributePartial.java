package net.sf.kfgodel.bean2bean.api.partials;

import net.sf.kfgodel.bean2bean.api.partials.ManipulatePropertyPartial;

/**
 * this type represents a partially defined manipulation definition over an object
 * Created by kfgodel on 16/08/14.
 */
public interface ManipulateAttributePartial<T> {
    /**
     * Continues the manipulation definition, indicating the instance property to be manipulated
     * @param fieldName The name of the property
     * @return A partially defined operation
     */
    ManipulatePropertyPartial<T> property(String fieldName);
}
