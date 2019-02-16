package info.kfgodel.bean2bean.dsl.api;

import info.kfgodel.bean2bean.core.api.exceptions.Bean2BeanException;

/**
 * This type defines the available options for creating objects.<br>
 * Date: 16/02/19 - 18:57
 */
public interface CreateDsl {

  /**
   * Creates a new instance of the given class.<br>
   *   This operation is equivalent to converting null to the expected type
   * @param expectedTypeClass The class that indicates the type of instance to be created
   * @param <T> The type of the output
   * @return A new instance of teh expected type
   * @throws Bean2BeanException If an error occurs during creation (like a missing converter)
   */
  <T> T anInstanceOf(Class<T> expectedTypeClass) throws Bean2BeanException;


  /**
   * Creates a new array for the given type and size.<br>
   *   This operation is equivalent to converting the array size integeer to the expected type
   * @param arraySize The expected array size
   * @param aClass Type of the array
   * @param <E> The type of element the array is expected to have
   * @return The created array
   * @throws Bean2BeanException If an error occurs during creation (like a missing converter)
   */
  <E> E[] anArrayOf(int arraySize, Class<E[]> aClass) throws Bean2BeanException;
}
