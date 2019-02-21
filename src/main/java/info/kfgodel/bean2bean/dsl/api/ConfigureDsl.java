package info.kfgodel.bean2bean.dsl.api;

import info.kfgodel.bean2bean.core.api.registry.DomainVector;
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
  <I,O> ConfigureDsl usingConverter(Function<I,O> converterFunction);

  /**
   * Registers a function to be used as converter, scoping it be a predicate.<br>
   *   The predicate is evaluated on the conversion domain vector to decide if the converter can be used,
   *   but only if no other type scoped converter matches first.<br>
   *
   * @param converterFunction The function to register for conversions
   * @param scopePredicate The predicate that returns true on a domain vector when the converter can be used
   * @return  This instance for method chaining
   */
  ConfigureDsl usingConverter(Function<?,?> converterFunction, Predicate<DomainVector> scopePredicate);

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
   * Registers a converter that will need access to b2b dsl as part of the conversion process. Usually
   * for delegating the conversion of one or mor parts of the original object.
   * @param converterFunction The function to use as converter
   * @return This instance for method chaining
   */
  <I,O> ConfigureDsl usingConverter(BiFunction<I,B2bDsl,O> converterFunction);

  /**
   * Registers a converter that will need access to b2b dsl as part of the conversion process. Usually
   * for delegating the conversion of one or mor parts of the original object.<br>
   * The predicate is evaluated on the conversion domain vector to decide if the converter can be used,
   *   but only if no other type scoped converter matches first.<br>
   * @param converterFunction The function to use as converter
   * @param scopePredicate The predicate that returns true on a domain vector when the converter can be used
   * @return This instance for method chaining
   */
  ConfigureDsl usingConverter(BiFunction<?,B2bDsl,?> converterFunction, Predicate<DomainVector> scopePredicate);

  /**
   * Registers a converter function though its reference that will need access to b2b dsl as part of
   * the conversion process. Usually for delegating the conversion of one or mor parts of the original object.
   * @param converterFunction The function to use as converter
   * @return This instance for method chaining
   */
  <I,O> ConfigureDsl usingConverter(BiFunctionRef<I,B2bDsl,O> converterFunction);

  /**
   * Registers a converter function that requires no parameters as input for the conversion.<br>
   *   Usually these converters are used as generators to instantiate other types
   * @param converterFunction The function to use as generator
   * @return This instance for method chaining
   */
  <O> ConfigureDsl usingConverter(Supplier<O> converterFunction);

  /**
   * Registers a converter function that requires no parameters as input for the conversion.<br>
   * Usually these converters are used as generators to instantiate other types <br>
   * The predicate is evaluated on the conversion domain vector to decide if the converter can be used,
   *    but only if no other type scoped converter matches first.<br>
   * @param converterFunction The function to use as generator
   * @param scopePredicate The predicate that returns true on a domain vector when the converter can be used
   * @return This instance for method chaining
   */
  ConfigureDsl usingConverter(Supplier<?> converterFunction, Predicate<DomainVector> scopePredicate);

  /**
   * Registers a converter function through its reference that requires no parameters as input for the conversion.<br>
   *   Usually these converters are used as generators to instantiate other types
   * @param converterFunction The function to use a generator
   * @return This instance for method chaining
   */
  <O> ConfigureDsl usingConverter(SupplierRef<O> converterFunction);

  /**
   * Registers a converter function that generates no output as result of the conversion.<br>
   *   Usually these converters are used as destructors of intances to release resources
   * @param converterFunction The function to use as destructor
   * @return This instance for method chaining
   */
  <I> ConfigureDsl usingConverter(Consumer<I> converterFunction);

  /**
   * Registers a converter function that generates no output as result of the conversion.<br>
   * Usually these converters are used as destructors of intances to release resources.<br>
   * The predicate is evaluated on the conversion domain vector to decide if the converter can be used,
   *     but only if no other type scoped converter matches first.<br>
   * @param converterFunction The function to use as destructor
   * @param scopePredicate The predicate that returns true on a domain vector when the converter can be used
   * @return This instance for method chaining
   */
  ConfigureDsl usingConverter(Consumer<?> converterFunction, Predicate<DomainVector> scopePredicate);

  /**
   * Registers a converter function through its reference that generates no output as result of the conversion.<br>
   *   Usually these converters are used as destructors of intances to release resources
   * @param converterFunction The function to use as destructor
   * @return This instance for method chaining
   */
  <I> ConfigureDsl usingConverter(ConsumerRef<I> converterFunction);

}
