package info.kfgodel.bean2bean.dsl.api;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This type represents a configuration that scopes the applicability of a converter to limit its usage
 *
 * Date: 05/03/19 - 16:33
 */
public interface ScopedConfigureDsl {

  /**
   * Registers a function to be used as converter between the function input-output types.<br>
   *   The applicability of the converter will be limited to the scope implied by this instance.
   * @param converterFunction The function instance to use as a converter
   * @return This instance for method chaining
   */
  <I,O> ScopedConfigureDsl useConverter(Function<I,O> converterFunction);

  /**
   * Registers a converter that will need access to the conversion task as part of the conversion
   *   process. Usually for delegating the conversion of one or more sub-parts of the original
   *   object (nesting conversions).
   * @param converterFunction The bi function to use as converter
   * @return This instance for method chaining
   */
  <I,O> ScopedConfigureDsl useConverter(BiFunction<I, Bean2beanTask,O> converterFunction);

  /**
   * Registers a converter function that requires no arguments as input for the conversion.<br>
   *   Usually these converters are used as generators to instantiate other types
   * @param converterFunction The supplier to use as generator
   * @return This instance for method chaining
   */
  <O> ScopedConfigureDsl useConverter(Supplier<O> converterFunction);

  /**
   * Registers a converter function that generates no output as result of the conversion.<br>
   *   Usually these converters are used as destructors of instances to release resources
   * @param converterFunction The consumer to use as destructor
   * @return This instance for method chaining
   */
  <I> ScopedConfigureDsl useConverter(Consumer<I> converterFunction);

}
