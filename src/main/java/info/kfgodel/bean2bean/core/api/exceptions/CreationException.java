package info.kfgodel.bean2bean.core.api.exceptions;

/**
 * This class represents an error during the creation of an instance
 * Date: 16/02/19 - 18:54
 */
public class CreationException extends Bean2BeanException {

  public CreationException(String message) {
    super(message);
  }

  public CreationException(String message, Throwable cause) {
    super(message, cause);
  }

  public CreationException(Throwable cause) {
    super(cause);
  }
}
