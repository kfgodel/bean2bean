package info.kfgodel.bean2bean.v3.core.api.exceptions;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;

/**
 * This class represents an error on a nested conversion while processing other conversion
 * Date: 11/03/19 - 00:40
 */
public class NestedConversionException extends ConversionException {

  private final Bean2beanTask nestedTask;
  private final Bean2beanTask originalTask;

  public NestedConversionException(String message, Bean2beanTask nestedTask, Bean2beanTask originalTask, Throwable cause) {
    super(message, originalTask, cause);
    this.nestedTask = nestedTask;
    this.originalTask = originalTask;
  }

  public Bean2beanTask getNestedTask() {
    return nestedTask;
  }

  public Bean2beanTask getOriginalTask() {
    return originalTask;
  }
}
