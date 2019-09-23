package info.kfgodel.bean2bean.v3.core.api.registry;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * This type represents a set of elements that can define a domain or codomain of a function.<br>
 *   The domain of a function determines the set of acceptable input elements, and the codomain defines the set
 *   of potential output elements for a function.<br>
 * In bean2bean the domain and codomain are used to define the scope of applicability for a converter function.<br>
 * Usually a java type defines an implicit domain of all the objects that are instances of that type <br>
 * <br>
 * The contract of a domain includes defining optimal {@link #equals(Object)} and {@link #hashCode()} so two domains can
 * be compared for equality, and domains can be used on {@link java.util.Map} instances without performance penalties.
 *
 * Date: 17/02/19 - 14:18
 */
public interface Domain {
  /**
   * @return The name that identifies this domain
   */
  String getName();

  /**
   * The domain hiearchy of which this domain is part.<br>
   *   The hierarchy starts on this domain and goes up to more generic domains
   * @return The ordered stream of this domain super domains
   */
  Stream<Domain> getHierarchy();

  /**
   * Returns the first domain on the hiearchy that doesn't have parameters
   * @return The super domain on this hierarchy that doesn't have type parameters
   */
  Optional<Domain> getUnparameterized();

  /**
   * Indicates if this domain represents a subset of other domain with parameters to define it.<br>
   *   Some types represent domains that have type parameters to restrict them in order to get sub-domains.<br>
   *   This is true if this domain is a paramaterized sub-domain
   * @return true if this domain uses type parameters (regardless of whether they are variables or concrete types)
   */
  boolean isParameterized();

  /**
   * Indicates if this domain, as a set of possible objects, is included in the given domain (as a super set).<br>
   *   This is the same as asking if this domain is assignable to the given domain. If an instance belonging to
   *   this domain also belongs to the given domain
   * @param otherDomain The domain to verify the inclusion relationship
   * @return true if this is the same or a sub-domain of the given. False otherwise
   */
  boolean isIncludedIn(Domain otherDomain);



}
