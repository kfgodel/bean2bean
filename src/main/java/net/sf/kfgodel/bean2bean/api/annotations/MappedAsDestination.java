package net.sf.kfgodel.bean2bean.api.annotations;

import java.lang.annotation.*;

/**
 * This type represents a mapping operation that uses expressions to define source, destination and/or transformation.<br>
 *     You would normally use this annotation on DTO fields to map properties from a domain object<br>
 *     For a simpler use case see CopyTo.
 * Created by kfgodel on 30/08/14.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface MappedAsDestination {

    /**
     * The producer expression that defines the source value.<br>
     *     If defined this expression overrides default source (the field annotated with this annotation)
     * @return An annotation that defines a producer expression to get a value
     */
    B2bExpression from();

    /**
     * A transformer expression to convert the source value into a destination value.<br>
     *     If defined this expression overrides any pre-defined type mapping
     * @return An annotation that defines a conversion between values
     */
    B2bExpression transform();

    /**
     * A consumer expression that defines the place to put the destination value.<br>
     *     If defined, this expression overrides value()
     * @return An annotation that defines a consumer expression to use the destination value
     */
    B2bExpression to();

}
