package info.kfgodel.bean2bean.v3.converters.mapping;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;

/**
 * This type represents a single mapping from a property in one object to a property in another.<br>
 *   Depending on the type of mapping a conversion may be needed before assignment on target property
 *
 * Date: 24/03/19 - 23:52
 */
public interface PropertyMapping {

  /**
   * Maps a property on the source object into a property on the target object.<br> A conversion
   * may be needed before assignment. In that case the task dsl is used
   * @param source The object to take the value from
   * @param task The task to use for any conversion needed
   * @param target The target object to assign the value on
   */
  void applyOn(Object source, Bean2beanTask task, Object target);
}
