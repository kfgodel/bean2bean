package info.kfgodel.bean2bean.core.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.api.Bean2beanConfiguration;
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

  public static Bean2BeanImpl create(Bean2beanConfiguration configuration) {
    Bean2BeanImpl bean2Bean = new Bean2BeanImpl();
    bean2Bean.config = configuration;
    return bean2Bean;
  }

  @Override
  public <O> O process(ObjectConversion task) {
    Optional<Function<ObjectConversion, O>> foundProcess = config.getRegistry().findBestConverterFor(task);
    return foundProcess
      .map(process -> process.apply(task))
      .orElseThrow(task::exceptionForMissingProcess);
  }
}
