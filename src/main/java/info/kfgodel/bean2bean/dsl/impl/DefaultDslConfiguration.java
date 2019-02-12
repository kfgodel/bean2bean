package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.api.Bean2beanConfiguration;
import info.kfgodel.bean2bean.core.impl.Bean2BeanImpl;
import info.kfgodel.bean2bean.core.impl.DefaultBean2BeanConfiguration;
import info.kfgodel.bean2bean.dsl.api.B2bDslConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * This type defines the defaults for a b2b configuration
 * Date: 10/02/19 - 23:06
 */
public class DefaultDslConfiguration implements B2bDslConfig {

  private List<Function> converterFunctions;

  public static DefaultDslConfiguration create() {
    DefaultDslConfiguration config = new DefaultDslConfiguration();
    config.converterFunctions = new ArrayList<>();
    return config;
  }

  @Override
  public B2bDslConfig usingConverter(Function<?, ?> converterFunction) {
    converterFunctions.add(converterFunction);
    return this;
  }

  @Override
  public Bean2bean createBean2bean() {
    Bean2beanConfiguration config = createConfiguration();
    return Bean2BeanImpl.create(config);
  }

  private Bean2beanConfiguration createConfiguration() {
    DefaultBean2BeanConfiguration configuration = DefaultBean2BeanConfiguration.create();
    converterFunctions.forEach(configuration.getRegistry()::store);
    return configuration;
  }
}
