package info.kfgodel.bean2bean.v3.dsl.api;

/**
 * This class represents the nothingness itself. Used for conversions in which an object is created or destroyed.
 * Appeared from nothing or converted to nothing.<br>
 *   The only instance is used as parameter for conversions in which a parameter is not needed (like supplier lambdas)
 *
 * Date: 10/03/19 - 22:36
 */
public class Nothing {

  /**
   * Only instance to reference the concept of nothing
   */
  public static final Nothing INSTANCE = new Nothing();

  @Override
  public String toString() {
    return "nothing";
  }
}
