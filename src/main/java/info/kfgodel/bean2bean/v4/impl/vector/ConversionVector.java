package info.kfgodel.bean2bean.v4.impl.vector;

/**
 * This interface represents a vector indicating direction for a conversion. It has source and a target
 * to indicate what's the input and the expected output
 *
 * Date: 24/9/19 - 17:07
 */
public interface ConversionVector {

  /**
   * The object that represents this vector's origin point. It may be a type indicating which set of elements
   * the input is part from
   * @return The object that represents the converter function input domain
   */
  Object getSource();

  /**
   * The object that represents this vector's destination point. It may be a type indicating which set of elements
   * the input is expected to belog to, and therefore what kind of mapping is needed
   * @return The object that represents the converter function output codomain
   */
  Object getTarget();
}
