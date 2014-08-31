package net.sf.kfgodel.bean2bean.api.annotations;

import java.lang.annotation.*;

/**
 * This type represents a mapping operation that copies the value from a specified source to the field annotated with this annotation.<br>
 *     You would normally use this annotation on DTO fields to map properties from a domain object<br>
 *     For advanced use case see MappedAsSource. This annotation gets overridden if both present
 * Created by kfgodel on 30/08/14.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CopyFrom {

    /**
     * Expression that defines the property sequence to follow on source object to get the source value.<br>
     *     This expression uses the native interpreter and by default maps to a property with same name as the annotated field.<br>
     *     You should use from() to override this value and select an interpreter different than native
     * @return An expression in native interpreter to get the value from source
     */
    String value() default "property with same name";

    /**
     * The name of the interpreter to use when parsing the value expression
     * @return One of the valid interpreters registered in the configuration
     */
    String interpreter() default "native";
}
