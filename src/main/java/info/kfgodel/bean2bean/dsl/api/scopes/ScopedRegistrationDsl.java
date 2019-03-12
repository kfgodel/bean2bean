package info.kfgodel.bean2bean.dsl.api.scopes;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.other.references.ConsumerRef;
import info.kfgodel.bean2bean.other.references.FunctionRef;
import info.kfgodel.bean2bean.other.references.SupplierRef;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This type defines the methods for registering a converter under a limited scope
 *
 * Date: 11/03/19 - 21:50
 */
public interface ScopedRegistrationDsl<I,O> {

  /**
   * Registers a function to be used as converter between the function input-output types.<br>
   *   The applicability of the converter will be limited to the scope implied by this instance.
   * @param converterFunction The function instance to use as a converter
   * @return This instance for method chaining
   */
  ScopedRegistrationDsl<I,O> useConverter(Function<? super I, ? extends O> converterFunction);

  /**
   * Registers a converter that will need access to the conversion task as part of the conversion
   *   process. Usually for delegating the conversion of one or more sub-parts of the original
   *   object (nesting conversions).
   * @param converterFunction The bi function to use as converter
   * @return This instance for method chaining
   */
  ScopedRegistrationDsl<I,O> useConverter(BiFunction<? super I, Bean2beanTask,? extends O> converterFunction);

  /**
   * Registers a converter function that requires no arguments as input for the conversion.<br>
   *   Usually these converters are used as generators to instantiate other types
   * @param converterFunction The supplier to use as generator
   * @return This instance for method chaining
   */
  ScopedRegistrationDsl<I,O> useConverter(Supplier<? extends O> converterFunction);

  /**
   * Registers a converter function that generates no output as result of the conversion.<br>
   *   Usually these converters are used as destructors of instances to release resources
   * @param converterFunction The consumer to use as destructor
   * @return This instance for method chaining
   */
  ScopedRegistrationDsl<I,O> useConverter(Consumer<? super I> converterFunction);

  /**
   * Registers a function through its reference to be used as converter between the function input-output types.<br>
   *   This method variant allows using a reference to disambiguate lambda parameters (useful for method references with
   *   overloaded versions)
   * @param converterFunctionRef The reference to the function used as converter
   * @return This instance for method chaining
   */
  ScopedRegistrationDsl<I,O> useConverter(FunctionRef<? super I,? extends O> converterFunctionRef);

  /**
   * Registers a converter function though its reference that will need access to b2b dsl as part of the conversion
   *  process. Usually for delegating the conversion of one or more parts of the original object (nesting conversions).
   * @param converterFunction The function to use as converter
   * @return This instance for method chaining
   */
  ScopedRegistrationDsl<I,O> useConverter(BiFunctionRef<? super I, Bean2beanTask, ? extends O> converterFunction);

  /**
   * Registers a converter lambda through its reference that requires no parameters as input for the conversion.<br>
   *   Usually these converters are used as generators to instantiate other types
   * @param converterFunction The function to use a generator
   * @return This instance for method chaining
   */
  ScopedRegistrationDsl<I,O> useConverter(SupplierRef<? extends O> converterFunction);

  /**
   * Registers a converter lambda through its reference that generates no output as result of the conversion.<br>
   *   Usually these converters are used as destructors of intances to release resources
   * @param converterFunction The function to use as destructor
   * @return This instance for method chaining
   */
  ScopedRegistrationDsl<I,O> useConverter(ConsumerRef<? super I> converterFunction);
}
