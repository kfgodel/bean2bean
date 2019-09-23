package info.kfgodel.bean2bean.v3.core.api.registry.definitions;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;

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
  <O> Function<Bean2beanTask, O> getConverter();
}
