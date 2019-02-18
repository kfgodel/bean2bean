package info.kfgodel.bean2bean.core.api.registry;

import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;
import info.kfgodel.bean2bean.core.impl.registry.DomainVector;
import info.kfgodel.bean2bean.core.impl.registry.definitions.ConverterDefinition;

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
   * Stores the given converter definition in this registry so the converter can be used on conversions
   * @param definition The descripcion of the converter
   * @return This instance for method chaining
   */
  Bean2BeanRegistry store(ConverterDefinition definition);
}
