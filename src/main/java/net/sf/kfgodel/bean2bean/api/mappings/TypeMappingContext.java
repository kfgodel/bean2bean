package net.sf.kfgodel.bean2bean.api.mappings;

import net.sf.kfgodel.bean2bean.api.expressions.ProducerExpressionContext;
import net.sf.kfgodel.bean2bean.api.partials.MappingFromPartial;

import java.util.function.Function;

/**
 * This type represents the context of a type mapping operation
 * Created by kfgodel on 18/08/14.
 */
public interface TypeMappingContext<S,D> extends InstanceMappingContext<S,D> {

    /**
     * Starts the copy operation definition by indicating a property to take the value from in the source type
     * @param propertyNameOnSourceType The name of the property in the source type
     * @return The partial copy operation definition
     */
    MappingFromPartial fromProperty(String propertyNameOnSourceType);

    /**
     * Starts a copy operation definition using an expression as the source for the mapping
     * @param producer the producer expression
     * @return The partial mapping definition
     */
    MappingFromPartial fromExpression(Function<ProducerExpressionContext, ?> producer);
}
