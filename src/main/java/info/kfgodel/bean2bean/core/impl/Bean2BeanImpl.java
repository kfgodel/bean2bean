package info.kfgodel.bean2bean.core.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.api.Bean2beanConfiguration;
import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.api.registry.Bean2BeanRegistry;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;

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
  public <O> O process(Bean2beanTask task) {
    DomainVector conversionVector = task.getConversionVector();
    Optional<Function<Bean2beanTask, O>> foundConverter = getRegistry().findBestConverterFor(conversionVector);
    // We need 2 different variables to help the compiler figuring type of `O`
    Function<Bean2beanTask, O> converter = foundConverter
      .orElseThrow(() -> exceptionForMissingConverter(task));
    return converter.apply(task);
  }

  public ConversionException exceptionForMissingConverter(Bean2beanTask task) {
    DomainVector domainVector = task.getConversionVector();
    return new ConversionException("No converter found from " +
      task.getSource() + domainVector.getSource() +
      " to " + domainVector.getTarget(), task);
  }

  @Override
  public Bean2BeanRegistry getRegistry() {
    return config.getRegistry();
  }
}
