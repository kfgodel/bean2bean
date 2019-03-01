package info.kfgodel.bean2bean.core.api;

import info.kfgodel.bean2bean.core.api.registry.DomainVector;

/**
 * This type represents a task that can be processed by a {@link Bean2bean} core
 * Date: 12/02/19 - 00:08
 */
public interface Bean2beanTask {
  /**
   * The vector that indicates from which domain to which codomain this task generates a result
   * @return A domain vector indicating the direction for a conversion
   */
  DomainVector getConversionVector();

  /**
   * The object that is the source of the conversion from which a result must be generated
   * @return The input for the conversion
   */
  Object getSource();
}
