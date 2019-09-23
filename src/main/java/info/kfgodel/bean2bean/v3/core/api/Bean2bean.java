package info.kfgodel.bean2bean.v3.core.api;

import info.kfgodel.bean2bean.v3.core.api.exceptions.Bean2BeanException;
import info.kfgodel.bean2bean.v3.core.api.registry.Bean2BeanRegistry;

/**
 * This type represents the interface for the core bean2bean functionality.<br>
 *   Usually you want to use this through a {@link info.kfgodel.bean2bean.v3.dsl.impl.Dsl} instance to get a human friendlier
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
   * @throws Bean2BeanException If an exception is produced while processing
   */
  <O> O process(Bean2beanTask task) throws Bean2BeanException;

  /**
   * Allows access to the converter registry for this instance.<br>
   *   The registry is where bean2bean looks for options when doing a conversion
   * @return This instance registry
   */
  Bean2BeanRegistry getRegistry();
}
