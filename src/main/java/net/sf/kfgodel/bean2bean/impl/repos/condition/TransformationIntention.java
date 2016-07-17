package net.sf.kfgodel.bean2bean.impl.repos.condition;

/**
 * This type represents the desire of converting the source object into the destination.
 * Depending on the source an destination types what the transformation means
 *
 * Created by kfgodel on 16/07/16.
 */
public interface TransformationIntention {
  /**
   * The objet that represents the source state
   * @return The object to transform
   */
  Object source();

  /**
   * The object that represents the desired final state after transformation
   * @return The expected transformation result
   */
  Object destination();
}
