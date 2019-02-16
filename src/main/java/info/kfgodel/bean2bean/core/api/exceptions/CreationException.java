package info.kfgodel.bean2bean.core.api.exceptions;

import java.lang.reflect.Type;

/**
 * This class represents an error during the creation of an instance
 * Date: 16/02/19 - 18:54
 */
public class CreationException extends Bean2BeanException {

  private final Type expectedType;

  public <T> CreationException(String message, Type expectedType, Throwable cause) {
    super(message, cause);
    this.expectedType = expectedType;
  }
}
