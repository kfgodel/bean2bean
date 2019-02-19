package info.kfgodel.bean2bean.core.impl.registry.definitions;

import info.kfgodel.bean2bean.core.api.registry.ConverterDefinition;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;

import java.util.function.Function;

/**
 * This class is the default implementation for a conversion definition
 * Date: 16/02/19 - 18:21
 */
public class DefaultDefinition implements ConverterDefinition {

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

  public static DefaultDefinition create(Function<ObjectConversion, Object> converter, DomainVector conversionVector) {
    DefaultDefinition definition = new DefaultDefinition();
    definition.converter = converter;
    definition.conversionVector = conversionVector;
    return definition;
  }

}
