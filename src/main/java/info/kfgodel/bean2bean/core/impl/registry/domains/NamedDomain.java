package info.kfgodel.bean2bean.core.impl.registry.domains;

import info.kfgodel.bean2bean.core.api.registry.Domain;

import javax.lang.model.type.NullType;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * This class implements a domain based purely on its name
 * Date: 17/02/19 - 16:08
 */
public class NamedDomain implements Domain {

  private String name;
  private Supplier<Stream<Domain>> hierarchyGenerator;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Stream<Domain> getHierarchy() {
    return hierarchyGenerator.get();
  }

  @Override
  public Optional<Domain> getUnparameterized() {
    return getHierarchy()
      .filter(domain -> domain.getName().contains(".")) // This is a quick solution that may need to be questioned
      .filter(domain -> !domain.isParameterized())
      .findFirst();
  }

  @Override
  public boolean isParameterized() {
    return this.getName().contains("<") && this.getName().contains(">");
  }

  @Override
  public boolean isIncludedIn(Domain otherDomain) {
    if(getName().contains(NullType.class.getTypeName())){
      // This reflects the language decision of including null as a subtype of every other type
      return true;
    }
    return getHierarchy().anyMatch(otherDomain::equals);
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
    return create(name, Stream::empty);
  }

  public static NamedDomain create(String name, Supplier<Stream<Domain>> hierarchyGenerator) {
    NamedDomain domain = new NamedDomain();
    domain.name = name;
    domain.hierarchyGenerator = hierarchyGenerator;
    return domain;
  }

}
