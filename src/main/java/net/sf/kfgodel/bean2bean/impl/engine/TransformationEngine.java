package net.sf.kfgodel.bean2bean.impl.engine;

import net.sf.kfgodel.bean2bean.api.exceptions.Bean2beanException;

import java.util.function.Function;

/**
 * This type represents the core of the b2b transformations.<br>
 *     It's based on a set of transformation rules and a mapping repository
 * Created by kfgodel on 10/02/15.
 */
public interface TransformationEngine {

    /**
     * Calculates the best transformation for the given context
     * @param aContext The mapping context that needs a transformation
     * @param <R> The type of transformation result (unbound to be inferred)
     * @return The transformation function
     * @throws net.sf.kfgodel.bean2bean.api.exceptions.Bean2beanException if no transformation could be found
     */
    <R> Function<EngineContext,R> getBestTransformationFor(EngineContext aContext) throws Bean2beanException;
}
