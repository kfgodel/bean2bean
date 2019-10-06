package info.kfgodel.bean2bean.v3.dsl.api;

/**
 * This type defines the available options when converting between objects
 * Date: 10/02/19 - 23:12
 */
public interface ConvertDsl {

  /**
   * Defines the source object for a conversion in which that object
   * will be transformed into something else, yet to be defined
   * @param source The object to convert
   * @param <I> The type of source object
   * @return A dsl to define the expected output of the conversion
   */
  <I> SourceDefinedConversionDsl<I> from(I source);
}
