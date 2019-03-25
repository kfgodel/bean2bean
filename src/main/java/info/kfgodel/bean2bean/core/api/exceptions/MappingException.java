package info.kfgodel.bean2bean.core.api.exceptions;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;

/**
 * This class represents an error while mapping properties from an object to another
 * Date: 24/03/19 - 21:34
 */
public class MappingException extends ConversionException {

  public MappingException(String message, Bean2beanTask task, Throwable cause) {
    super(message, task, cause);
  }

  public MappingException(String message, Bean2beanTask task) {
    super(message, task);
  }
}
