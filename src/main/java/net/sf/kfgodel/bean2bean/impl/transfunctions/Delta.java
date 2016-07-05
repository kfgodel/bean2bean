package net.sf.kfgodel.bean2bean.impl.transfunctions;

import net.sf.kfgodel.bean2bean.impl.transfunctions.partials.SourceDefinedTransfunctionDefinition;

/**
 * This type represents the access point to transfuctions for primitive types
 * Created by kfgodel on 05/07/16.
 */
public interface Delta {

  /**
   * Defines the type of input to get a transfunction for the conversion
   * @return The uncomplete definition to complete by defining a destination type
   */
  SourceDefinedTransfunctionDefinition<String> fromString();
}
