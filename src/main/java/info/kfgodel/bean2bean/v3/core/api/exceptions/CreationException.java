package info.kfgodel.bean2bean.v3.core.api.exceptions;

import java.lang.reflect.Type;

/**
 * This class represents an error during the creation of an instance
 * Date: 16/02/19 - 18:54
 */
public class CreationException extends Bean2BeanException {

  private final Type expectedType;


  public CreationException(String message, Type expectedType) {
    super(message);
    this.expectedType = expectedType;
  }

  public CreationException(String message, Type expectedType, Throwable cause) {
    super(message, cause);
    this.expectedType = expectedType;
  }

  public Type getExpectedType() {
    return expectedType;
  }
}
