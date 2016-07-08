package net.sf.kfgodel.bean2bean.impl.transfunctions.partials;

import ar.com.kfgodel.nary.api.optionals.Optional;

import java.util.function.Function;

/**
 * This type represents a half definition of a desired transfunction
 * Created by kfgodel on 05/07/16.
 */
public interface SourceDefinedTransfunctionDefinition<I> {
  /**
   * @return The transfunction to convert to a long type from the source type
   */
  Function<I,Long> toLong();

  /**
   * Returns the transfunction that allows conversion from string to the expected destination type.
   * An empty optional is returned if there's no such transfunction (destination type is not a primitive)
   * @param destinationType The class instances for a primitive type
   * @param <T> The expected type after conversion
   * @return The transfunction or empty if destination type is not a primitive type
   */
  <T> Optional<Function<String,T>> toType(Class<T> destinationType);
}
