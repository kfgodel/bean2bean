package info.kfgodel.bean2bean.v4.impl.vector;

import java.util.Objects;

/**
 * Default implementation for a conversion vector
 *
 * Date: 24/9/19 - 17:12
 */
public class Vector implements ConversionVector {

  private Object source;
  private Object target;

  public static Vector create(Object source, Object target) {
    Vector vector = new Vector();
    vector.source = source;
    vector.target = target;
    return vector;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Vector vector = (Vector) o;
    return source.equals(vector.source) &&
      target.equals(vector.target);
  }

  @Override
  public int hashCode() {
    return Objects.hash(source, target);
  }

  @Override
  public String toString() {
    return source + " -> " + target;
  }

  @Override
  public Object getSource() {
    return source;
  }

  @Override
  public Object getTarget() {
    return target;
  }
}
