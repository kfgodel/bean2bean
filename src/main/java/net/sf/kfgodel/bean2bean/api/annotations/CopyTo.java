package net.sf.kfgodel.bean2bean.api.annotations;

import java.lang.annotation.*;

/**
 * This type represents a mapping operation that copies the value from the field annotated with this annotation to an specified destination.<br>
 *     You would normally use this annotation on DTO fields to map properties to a domain object.<br>
 *     For advanced use case see MappedAsDestination
 * Created by kfgodel on 30/08/14.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CopyTo {

    /**
     * Expression that defines the property sequence to follow on destination object to set the destination value.<br>
     *     This expression uses the native interpreter and by default maps to a property with same name as the annotated field.<br>
     *     You should use to() to override this value and select an interpreter different than native
     * @return An expression in native interpreter to set the value on destination
     */
    String value() default "property with same name";

    /**
     * The name of the interpreter to use when parsing the value expression
     * @return One of the valid interpreters registered in the configuration
     */
    String interpreter() default "native";

}
