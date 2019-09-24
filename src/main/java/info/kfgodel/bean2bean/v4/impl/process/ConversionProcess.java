package info.kfgodel.bean2bean.v4.impl.process;

/**
 * This interface represents the ongoing process to fulfill a conversion. Since a conversion may also need nested
 * conversions, this process can have sub-processes
 * Date: 23/9/19 - 22:01
 * @param <O> Type of expected conversion result
 */
public interface ConversionProcess<O> {
  /**
   * Executes this process generating the conversion result as an object
   * @return The object that results after the conversion is fulfilled
   */
  O execute();
}
