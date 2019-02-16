package info.kfgodel.bean2bean.dsl.api;

import info.kfgodel.bean2bean.other.FunctionRef;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This type represents the configuration parameters that a b2b dsl can have
 * Date: 10/02/19 - 23:05
 */
public interface ConfigureDsl {
  /**
   * Registers a function to be used as converter between the function input-output types.<br>
   *   The type parameters of the function class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the function class, then {@link Object} will be used
   *   instead for input and output types scoping (Note, this may collide with other converters).<br>
   * @param converterFunction The function instance to use as a converter
   * @return This instance for method chaining
   */
  ConfigureDsl usingConverter(Function<?,?> converterFunction);

  /**
   * Registers a function through its reference to be used as converter between the function input-output types.<br>
   *   The type parameters of the function class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the function class, then {@link Object} will be used
   *   instead for input and output types scoping (Note, this may collide with other converters).<br>
   * @param converterFunctionRef The reference to the function used a s converter
   * @return This instance for method chaining
   */
  ConfigureDsl usingConverter(FunctionRef<?,?> converterFunctionRef);

  /**
   * Registers a converter that will need access to b2b dsl as part of the conversion process. Usually
   * for delegating the conversion of one or mor parts of the original object.
   * @param converterFunction The function to use as converter
   * @return This instance for method chaining
   */
  ConfigureDsl usingConverter(BiFunction<?,B2bDsl,?> converterFunction);


  /**
   * Registers a converter function that requires no parameters as input for the conversion.<br>
   *   Usually this converters are used as generators to instantiate other types
   * @param converterFunction The function to use a generator
   * @return This instance for method chaining
   */
  ConfigureDsl usingConverter(Supplier<?> converterFunction);
}
