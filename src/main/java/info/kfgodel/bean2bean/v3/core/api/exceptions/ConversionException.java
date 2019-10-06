package info.kfgodel.bean2bean.v3.core.api.exceptions;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;

/**
 * This type represents an error during a conversion attempt
 * Date: 16/02/19 - 18:50
 */
public class ConversionException extends Bean2BeanException {

  private final Bean2beanTask task;

  public ConversionException(String message, Bean2beanTask task, Throwable cause) {
    super(message, cause);
    this.task = task;
  }
  public ConversionException(String message, Bean2beanTask task) {
    super(message);
    this.task = task;
  }

  public Bean2beanTask getTask() {
    return task;
  }
}
