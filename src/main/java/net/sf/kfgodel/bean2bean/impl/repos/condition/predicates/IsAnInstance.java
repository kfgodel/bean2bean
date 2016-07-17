package net.sf.kfgodel.bean2bean.impl.repos.condition.predicates;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.function.Predicate;

/**
 * This type represents the condition that verifies the type of source object
 * Created by kfgodel on 17/07/16.
 */
public class IsAnInstance implements Predicate<Object> {

  private Set<Class<?>> types;
  public static final String types_FIELD = "types";


  public static IsAnInstance of(Class<?> type) {
    return ofAny(Sets.newHashSet(type));
  }

  public static IsAnInstance ofAny(Set<Class<?>> types) {
    IsAnInstance condition = new IsAnInstance();
    condition.types = types;
    return condition;
  }


  @Override
  public boolean test(Object o) {
    for (Class<?> type : types) {
      if(type.isInstance(o)){
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
      .add(types_FIELD, types)
      .toString();
  }

}
