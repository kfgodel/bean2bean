package net.sf.kfgodel.bean2bean.api.partials;

import java.util.function.Supplier;

/**
 * This type represents a definition for creation that is yet to be completed
 * Created by kfgodel on 14/08/14.
 */
public interface ManipulateCreationPartial<T> {
    /**
     * Defines the supplier to be used when creating instances of the type indicated in this partial
     * @param instanceSupplier The supplier for new instances
     */
    void with(Supplier<T> instanceSupplier);
}
