package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.impl.Bean2BeanImpl;
import info.kfgodel.bean2bean.core.impl.registry.domains.DomainCalculator;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.ConfigureDsl;
import info.kfgodel.bean2bean.dsl.api.ConvertDsl;
import info.kfgodel.bean2bean.dsl.api.CreateDsl;
import info.kfgodel.bean2bean.dsl.api.DestroyDsl;

/**
 * This type implements the b2b dsl
 * Date: 10/02/19 - 23:02
 */
public class Dsl implements B2bDsl {

  private Bean2bean b2b;
  private DomainCalculator calculator;

  public static Dsl createWitDefaultConverters() {
    Dsl dsl = create();
    dsl.configure().usingDefaultConverters();
    return dsl;
  }

  public static Dsl create() {
    return createFor(Bean2BeanImpl.create());
  }

  public static Dsl createFor(Bean2bean b2b) {
    Dsl dsl = new Dsl();
    dsl.b2b = b2b;
    dsl.calculator = DomainCalculator.create();
    return dsl;
  }

  @Override
  public ConvertDsl convert() {
    return ConvertDslImpl.create(this);
  }

  @Override
  public CreateDsl generate() {
    return CreateDslImpl.create(this);
  }

  @Override
  public DestroyDsl destroy() {
    return DestroyDslImpl.create(this);
  }

  @Override
  public ConfigureDsl configure() {
    return ConfigureDslImpl.create(this);
  }

  public Bean2bean getCore() {
    return b2b;
  }

  @Override
  public DomainCalculator getCalculator() {
    return calculator;
  }
}
