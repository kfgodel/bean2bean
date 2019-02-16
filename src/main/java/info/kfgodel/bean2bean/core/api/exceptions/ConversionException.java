package info.kfgodel.bean2bean.core.api.exceptions;

import java.lang.reflect.Type;

/**
 * This type represents an error during a conversion attempt
 * Date: 16/02/19 - 18:50
 */
public class ConversionException extends Bean2BeanException {

  private final Object source;
  private final Type destinationType;

  public ConversionException(String message, Object source, Type destinationType) {
    super(message);
    this.destinationType = destinationType;
    this.source = source;
  }

  public Object getSource() {
    return source;
  }

  public Type getDestinationType() {
    return destinationType;
  }
}
