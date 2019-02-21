package info.kfgodel.bean2bean.other.types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
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
    return SupertypeSpliterator.createAsStream(concreteSubclass)
      .filter(supertype -> isTheParameterizedVersionOf(parametrizableClass, supertype))
      .limit(1)// Don't waste time with the rest of the hierarchy once we find it
      .map(ParameterizedType.class::cast)
      .flatMap(this::extractArguments);
  }

  private Stream<Type> extractArguments(ParameterizedType type) {
    Type[] actualTypeArguments = type.getActualTypeArguments();
    if(actualTypeArguments == null){
      // Don't trust the method's javadoc. I'm sure null is a possible return value
      return Stream.empty();
    }
    return Arrays.stream(actualTypeArguments);
  }

  private <T> boolean isTheParameterizedVersionOf(Class<T> parametrizableClass, Type type) {
    if (!(type instanceof ParameterizedType)) {
      return false;
    }
    ParameterizedType parameterizedType = (ParameterizedType) type;
    boolean isTheClassWeLookFor = parameterizedType.getRawType().equals(parametrizableClass);
    return isTheClassWeLookFor;
  }

}
