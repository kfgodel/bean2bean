package net.sf.kfgodel.bean2bean.impl.transfunctions;

import net.sf.kfgodel.bean2bean.impl.transfunctions.partials.SourceDefinedTransfunctionDefinition;
import net.sf.kfgodel.bean2bean.impl.transfunctions.partials.StringDefinedTransfuctionDefinition;

/**
 * This type implements the transfunction definitions by different source types
 * Created by kfgodel on 05/07/16.
 */
public class DeltaImpl implements Delta {

  public static DeltaImpl create() {
    DeltaImpl delta = new DeltaImpl();
    return delta;
  }

  @Override
  public SourceDefinedTransfunctionDefinition<String> fromString() {
    return StringDefinedTransfuctionDefinition.create();
  }
}
