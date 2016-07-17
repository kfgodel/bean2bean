package net.sf.kfgodel.bean2bean.impl.repos.condition.predicates;

import com.google.common.base.MoreObjects;

import java.util.function.Predicate;

/**
 * This type represents the condition that verifies the type of source object
 * Created by kfgodel on 17/07/16.
 */
public class IsAnInstance implements Predicate<Object> {

  private Class<?> type;
  public static final String type_FIELD = "type";


  public static IsAnInstance of(Class<?> type) {
    IsAnInstance condition = new IsAnInstance();
    condition.type = type;
    return condition;
  }

  @Override
  public boolean test(Object o) {
    return type.isInstance(o);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add(type_FIELD, type)
      .toString();
  }
}
