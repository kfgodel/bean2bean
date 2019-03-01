package info.kfgodel.bean2bean.core.api;

import info.kfgodel.bean2bean.core.api.registry.Bean2BeanRegistry;

/**
 * This type represents the interface for the core bean2bean functionality.<br>
 *   Usually you want to use this through a {@link info.kfgodel.bean2bean.dsl.impl.Dsl} instance to get a human friendlier
 *   interface
 *
 * Date: 11/02/19 - 22:41
 */
public interface Bean2bean {

  /**
   * Processes the given task and returns the result (if any)
   * @param task The task to process by this core
   * @param <O> The expected result type
   * @return The result of processing the task
   */
  <O> O process(Bean2beanTask task);

  /**
   * Allows access to the converter registry for this instance.<br>
   *   The registry is where bean2bean looks for options when doing a conversion
   * @return This instance registry
   */
  Bean2BeanRegistry getRegistry();
}
