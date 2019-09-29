package info.kfgodel.bean2bean.v4.impl.sets;

import java.util.stream.Stream;

/**
 * This interface defines the basic contract that every conversion domain or codomain has.<br>
 *   This type is not to be confused with {@link java.util.Set}. It represents a "Set theory" set
 *   which can be used to define the input and output domains of a converter function
 *
 * Date: 26/9/19 - 23:55
 */
public interface Set {

  /**
   * Returns the ordered stream of supersets for this set.<br>
   * The stream follows the order of closest superset first (closest to this set), going up the set inclusion hierarchy.<br>
   * The stream includes this set as the first element (being the closest superset) and goes up a hierarchy natural to this
   * set type
   * @return The stream of sets that include this set
   */
  Stream<Set> getSuperSets();
}
