package info.kfgodel.bean2bean.core.impl.nesting;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.NestedConversionException;
import info.kfgodel.bean2bean.core.api.registry.Bean2BeanRegistry;

/**
 * This class is used as a nested reference to the real core so errors are better described
 * Date: 10/03/19 - 23:34
 */
public class NestedBean2bean implements Bean2bean {

  private Bean2beanTask originalTask;
  private Bean2bean realB2b;

  @Override
  public <O> O process(Bean2beanTask task) {
    task.linkToParent(originalTask);
    try {
      return realB2b.process(task);
    } catch (Exception e) {
      String message = createMessageFrom(e);
      throw new NestedConversionException(message, task, originalTask, e);
    }
  }

  private String createMessageFrom(Exception e) {
    return "Failed conversion from " + originalTask.describeSource() + " to " + originalTask.describeTarget() + "\n"
          + "\tdue to: " + e.getMessage();
  }

  @Override
  public Bean2BeanRegistry getRegistry() {
    return realB2b.getRegistry();
  }

  public Bean2bean getRealB2b() {
    return realB2b;
  }

  public static NestedBean2bean create(Bean2bean realB2b, Bean2beanTask originalTask) {
    NestedBean2bean nested = new NestedBean2bean();
    nested.realB2b = realB2b;
    nested.originalTask = originalTask;
    return nested;
  }

}
