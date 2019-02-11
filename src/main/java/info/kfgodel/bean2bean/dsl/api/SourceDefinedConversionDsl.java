package info.kfgodel.bean2bean.dsl.api;

import info.kfgodel.bean2bean.core.api.B2bException;

/**
 * This type defines the available options for a conversion once the
 * source object is defined
 *
 * Date: 10/02/19 - 23:14
 */
public interface SourceDefinedConversionDsl<I> {

  /**
   * Converts the source object into an instance of the expected class
   * @param outputClass The class that indicates the type of expected output for
   *                    the conversion
   * @param <O> The type of expeced output
   * @return The generated output after the conversion
   * @throws B2bException If an error happened during conversion (like failed
   * or missing converter for the transformation)
   */
  <O> O to(Class<O> outputClass) throws B2bException;
}
