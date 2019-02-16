package info.kfgodel.bean2bean.core.api.exceptions;

/**
 * This type represents an error during a conversion attempt
 * Date: 16/02/19 - 18:50
 */
public class ConversionException extends Bean2BeanException {

  public ConversionException(String message) {
    super(message);
  }

  public ConversionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConversionException(Throwable cause) {
    super(cause);
  }
}
