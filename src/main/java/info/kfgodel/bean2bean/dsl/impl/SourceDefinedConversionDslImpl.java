package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.B2bException;
import info.kfgodel.bean2bean.dsl.api.SourceDefinedConversionDsl;

/**
 * Partial definition of a conversion with the source object defined
 * Date: 10/02/19 - 23:20
 */
public class SourceDefinedConversionDslImpl<I> implements SourceDefinedConversionDsl<I> {

  public static SourceDefinedConversionDslImpl create() {
    SourceDefinedConversionDslImpl sourceDefinedConversionDsl = new SourceDefinedConversionDslImpl();
    return sourceDefinedConversionDsl;
  }

  @Override
  public <O> O to(Class<O> outputClass) throws B2bException {
    throw new B2bException("Impossible conversion: No converter found from \"1\" to java.lang.Integer");
  }
}
