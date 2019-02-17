package info.kfgodel.bean2bean.core.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.api.Bean2beanConfiguration;
import info.kfgodel.bean2bean.core.api.registry.Bean2BeanRegistry;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;

import java.util.Optional;
import java.util.function.Function;

/**
 * This class is the b2b core for all the functionality
 *
 * Date: 11/02/19 - 22:43
 */
public class Bean2BeanImpl implements Bean2bean {

  private Bean2beanConfiguration config;

  public static Bean2BeanImpl create() {
    Bean2BeanImpl bean2Bean = new Bean2BeanImpl();
    bean2Bean.config = DefaultBean2BeanConfiguration.create();
    return bean2Bean;
  }

  @Override
  public <O> O process(ObjectConversion conversion) {
    Optional<Function<ObjectConversion, O>> foundConverter = getRegistry().findBestConverterFor(conversion);
    return foundConverter
      .map(converter -> converter.apply(conversion))
      .orElseThrow(conversion::exceptionForMissingConverter);
  }

  @Override
  public Bean2BeanRegistry getRegistry() {
    return config.getRegistry();
  }
}
