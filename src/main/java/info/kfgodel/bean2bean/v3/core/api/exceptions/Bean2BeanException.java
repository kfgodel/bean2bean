package info.kfgodel.bean2bean.v3.core.api.exceptions;

/**
 * This type represents an error on bean 2 bean
 * Date: 10/02/19 - 23:08
 */
public class Bean2BeanException extends RuntimeException {

  public Bean2BeanException(String message) {
    super(message);
  }

  public Bean2BeanException(String message, Throwable cause) {
    super(message, cause);
  }

  public Bean2BeanException(Throwable cause) {
    super(cause);
  }
}
