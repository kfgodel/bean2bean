package info.kfgodel.bean2bean.core.api;

/**
 * This type represents an error on bean 2 bean
 * Date: 10/02/19 - 23:08
 */
public class B2bException extends RuntimeException {

  public B2bException(String message) {
    super(message);
  }

  public B2bException(String message, Throwable cause) {
    super(message, cause);
  }

  public B2bException(Throwable cause) {
    super(cause);
  }
}
