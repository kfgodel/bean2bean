package net.sf.kfgodel.bean2bean.impl.transfunctions.partials;

import net.sf.kfgodel.bean2bean.impl.transfunctions.primitives.PrimitiveTransfunction;

import java.util.function.Function;

/**
 * This type has string as a predefined source
 * Created by kfgodel on 05/07/16.
 */
public class StringDefinedTransfuctionDefinition implements SourceDefinedTransfunctionDefinition<String> {

  public static StringDefinedTransfuctionDefinition create() {
    StringDefinedTransfuctionDefinition definition = new StringDefinedTransfuctionDefinition();
    return definition;
  }

  @Override
  public Function<String, Long> toLong() {
    return PrimitiveTransfunction::stringToLong;
  }
}
