package info.kfgodel.bean2bean.core.impl.registry.domains;

import info.kfgodel.bean2bean.core.api.registry.Domain;

import java.util.Objects;

/**
 * This class implements a domain based purely on its name
 * Date: 17/02/19 - 16:08
 */
public class NamedDomain implements Domain {

  private String name;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof NamedDomain)) return false;
    NamedDomain that = (NamedDomain) o;
    return getName().equals(that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getName());
  }

  @Override
  public String toString() {
    return "{" + name + "}";
  }

  public static NamedDomain create(String name) {
    NamedDomain domain = new NamedDomain();
    domain.name = name;
    return domain;
  }

}
