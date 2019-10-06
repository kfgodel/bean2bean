package info.kfgodel.bean2bean.v3.core.api.registry;

import java.util.Objects;

/**
 * This class represents a conversion direction from a domain to other
 * Date: 17/02/19 - 15:30
 */
public class DomainVector {

  private Domain source;
  private Domain target;

  public Domain getSource() {
    return source;
  }

  public Domain getTarget() {
    return target;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof DomainVector)) return false;
    DomainVector that = (DomainVector) o;
    return getSource().equals(that.getSource()) &&
      getTarget().equals(that.getTarget());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getSource(), getTarget());
  }

  public static DomainVector create(Domain source, Domain target) {
    DomainVector vector = new DomainVector();
    vector.source = source;
    vector.target = target;
    return vector;
  }

  @Override
  public String toString() {
    return source + " -> " + target;
  }
}
