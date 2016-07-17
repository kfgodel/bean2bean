package net.sf.kfgodel.bean2bean.impl.repos.condition.predicates;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.function.Predicate;

/**
 * This type represents the condition that indicates a type of expected object
 * Created by kfgodel on 17/07/16.
 */
public class ExpectingAnInstance implements Predicate<Object> {

  private Set<Class<?>> expectedTypes;
  public static final String expectedTypes_FIELD = "expectedTypes";

  public static ExpectingAnInstance of(Class<?> expectedType) {
    return ofAny(Sets.newHashSet(expectedType));
  }
  public static ExpectingAnInstance ofAny(Set<Class<?>> expectedTypes) {
    ExpectingAnInstance condition = new ExpectingAnInstance();
    condition.expectedTypes = expectedTypes;
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
    if(that.expectedTypes.size() != 1){
      // Only matche if destination is one of the expected
      return false;
    }
    return this.expectedTypes.contains(that.getExpectedType());
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add(expectedTypes_FIELD, expectedTypes)
      .toString();
  }

  public Set<Class<?>> getExpectedTypes() {
    return expectedTypes;
  }

  /**
   * Assumes there's only one expected type and returns it
   */
  public Class<?> getExpectedType() {
    if(expectedTypes.size() != 1){
      throw new IllegalStateException("Can't call this method if there's 0 o more than one expected types");
    }
    return expectedTypes.stream().findFirst().get();
  }
}
