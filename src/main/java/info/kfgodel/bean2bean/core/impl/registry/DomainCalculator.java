package info.kfgodel.bean2bean.core.impl.registry;

import info.kfgodel.bean2bean.core.api.registry.Domain;

import java.lang.reflect.Type;

/**
 * This class represents the object that can calculate the domain implicit in each object
 * Date: 17/02/19 - 14:30
 */
public class DomainCalculator {

  public static DomainCalculator create() {
    DomainCalculator calculator = new DomainCalculator();
    return calculator;
  }

  public Domain forType(Type aType) {
    return NamedDomain.create(aType.getTypeName());
  }
}
