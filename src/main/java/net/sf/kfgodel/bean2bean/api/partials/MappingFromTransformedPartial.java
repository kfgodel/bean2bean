package net.sf.kfgodel.bean2bean.api.partials;

import net.sf.kfgodel.bean2bean.api.expressions.ConsumerExpressionContext;

import java.util.function.Consumer;

/**
 * This type represents a partial definition of a copy operation with a transformation
 * Created by kfgodel on 26/08/14.
 */
public interface MappingFromTransformedPartial {

    /**
     * Defines the destination property of the mapping operation
     * @param propertyNameOnDestType The name of the property in the destination type
     */
    void toProperty(String propertyNameOnDestType);

    /**
     * Defines the destination of the mapping operation using a custom expression to consume the value
     * @param consumerExpression The expression that will receive the mapped value in the context
     */
    void toExpression(Consumer<ConsumerExpressionContext> consumerExpression);

}
