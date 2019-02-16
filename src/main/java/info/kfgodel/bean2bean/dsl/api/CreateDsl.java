package info.kfgodel.bean2bean.dsl.api;

import info.kfgodel.bean2bean.core.api.exceptions.Bean2BeanException;

/**
 * This type defines the available options for creating objects.<br>
 *   All this operations are equivalent to converting from null to the expected
 *   result type
 * Date: 16/02/19 - 18:57
 */
public interface CreateDsl {

  /**
   * Creates a new instance of the given class.<br>
   * @param expectedTypeClass The class that indicates the type of instance to be created
   * @param <T> The type of the output
   * @return A new instance of teh expected type
   * @throws Bean2BeanException If an error occurs during creation (like a missing converter)
   */
  <T> T anInstanceOf(Class<T> expectedTypeClass) throws Bean2BeanException;
}
