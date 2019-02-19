package info.kfgodel.bean2bean.core.impl.registry.domains;

import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.other.SupertypeSpliterator;

import javax.lang.model.type.NullType;
import java.lang.reflect.Type;
import java.util.stream.Stream;

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
    return NamedDomain.create(aType.getTypeName(), () -> this.calculateHiearchyFor(aType));
  }

  private Stream<Domain> calculateHiearchyFor(Type aType) {
    return SupertypeSpliterator.createAsStream(aType)
      .map(this::forType);
  }

  public Domain forObject(Object anObject) {
    Class<?> objectClass = NullType.class;
    if (anObject != null) {
      objectClass = anObject.getClass();
    }
    return forType(objectClass);
  }
}
