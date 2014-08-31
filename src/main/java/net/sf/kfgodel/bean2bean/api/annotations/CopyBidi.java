package net.sf.kfgodel.bean2bean.api.annotations;

import java.lang.annotation.*;

/**
 * This type represents 2in1 mapping equivalent to having a CopyFrom and CopyTo annotations. Copies the value from the
 * field annotated with this annotation to an specified destination and vice-versa, depending on the direction of the
 * mapping.<br>
 *     You would normally use this annotation on DTO fields to map properties bidirectionally on a domain object.<br>
 *     For advanced mappings see MappedAsSource and MappedAsDestination. Both override this annotation if defined
 * Created by kfgodel on 30/08/14.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface CopyBidi {

    /**
     * Expression that defines the property sequence to follow on the other object to set and get the mapped value.<br>
     *     This expression uses the native interpreter and by default maps to a property with same name as the annotated field.<br>
     * @return An expression in native interpreter to set the value on destination
     */
    String value() default "property with same name";

    /**
     * The name of the interpreter to use when parsing the value expression
     * @return One of the valid interpreters registered in the configuration
     */
    String interpreter() default "native";

}
