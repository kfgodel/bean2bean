package net.sf.kfgodel.bean2bean.impl.transfunctions;

import ar.com.kfgodel.nary.api.optionals.Optional;
import net.sf.kfgodel.bean2bean.impl.transfunctions.partials.SourceDefinedTransfunctionDefinition;


/**
 * This type represents the access point to transfuctions for primitive types
 * Created by kfgodel on 05/07/16.
 */
public interface Delta {

  /**
   * Defines String as the type of input to get a transfunction for the conversion
   * @return The uncomplete definition to complete by defining a destination type
   */
  SourceDefinedTransfunctionDefinition<String> fromString();

  /**
   * Defines the type of input to get a transfunction for the primitive conversion.<br>
   * Returns an empty optional if the give class doesn't correspond to a primitive type
   *
   * @param sourceType The class instance that represents a primitive type
   * @param <T> The type of expected input
   * @return The uncomplete definition or an empty optional if type is not a primitive
   */
  <T> Optional<SourceDefinedTransfunctionDefinition<T>> fromType(Class<T> sourceType);
}
