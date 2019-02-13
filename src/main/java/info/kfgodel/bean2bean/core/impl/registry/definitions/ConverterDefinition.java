package info.kfgodel.bean2bean.core.impl.registry.definitions;

import info.kfgodel.bean2bean.other.TypeVector;

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
  TypeVector getConversionVector();

  /**
   * @return The function that can be used as converter to transform input into output
   */
  Function getConverter();
}
