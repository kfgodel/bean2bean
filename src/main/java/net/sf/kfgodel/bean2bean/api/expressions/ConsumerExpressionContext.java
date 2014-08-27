package net.sf.kfgodel.bean2bean.api.expressions;

/**
 * This type represents the context of a consumer expression
 * Created by kfgodel on 25/08/14.
 */
public interface ConsumerExpressionContext {
    /**
     * The value that can be consumed by the expression. Depending on the mapping, this can bethe source value, a transformation,
     * or something else. In any case, this is the value that¡s expected to be setted on the destination property
     * @param <T> The type of expected value
     * @return The value
     */
    <T> T getDestinationValue();

}
