package net.sf.kfgodel.bean2bean.impl.transfunctions.partials;

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
}
