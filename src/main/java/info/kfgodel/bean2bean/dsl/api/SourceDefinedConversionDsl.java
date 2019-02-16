package info.kfgodel.bean2bean.dsl.api;

import info.kfgodel.bean2bean.core.api.exceptions.Bean2BeanException;
import info.kfgodel.bean2bean.other.TypeRef;

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
   * @param <O> The type of expected output
   * @return The generated output after the conversion
   * @throws Bean2BeanException If an error happened during conversion (like failed
   * or missing converter for the transformation)
   */
  <O> O to(Class<O> outputClass) throws Bean2BeanException;

  /**
   * Converts the source object into an instance of the expected class
   * @param outputTypeRef The type reference that indicates the type of expected output for
   *                    the conversion (usually a parameterized type)
   * @param <O> The type of expected output
   * @return The generated output after the conversion
   * @throws Bean2BeanException If an error happened during conversion (like failed
   * or missing converter for the transformation)
   */
  <O> O to(TypeRef<O> outputTypeRef) throws Bean2BeanException;
}
