package info.kfgodel.bean2bean.v3.core.impl.registry.definitions;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.v3.core.api.registry.definitions.VectorScopedDefinition;

import java.util.function.Function;

/**
 * This class is the default implementation for a conversion definition
 * Date: 16/02/19 - 18:21
 */
public class VectorDefinition implements VectorScopedDefinition {

  private DomainVector conversionVector;
  private Function<Bean2beanTask, Object> converter;

  @Override
  public DomainVector getConversionVector() {
    return conversionVector;
  }

  @Override
  public Function<Bean2beanTask, Object> getConverter() {
    return converter;
  }

  @Override
  public String toString() {
    return "VectorDefinition{" +
      "conversionVector=" + conversionVector +
      ", converter=" + converter +
      '}';
  }

  public static VectorDefinition create(Function<Bean2beanTask, Object> converter, DomainVector conversionVector) {
    VectorDefinition definition = new VectorDefinition();
    definition.converter = converter;
    definition.conversionVector = conversionVector;
    return definition;
  }

}
