package net.sf.kfgodel.bean2bean.impl.repos.primitive;

import ar.com.kfgodel.nary.api.optionals.Optional;
import net.sf.kfgodel.bean2bean.impl.repos.primitive.partials.SourceDefinedPrimitiveTransfunctionDefinition;

import java.util.Set;


/**
 * This type represents the repository for transfuctions for primitive types
 * 
 * Created by kfgodel on 05/07/16.
 */
public interface PrimitiveTransfunctionRepo {

  /**
   * Defines String as the type of input to get a transfunction for the conversion
   * @return The uncomplete definition to complete by defining a destination type
   */
  SourceDefinedPrimitiveTransfunctionDefinition<String> fromString();

  /**
   * Defines the type of input to get a transfunction for the primitive conversion.<br>
   * Returns an empty optional if the give class doesn't correspond to a primitive type
   *
   * @param sourceType The class instance that represents a primitive type
   * @param <T> The type of expected input
   * @return The uncomplete definition or an empty optional if type is not a primitive
   */
  <T> Optional<SourceDefinedPrimitiveTransfunctionDefinition<T>> fromType(Class<T> sourceType);

  /**
   * Defines the set of primitive types this repo supports for conversions
   * @return The set of classes that represent all the primitive types
   */
  Set<Class<?>> getPrimitiveTypes();
}
