package info.kfgodel.bean2bean.dsl.api;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.api.registry.definitions.ConverterDefinition;
import info.kfgodel.bean2bean.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.other.references.ConsumerRef;
import info.kfgodel.bean2bean.other.references.FunctionRef;
import info.kfgodel.bean2bean.other.references.SupplierRef;
import info.kfgodel.bean2bean.other.references.TypeRef;

import java.lang.reflect.Type;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This type represents the configuration parameters that a b2b dsl can have.<br>
 *   The scope of this instance is the default scope (all conversions)
 * Date: 10/02/19 - 23:05
 */
public interface ConfigureDsl extends ImplicitlyScopedConfigureDsl {

  /**
   * Registers a converter function using its full definition.<br>
   *   The definition contains metadata for the converter function to be used correctly on conversions
   * @param converterDefinition The converter definition to add
   * @return This instance for method chaining
   */
  ConfigureDsl useDefinition(ConverterDefinition converterDefinition);

  /**
   * Limits the applicability of a converter by using a predicate on the conversion vector, so only
   * if the predicate passes, the converter is used.<br>
   *   All predicate scoped converters are evaluated <b>after</b> vector scoped converters due to their specificity.
   *   Vector converters are usually more specific and direct than predicate converters
   * @param scopePredicate The predicate that is going to be applied on the conversion vector to determine the applicability
   *                       of a converter
   * @return The partial definition of this configuration dsl with the predicate defined
   */
  ScopedConfigureDsl scopingWith(Predicate<DomainVector> scopePredicate);

  /**
   * Limits the applicability of a converter by restricting the type of acceptable instances and the type of produced
   * results explicitly. The indicated types serve as the domain vector to be compared at on conversions
   * @param sourceType The type of input instances that can be accepted by the converter
   * @param targetType The type of results that the converter generates
   * @return The partial definition of this configuration dsl scoped by the indicated domain vector
   */
  ScopedConfigureDsl scopingTo(Type sourceType, Type targetType);

  /**
   * Limits the applicability of a converter by restricting the type of acceptable instances and the type of produced
   * results explicitly. The indicated type references serve as the domain vector to be compared at on conversions
   * @param sourceType The type of input instances that can be accepted by the converter
   * @param targetType The type of results that the converter generates
   * @return The partial definition of this configuration dsl scoped by the indicated domain vector
   */
  ScopedConfigureDsl scopingTo(TypeRef<?> sourceType, TypeRef<?> targetType);

  /**
   * Limits the applicability of a converter by using its type parameters to restrict its usage to valid instances.<br>
   * If no type parameters can be obtained using reflection on the lambda class, then {@link Object} will be used
   * instead for input or output types scoping. <br>
   * <br>
   * Note, this may collide with other converters if carelessly used. Use lambda refs like {@link FunctionRef}
   * to explicitly define lambda parameter types<br>
   *
   * @return The partial definition of this configuration dsl scoping to implicit types
   */
  ImplicitlyScopedConfigureDsl scopingByTypeArguments();


  @Override
  <I, O> ConfigureDsl useConverter(Function<I, O> converterFunction);

  @Override
  <I, O> ConfigureDsl useConverter(BiFunction<I, Bean2beanTask, O> converterFunction);

  @Override
  <O> ConfigureDsl useConverter(Supplier<O> converterFunction);

  @Override
  <I> ConfigureDsl useConverter(Consumer<I> converterFunction);

  @Override
  <I, O> ConfigureDsl useConverter(FunctionRef<I, O> converterFunctionRef);

  @Override
  <I, O> ConfigureDsl useConverter(BiFunctionRef<I, Bean2beanTask, O> converterFunction);

  @Override
  <O> ConfigureDsl useConverter(SupplierRef<O> converterFunction);

  @Override
  <I> ConfigureDsl useConverter(ConsumerRef<I> converterFunction);
}
