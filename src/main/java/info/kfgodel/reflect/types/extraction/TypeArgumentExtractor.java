package info.kfgodel.reflect.types.extraction;

import java.lang.reflect.Type;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * This class knows how to look for the actual type argument used to parameterize a type
 * given a class and its hierarchy
 *
 * Date: 17/02/19 - 23:23
 */
public class TypeArgumentExtractor {

  public static TypeArgumentExtractor create() {
    TypeArgumentExtractor extractor = new TypeArgumentExtractor();
    return extractor;
  }

  /**
   * Facility method to get the only argument.<br>
   *   This is a variant of {@link #getArgumentsUsedFor(Class, Class)} where only the first argument is considered
   */
  public <T> Optional<Type> getArgumentUsedFor(Class<T> parametrizableClass, Class<? extends T> concreteSubclass) {
    return getArgumentUsedFor(parametrizableClass, (Type) concreteSubclass);
  }

  /**
   * Facility method to get the only argument.<br>
   *   This is a variant of {@link #getArgumentsUsedFor(Class, Type)} where only the first argument is considered
   */
  public Optional<Type> getArgumentUsedFor(Class<?> parametrizableClass, Type concreteSubclass) {
    return getArgumentsUsedFor(parametrizableClass, concreteSubclass)
      .findFirst();
  }
  /**
   * Extracts the type arguments used to parameterize a supertype of the given concrete class.<br>
   *   It returns empty if no argument was found, or the supertype doesn't have type parameters
   * @param parametrizableClass The supertype for which we want to know the actual type arguments
   * @param concreteSubclass The concrete subclass that parameterizes the supertype
   * @param <T> The parametrizable type
   * @return The stream of type arguments found or empty
   */
  public <T> Stream<Type> getArgumentsUsedFor(Class<T> parametrizableClass, Class<? extends T> concreteSubclass) {
    return getArgumentsUsedFor(parametrizableClass, (Type) concreteSubclass);
  }

  /**
   * Extracts the type arguments used to parameterize a supertype of the given concrete type.<br>
   *   It returns empty if no argument was found, or the supertype doesn't have type parameters
   * @param parametrizableClass The supertype for which we want to know the actual type arguments
   * @param concreteType The concrete type that parameterizes the supertype
   * @param <T> The parametrizable type
   * @return The stream of type arguments found or empty
   */
  public Stream<Type> getArgumentsUsedFor(Class<?> parametrizableClass, Type concreteType) {
    TypeArgumentExtraction extraction = TypeArgumentExtraction.create(concreteType);
    return extraction.getArgumentsFor(parametrizableClass);
  }


}
