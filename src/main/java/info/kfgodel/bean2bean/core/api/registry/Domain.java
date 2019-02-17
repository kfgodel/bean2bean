package info.kfgodel.bean2bean.core.api.registry;

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
}
