package info.kfgodel.bean2bean.v3.dsl.api;

/**
 * This type defines the available operations for destruction of instances so they releases resources
 * or execute code that is necessary during its termination
 *
 * Date: 17/02/19 - 12:27
 */
public interface DestroyDsl {
  /**
   * Destroys the given object by executing the converted registered for it.<br>
   *   This is equivalent to converting the object to null.<br>
   *   Due to the nature of the language, the object is not really destroyed, that it's done by the
   *   garbage collector. however it's logically destroyed (meaning that it can't be used after this)
   * @param anObject  The object to be destroyed
   * @return This instance for method chaining
   */
  DestroyDsl object(Object anObject);
}
