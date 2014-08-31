package net.sf.kfgodel.bean2bean.api.annotations;

/**
 * This type represents a Bean2bean expression that can be used with mapping annotations
 * Created by kfgodel on 30/08/14.
 */
public @interface B2bExpression {

    /**
     * The expression that can be interpreted by indicated interpreter and used in the context of a mapping operation
     * @return A mapping expression (producer, transformer or consumer)
     */
    String value();

    /**
     * The name of the interpreter to use when parsing the value expression
     * @return One of the valid interpreters registered in the configuration
     */
    String interpreter() default "native";
}
