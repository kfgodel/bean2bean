package info.kfgodel.bean2bean.core.impl.registry.definitions;

import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.api.registry.definitions.VectorBasedDefinition;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;

import java.util.function.Function;

/**
 * This class is the default implementation for a conversion definition
 * Date: 16/02/19 - 18:21
 */
public class VectorDefinition implements VectorBasedDefinition {

  private DomainVector conversionVector;
  private Function<ObjectConversion, Object> converter;

  @Override
  public DomainVector getConversionVector() {
    return conversionVector;
  }

  @Override
  public Function<ObjectConversion, Object> getConverter() {
    return converter;
  }

  public static VectorDefinition create(Function<ObjectConversion, Object> converter, DomainVector conversionVector) {
    VectorDefinition definition = new VectorDefinition();
    definition.converter = converter;
    definition.conversionVector = conversionVector;
    return definition;
  }

}
