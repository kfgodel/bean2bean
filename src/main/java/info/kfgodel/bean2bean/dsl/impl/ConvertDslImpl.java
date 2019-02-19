package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.impl.registry.domains.DomainCalculator;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.ConvertDsl;
import info.kfgodel.bean2bean.dsl.api.SourceDefinedConversionDsl;

/**
 * Root for a conversion dsl expression
 * Date: 10/02/19 - 23:19
 */
public class ConvertDslImpl implements ConvertDsl {

  private B2bDsl parentDsl;

  public static ConvertDslImpl create(B2bDsl dsl) {
    ConvertDslImpl convertDsl = new ConvertDslImpl();
    convertDsl.parentDsl = dsl;
    return convertDsl;
  }

  @Override
  public <I> SourceDefinedConversionDsl<I> from(I value) {
    Domain sourceDomain = getCalculator().forObject(value);
    return SourceDefinedConversionDslImpl.create(this, value, sourceDomain);
  }

  public Bean2bean getCore() {
    return parentDsl.getCore();
  }

  public DomainCalculator getCalculator() {
    return parentDsl.getCalculator();
  }
}
