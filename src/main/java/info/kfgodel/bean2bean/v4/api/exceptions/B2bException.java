package info.kfgodel.bean2bean.v4.api.exceptions;

/**
 * This class represents the general type for all the exceptions generated in b2b
 * Date: 23/9/19 - 16:23
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
