package info.kfgodel.bean2bean.v3.dsl.api.scopes;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.v3.other.references.ConsumerRef;
import info.kfgodel.bean2bean.v3.other.references.FunctionRef;
import info.kfgodel.bean2bean.v3.other.references.SupplierRef;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This type represents a configuration that scopes the applicability of a converter to limit its usage
 *
 * Date: 05/03/19 - 16:33
 */
public interface ScopedRegistrationDsl {

  /**
   * Registers a function to be used as converter between the function input-output types.<br>
   *   The applicability of the converter will be limited to the scope implied by this instance.
   * @param converterFunction The function instance to use as a converter
   * @return This instance for method chaining
   */
  <I,O> ScopedRegistrationDsl useConverter(Function<I,O> converterFunction);

  /**
   * Registers a converter that will need access to the conversion task as part of the conversion
   *   process. Usually for delegating the conversion of one or more sub-parts of the original
   *   object (nesting conversions).
   * @param converterFunction The bi function to use as converter
   * @return This instance for method chaining
   */
  <I,O> ScopedRegistrationDsl useConverter(BiFunction<I, Bean2beanTask,O> converterFunction);

  /**
   * Registers a converter function that requires no arguments as input for the conversion.<br>
   *   Usually these converters are used as generators to instantiate other types
   * @param converterFunction The supplier to use as generator
   * @return This instance for method chaining
   */
  <O> ScopedRegistrationDsl useConverter(Supplier<O> converterFunction);

  /**
   * Registers a converter function that generates no output as result of the conversion.<br>
   *   Usually these converters are used as destructors of instances to release resources
   * @param converterFunction The consumer to use as destructor
   * @return This instance for method chaining
   */
  <I> ScopedRegistrationDsl useConverter(Consumer<I> converterFunction);

  /**
   * Registers a function through its reference to be used as converter between the function input-output types.<br>
   *   This method variant allows using a reference to disambiguate lambda parameters (useful for method references with
   *   overloaded versions)
   * @param converterFunctionRef The reference to the function used as converter
   * @return This instance for method chaining
   */
  <I,O> ScopedRegistrationDsl useConverter(FunctionRef<I,O> converterFunctionRef);

  /**
   * Registers a converter function though its reference that will need access to b2b dsl as part of the conversion
   *  process. Usually for delegating the conversion of one or more parts of the original object (nesting conversions).
   * @param converterFunction The function to use as converter
   * @return This instance for method chaining
   */
  <I,O> ScopedRegistrationDsl useConverter(BiFunctionRef<I,Bean2beanTask,O> converterFunction);

  /**
   * Registers a converter lambda through its reference that requires no parameters as input for the conversion.<br>
   *   Usually these converters are used as generators to instantiate other types
   * @param converterFunction The function to use a generator
   * @return This instance for method chaining
   */
  <O> ScopedRegistrationDsl useConverter(SupplierRef<O> converterFunction);

  /**
   * Registers a converter lambda through its reference that generates no output as result of the conversion.<br>
   *   Usually these converters are used as destructors of intances to release resources
   * @param converterFunction The function to use as destructor
   * @return This instance for method chaining
   */
  <I> ScopedRegistrationDsl useConverter(ConsumerRef<I> converterFunction);
}
