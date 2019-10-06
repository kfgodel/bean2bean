package info.kfgodel.bean2bean.v3.core.impl;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanConfiguration;
import info.kfgodel.bean2bean.v3.core.api.registry.Bean2BeanRegistry;
import info.kfgodel.bean2bean.v3.core.impl.registry.DefaultRegistry;

/**
 * This class implements the default options for a bean2bean configuration
 * Date: 11/02/19 - 22:44
 */
public class DefaultBean2BeanConfiguration implements Bean2beanConfiguration {

  private Bean2BeanRegistry registry;

  public static DefaultBean2BeanConfiguration create() {
    DefaultBean2BeanConfiguration config = new DefaultBean2BeanConfiguration();
    config.registry = DefaultRegistry.create();
    return config;
  }

  @Override
  public Bean2BeanRegistry getRegistry() {
    return registry;
  }
}
