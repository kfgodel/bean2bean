package info.kfgodel.bean2bean.dsl.api;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.other.FunctionRef;

import java.util.function.Function;

/**
 * This type represents the configuration parameters that a b2b dsl can have
 * Date: 10/02/19 - 23:05
 */
public interface B2bDslConfig {
  /**
   * Registers a function to be used as converter between the function input-output types.<br>
   *   The type parameters of the function class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the function class, then {@link Object} will be used
   *   instead for input and output types scoping (Note, this may collide with other converters).<br>
   * @param converterFunction The function instance to use as a converter
   * @return This instance for method chaining
   */
  B2bDslConfig usingConverter(Function<?,?> converterFunction);

  /**
   * Registers a function through its reference to be used as converter between the function input-output types.<br>
   *   The type parameters of the function class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the function class, then {@link Object} will be used
   *   instead for input and output types scoping (Note, this may collide with other converters).<br>
   * @param converterFunctionRef The reference to the function used a s converter
   * @return This instance for method chaining
   */
  B2bDslConfig usingConverter(FunctionRef<?,?> converterFunctionRef);

  /**
   * Creates a new instance of bean2bean core with current state of configuration
   * @return The new instance
   */
  Bean2bean createBean2bean();
}
