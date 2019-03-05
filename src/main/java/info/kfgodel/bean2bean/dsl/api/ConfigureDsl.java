package info.kfgodel.bean2bean.dsl.api;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.api.registry.definitions.ConverterDefinition;
import info.kfgodel.bean2bean.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.other.references.ConsumerRef;
import info.kfgodel.bean2bean.other.references.FunctionRef;
import info.kfgodel.bean2bean.other.references.SupplierRef;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This type represents the configuration parameters that a b2b dsl can have.<br>
 *   By default the scope of this configuration is all the conversions
 * Date: 10/02/19 - 23:05
 */
public interface ConfigureDsl extends ScopedConfigureDsl {

  /**
   * Registers a function to be used as converter between the function input-output types.<br>
   *   The type parameters of the function class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the lambda class, then {@link Object}
   *   will be used instead for input or output types scoping. Note, this may collide with other converters.<br>
   *   <br>
   *   For type disambiguation use explicit lambda references that retains the type parameters after
   *   erasure. See {@link #usingConverter(FunctionRef)}
   * @param converterFunction The function instance to use as a converter
   * @return This instance for method chaining
   */
  <I,O> ConfigureDsl usingConverter(Function<I,O> converterFunction);

  /**
   * Registers a converter that will need access to the task as part of the conversion process. Usually
   * for delegating the conversion of one or more parts of the original object (nesting conversions).<br>
   *   The type parameters of the lambda class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the lambda class, then {@link Object}
   *   will be used instead for input or output types scoping. Note, this may collide with other converters.<br>
   *   <br>
   *   For type disambiguation use explicit lambda references that retains the type parameters after
   *   erasure. See {@link #usingConverter(BiFunctionRef)}
   *
   * @param converterFunction The function to use as converter
   * @return This instance for method chaining
   */
  <I,O> ConfigureDsl usingConverter(BiFunction<I, Bean2beanTask,O> converterFunction);

  /**
   * Registers a converter function that requires no parameters as input for the conversion.<br>
   *   Usually these converters are used as generators to instantiate other types.<br>
   *   The type parameters of the lambda class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the lambda class, then {@link Object}
   *   will be used instead for input or output types scoping. Note, this may collide with other converters.<br>
   *   <br>
   *   For type disambiguation use explicit lambda references that retains the type parameters after
   *   erasure. See {@link #usingConverter(SupplierRef)}
   *
   * @param converterFunction The function to use as generator
   * @return This instance for method chaining
   */
  <O> ConfigureDsl usingConverter(Supplier<O> converterFunction);

  /**
   * Registers a converter function that generates no output as result of the conversion.<br>
   *   Usually these converters are used as destructors of instances to release resources.<br>
   *   The type parameters of the lambda class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the lambda class, then {@link Object}
   *   will be used instead for input or output types scoping. Note, this may collide with other converters.<br>
   *   <br>
   *   For type disambiguation use explicit lambda references that retains the type parameters after
   *   erasure. See {@link #usingConverter(ConsumerRef)}
   *
   * @param converterFunction The function to use as destructor
   * @return This instance for method chaining
   */
  <I> ConfigureDsl usingConverter(Consumer<I> converterFunction);

  /**
   * Registers a converter function using its full definition.<br>
   *   The definition contains metadata for the converter function to be used correctly on conversions
   * @param converterDefinition The converter definition to add
   * @return This instance for method chaining
   */
  ConfigureDsl usingDefinition(ConverterDefinition converterDefinition);

  /**
   * Limits the applicability of a converter by using a predicate on the conversion vector, so only
   * if the predicate passes, the converter is used.<br>
   *   All predicate scoped converters are evaluated <b>after</b> vector scoped converters due to their specificity.
   *   Vector converters are usually more specific and direct than predicate converters
   * @param scopePredicate The predicate that is going to be applied on the conversion vector to determine the applicability
   *                       of a converter
   * @return The partial definition of this configuration dsl with the predicate defined
   */
  ScopedConfigureDsl scopedBy(Predicate<DomainVector> scopePredicate);


  /**
   * Registers a function through its reference to be used as converter between the function input-output types.<br>
   *   The type parameters of the function class will be used to scope the applicability of the given instance.
   *   If no type parameters can be obtained using reflection on the function class, then {@link Object} will be used
   *   instead for input and output types scoping (Note, this may collide with other converters).<br>
   * @param converterFunctionRef The reference to the function used a s converter
   * @return This instance for method chaining
   */
  <I,O> ConfigureDsl usingConverter(FunctionRef<I,O> converterFunctionRef);

  /**
   * Registers a converter function though its reference that will need access to b2b dsl as part of
   * the conversion process. Usually for delegating the conversion of one or more parts of the original object (nesting conversions).
   * @param converterFunction The function to use as converter
   * @return This instance for method chaining
   */
  <I,O> ConfigureDsl usingConverter(BiFunctionRef<I,Bean2beanTask,O> converterFunction);

  /**
   * Registers a converter function through its reference that requires no parameters as input for the conversion.<br>
   *   Usually these converters are used as generators to instantiate other types
   * @param converterFunction The function to use a generator
   * @return This instance for method chaining
   */
  <O> ConfigureDsl usingConverter(SupplierRef<O> converterFunction);

  /**
   * Registers a converter function through its reference that generates no output as result of the conversion.<br>
   *   Usually these converters are used as destructors of intances to release resources
   * @param converterFunction The function to use as destructor
   * @return This instance for method chaining
   */
  <I> ConfigureDsl usingConverter(ConsumerRef<I> converterFunction);


}
