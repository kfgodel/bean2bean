package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.api.Bean2beanConfiguration;
import info.kfgodel.bean2bean.core.impl.Bean2BeanImpl;
import info.kfgodel.bean2bean.core.impl.DefaultBean2BeanConfiguration;
import info.kfgodel.bean2bean.core.impl.registry.VectorExtractor;
import info.kfgodel.bean2bean.core.impl.registry.definitions.ConverterDefinition;
import info.kfgodel.bean2bean.core.impl.registry.definitions.FunctionAsConverterDefinition;
import info.kfgodel.bean2bean.dsl.api.B2bDslConfig;
import info.kfgodel.bean2bean.other.FunctionRef;
import info.kfgodel.bean2bean.other.TypeVector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * This type defines the defaults for a b2b configuration
 * Date: 10/02/19 - 23:06
 */
public class DefaultDslConfiguration implements B2bDslConfig {

  private List<ConverterDefinition> converterDefinitions;

  public static DefaultDslConfiguration create() {
    DefaultDslConfiguration config = new DefaultDslConfiguration();
    config.converterDefinitions = new ArrayList<>();
    return config;
  }

  @Override
  public B2bDslConfig usingConverter(Function<?, ?> converterFunction) {
    TypeVector implicitVector = VectorExtractor.create().apply(converterFunction);
    return usingConverter(implicitVector, converterFunction);
  }

  @Override
  public B2bDslConfig usingConverter(FunctionRef<?, ?> converterFunctionRef) {
    TypeVector implicitVector = converterFunctionRef.getInputOutputVector();
    Function<?, ?> function = converterFunctionRef.getFunction();
    return usingConverter(implicitVector, function);
  }

  private B2bDslConfig usingConverter(TypeVector conversionVector, Function converterFunction) {
    ConverterDefinition converterDefinition = FunctionAsConverterDefinition.create(conversionVector, converterFunction);
    converterDefinitions.add(converterDefinition);
    return this;
  }

  @Override
  public Bean2bean createBean2bean() {
    Bean2beanConfiguration config = createConfiguration();
    return Bean2BeanImpl.create(config);
  }

  private Bean2beanConfiguration createConfiguration() {
    DefaultBean2BeanConfiguration configuration = DefaultBean2BeanConfiguration.create();
    converterDefinitions.forEach(configuration.getRegistry()::store);
    return configuration;
  }
}
