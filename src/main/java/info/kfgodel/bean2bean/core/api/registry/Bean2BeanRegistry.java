package info.kfgodel.bean2bean.core.api.registry;

import info.kfgodel.bean2bean.core.api.registry.definitions.ConverterDefinition;
import info.kfgodel.bean2bean.core.api.registry.definitions.PredicateScopedDefinition;
import info.kfgodel.bean2bean.core.api.registry.definitions.VectorScopedDefinition;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;

import java.util.Optional;
import java.util.function.Function;

/**
 * This type represents the place where all the converters are registered and one is selected
 * when a task needs to be processed
 *
 * Date: 12/02/19 - 00:11
 */
public interface Bean2BeanRegistry {

  /**
   * Searches for the best available converter for the given domain transformation
   * @param conversionVector The vector that indicates from which domain to which codomain the conversion shouldbe
   * @param <O> The type of expected output
   * @return The process found, or empty if none exists for the input
   */
  <O> Optional<Function<ObjectConversion,O>> findBestConverterFor(DomainVector conversionVector);


  /**
   * Stores the given converter definition in this registry so the converter can be used later for conversions.<br>
   *   This method can be used when the type of definition is not previously known. It's a facility method for
   *   deciding which version of this method to call.
   * @param definition The definition to store
   * @return This instance for method chaining
   */
  Bean2BeanRegistry store(ConverterDefinition definition);

  /**
   * Stores the given converter definition in this registry so the converter can be used on conversions<br>
   *   This method allows adding definitions that are based on domain vectors to limit applicability
   * @param definition The description of the converter
   * @return This instance for method chaining
   */
  Bean2BeanRegistry store(VectorScopedDefinition definition);

  /**
   * Stores the given converter definition in this registry so teh converter can be found for conversiones.<br>
   *   This method allows adding definitions that are based on predicates to limit applicability of the converter
   * @param definition The description of the converter
   * @return This instance for method chaining
   */
  Bean2BeanRegistry store(PredicateScopedDefinition definition);
}
