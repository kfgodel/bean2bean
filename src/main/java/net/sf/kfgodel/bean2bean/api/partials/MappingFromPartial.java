package net.sf.kfgodel.bean2bean.api.partials;

import net.sf.kfgodel.bean2bean.api.expressions.ConsumerExpressionContext;
import net.sf.kfgodel.bean2bean.api.expressions.TransformerExpressionContext;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * This type represents a partial definition of a copy operation
 * Created by kfgodel on 18/08/14.
 */
public interface MappingFromPartial extends MappingFromTransformedPartial {

    /**
     * Defines a transformation to be done on the source value to obtain the destination value
     * @return The partially defined mapping with transformed operation
     */
    MappingFromTransformedPartial transformedWithExpression(Function<TransformerExpressionContext, ?> transformerExpression);
}
