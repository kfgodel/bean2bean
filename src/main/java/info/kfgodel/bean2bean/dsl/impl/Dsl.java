package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.impl.Bean2BeanImpl;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.ConfigureDsl;
import info.kfgodel.bean2bean.dsl.api.ConvertDsl;
import info.kfgodel.bean2bean.dsl.api.CreateDsl;

/**
 * This type implements the b2b dsl
 * Date: 10/02/19 - 23:02
 */
public class Dsl implements B2bDsl {

  private Bean2bean b2b;

  public static Dsl create() {
    return createFor(Bean2BeanImpl.create());
  }

  public static Dsl createFor(Bean2bean b2b) {
    Dsl dsl = new Dsl();
    dsl.b2b = b2b;
    return dsl;
  }

  @Override
  public ConvertDsl convert() {
    return ConvertDslImpl.create(this);
  }

  @Override
  public CreateDsl make() {
    return CreateDslImpl.create(this);
  }

  @Override
  public ConfigureDsl configure() {
    return ConfigureDslImpl.create(this);
  }

  public Bean2bean getCore() {
    return b2b;
  }
}
