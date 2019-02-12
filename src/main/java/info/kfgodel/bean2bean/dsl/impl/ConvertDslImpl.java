package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.dsl.api.ConvertDsl;
import info.kfgodel.bean2bean.dsl.api.SourceDefinedConversionDsl;

/**
 * Root for a conversion dsl expression
 * Date: 10/02/19 - 23:19
 */
public class ConvertDslImpl implements ConvertDsl {

  private Dsl parentDsl;

  public static ConvertDslImpl create(Dsl dsl) {
    ConvertDslImpl convertDsl = new ConvertDslImpl();
    convertDsl.parentDsl = dsl;
    return convertDsl;
  }

  @Override
  public <I> SourceDefinedConversionDsl<I> from(I source) {
    return SourceDefinedConversionDslImpl.create(this, source);
  }

  public Bean2bean getCore() {
    return parentDsl.getCore();
  }
}
