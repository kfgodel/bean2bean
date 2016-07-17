package net.sf.kfgodel.bean2bean.impl.repos.condition.predicates;

import com.google.common.base.MoreObjects;

import java.util.function.Predicate;

/**
 * This type represents the condition that indicates a type of expected object
 * Created by kfgodel on 17/07/16.
 */
public class ExpectingAnInstance implements Predicate<Object> {

  private Class<?> expectedType;
  public static final String expectedType_FIELD = "expectedType";

  public static ExpectingAnInstance of(Class<?> expectedType) {
    ExpectingAnInstance condition = new ExpectingAnInstance();
    condition.expectedType = expectedType;
    return condition;
  }

  @Override
  public boolean test(Object o) {
    // Only if the same type is expected we consider it to match this condition
    if (this == o){
      return true;
    }
    if (!(o instanceof ExpectingAnInstance)){
      return false;
    }

    ExpectingAnInstance that = (ExpectingAnInstance) o;
    return expectedType.equals(that.expectedType);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add(expectedType_FIELD, expectedType)
      .toString();
  }
}
