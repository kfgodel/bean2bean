package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.dsl.api.B2bDslConfig;

/**
 * This type defines the defaults for a b2b configuration
 * Date: 10/02/19 - 23:06
 */
public class DefaultDslConfiguration implements B2bDslConfig {

  public static DefaultDslConfiguration create() {
    DefaultDslConfiguration config = new DefaultDslConfiguration();
    return config;
  }

}
