package info.kfgodel.bean2bean.core.api.registry;

import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;

import java.util.function.Function;

/**
 * This class represents the information needed to define a converter
 *
 * Date: 13/02/19 - 19:18
 */
public interface ConverterDefinition {

  /**
   * Indicates the type direction this converter supports
   * @return A type vector that indicats from which type to which type the converter can handle
   */
  DomainVector getConversionVector();

  /**
   * @return The function that can be used as converter to transform input into output
   */
  <O> Function<ObjectConversion, O> getConverter();
}
