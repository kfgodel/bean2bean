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
 * This type represents a configuration that limits the applicability of registered converters by scoping
 * them according to their argument types.<br>
 *   For runtime defined lambdas a Lambda ref may be needed to keep the type arguments after erasure
 *
 * Date: 05/03/19 - 17:38
 */
public interface ImplicitlyScopedConfigureDsl extends ScopedConfigureDsl {

  /**
   * Registers a function to be used as converter between the function input-output types.<br>
   *   The type parameters of the function class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the lambda class, then {@link Object}
   *   will be used instead for input or output types scoping. Note, this may collide with other converters.<br>
   *   <br>
   *   For type disambiguation use explicit lambda references that retains the type parameters after
   *   erasure. See {@link #useConverter(FunctionRef)}
   * @param converterFunction The function instance to use as a converter
   * @return This instance for method chaining
   */
  <I,O> ImplicitlyScopedConfigureDsl useConverter(Function<I,O> converterFunction);

  /**
   * Registers a converter that will need access to the task as part of the conversion process. Usually
   * for delegating the conversion of one or more parts of the original object (nesting conversions).<br>
   *   The type parameters of the lambda class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the lambda class, then {@link Object}
   *   will be used instead for input or output types scoping. Note, this may collide with other converters.<br>
   *   <br>
   *   For type disambiguation use explicit lambda references that retains the type parameters after
   *   erasure. See {@link #useConverter(BiFunctionRef)}
   *
   * @param converterFunction The function to use as converter
   * @return This instance for method chaining
   */
  <I,O> ImplicitlyScopedConfigureDsl useConverter(BiFunction<I, Bean2beanTask,O> converterFunction);

  /**
   * Registers a converter function that requires no parameters as input for the conversion.<br>
   *   Usually these converters are used as generators to instantiate other types.<br>
   *   The type parameters of the lambda class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the lambda class, then {@link Object}
   *   will be used instead for input or output types scoping. Note, this may collide with other converters.<br>
   *   <br>
   *   For type disambiguation use explicit lambda references that retains the type parameters after
   *   erasure. See {@link #useConverter(SupplierRef)}
   *
   * @param converterFunction The function to use as generator
   * @return This instance for method chaining
   */
  <O> ImplicitlyScopedConfigureDsl useConverter(Supplier<O> converterFunction);

  /**
   * Registers a converter function that generates no output as result of the conversion.<br>
   *   Usually these converters are used as destructors of instances to release resources.<br>
   *   The type parameters of the lambda class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the lambda class, then {@link Object}
   *   will be used instead for input or output types scoping. Note, this may collide with other converters.<br>
   *   <br>
   *   For type disambiguation use explicit lambda references that retains the type parameters after
   *   erasure. See {@link #useConverter(ConsumerRef)}
   *
   * @param converterFunction The function to use as destructor
   * @return This instance for method chaining
   */
  <I> ImplicitlyScopedConfigureDsl useConverter(Consumer<I> converterFunction);

  /**
   * Registers a function through its reference to be used as converter between the function input-output types.<br>
   * This methods allows indicating explicit type arguments and avoid type erasure.<br>
   *   The type parameters of the function class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the function class, then {@link Object} will be used
   *   instead for input and output types scoping (Note, this may collide with other converters).<br>
   * @param converterFunctionRef The reference to the function used a s converter
   * @return This instance for method chaining
   */
  <I,O> ImplicitlyScopedConfigureDsl useConverter(FunctionRef<I,O> converterFunctionRef);

  /**
   * Registers a converter function though its reference that will need access to b2b dsl as part of
   * the conversion process. Usually for delegating the conversion of one or more parts of the original object (nesting conversions).
   * This methods allows indicating explicit type arguments and avoid type erasure.<br>
   * @param converterFunction The function to use as converter
   * @return This instance for method chaining
   */
  <I,O> ImplicitlyScopedConfigureDsl useConverter(BiFunctionRef<I,Bean2beanTask,O> converterFunction);

  /**
   * Registers a converter function through its reference that requires no parameters as input for the conversion.<br>
   *   Usually these converters are used as generators to instantiate other types
   *   This methods allows indicating explicit type arguments and avoid type erasure.<br>
   * @param converterFunction The function to use a generator
   * @return This instance for method chaining
   */
  <O> ImplicitlyScopedConfigureDsl useConverter(SupplierRef<O> converterFunction);

  /**
   * Registers a converter function through its reference that generates no output as result of the conversion.<br>
   *   Usually these converters are used as destructors of intances to release resources
   *   This methods allows indicating explicit type arguments and avoid type erasure.<br>
   * @param converterFunction The function to use as destructor
   * @return This instance for method chaining
   */
  <I> ImplicitlyScopedConfigureDsl useConverter(ConsumerRef<I> converterFunction);
}
