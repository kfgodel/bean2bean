package info.kfgodel.bean2bean.core.api.registry.definitions;

import info.kfgodel.bean2bean.core.api.registry.DomainVector;

/**
 * This type represents the definition of a converter scoped by a domain vector
 * Date: 19/02/19 - 22:19
 */
public interface VectorScopedDefinition extends ConverterDefinition {
  /**
   * Indicates the type direction this converter supports
   * @return A type vector that indicats from which type to which type the converter can handle
   */
  DomainVector getConversionVector();
}
