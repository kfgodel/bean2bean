package net.sf.kfgodel.bean2bean.api.partials;

import java.util.function.Consumer;

/**
 * This type represents a partial disposal definition for a given type
 * Created by kfgodel on 14/08/14.
 */
public interface ManipulateDisposalPartial<T> {
    /**
     * Defines the consumer to be called for disposing objects
     * @param disposer The object disposer
     */
    void with(Consumer<T> disposer);
}
