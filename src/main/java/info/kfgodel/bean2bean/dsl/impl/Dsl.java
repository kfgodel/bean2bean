package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.B2bDslConfig;
import info.kfgodel.bean2bean.dsl.api.ConvertDsl;

/**
 * This type implements the b2b dsl
 * Date: 10/02/19 - 23:02
 */
public class Dsl implements B2bDsl {

  private B2bDslConfig config;
  private Bean2bean b2b;

  public static Dsl create(B2bDslConfig configuration) {
    Dsl dsl = new Dsl();
    dsl.config = configuration;
    dsl.b2b = configuration.createBean2bean();
    return dsl;
  }

  @Override
  public ConvertDsl convert() {
    return ConvertDslImpl.create(this);
  }

  public Bean2bean getCore() {
    return b2b;
  }
}
