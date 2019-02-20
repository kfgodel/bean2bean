package info.kfgodel.bean2bean.core.api.registry.definitions;

import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;

import java.util.function.Function;

/**
 * This class represents the information needed to define a converter
 *
 * Date: 13/02/19 - 19:18
 */
public interface ConverterDefinition {

  /**
   * @return The function that can be used as converter to transform input into output
   */
  <O> Function<ObjectConversion, O> getConverter();
}
