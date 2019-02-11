package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.dsl.api.ConvertDsl;
import info.kfgodel.bean2bean.dsl.api.SourceDefinedConversionDsl;

/**
 * Root for a conversion dsl expression
 * Date: 10/02/19 - 23:19
 */
public class ConvertDslImpl implements ConvertDsl {

  public static ConvertDslImpl create() {
    ConvertDslImpl convertDsl = new ConvertDslImpl();
    return convertDsl;
  }

  @Override
  public <I> SourceDefinedConversionDsl<I> from(I source) {
    return SourceDefinedConversionDslImpl.create();
  }
}
