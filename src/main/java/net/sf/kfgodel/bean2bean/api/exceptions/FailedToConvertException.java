package net.sf.kfgodel.bean2bean.api.exceptions;

/**
 * This type represents a failed attempt to convert a valur
 * Created by kfgodel on 05/07/16.
 */
public class FailedToConvertException extends Bean2beanException {

  public FailedToConvertException(String message) {
    super(message);
  }

  public FailedToConvertException(String message, Throwable cause) {
    super(message, cause);
  }

  public FailedToConvertException(Throwable cause) {
    super(cause);
  }
}
