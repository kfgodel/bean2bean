package info.kfgodel.bean2bean.core.api;

import info.kfgodel.bean2bean.core.api.registry.Bean2BeanRegistry;

/**
 * This interface defines the configuration options for a {@link Bean2bean} instance
 *
 * Date: 11/02/19 - 22:44
 */
public interface Bean2beanConfiguration {
  /**
   * The registry to use in the {@link Bean2bean} instance when processing tasks
   * @return The registry to use
   */
  Bean2BeanRegistry getRegistry();
}
