package info.kfgodel.bean2bean.dsl.api;

/**
 * This type represents the a dsl to interact with bean2bean without knowing its internals
 *
 * Date: 10/02/19 - 22:57
 */
public interface B2bDsl {
  /**
   * Initiates a conversion operation
   * @return A conversion specific DSL
   */
  ConvertDsl convert();
}
