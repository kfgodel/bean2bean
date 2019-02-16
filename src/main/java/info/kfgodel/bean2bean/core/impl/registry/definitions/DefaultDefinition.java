package info.kfgodel.bean2bean.core.impl.registry.definitions;

import info.kfgodel.bean2bean.other.TypeVector;

import java.util.function.Function;

/**
 * This class is the default implementation for a conversion definition
 * Date: 16/02/19 - 18:21
 */
public class DefaultDefinition implements ConverterDefinition {

  private TypeVector conversionVector;
  private Function converter;

  @Override
  public TypeVector getConversionVector() {
    return conversionVector;
  }

  @Override
  public Function getConverter() {
    return converter;
  }

  public static DefaultDefinition create(Function converter, TypeVector conversionVector) {
    DefaultDefinition definition = new DefaultDefinition();
    definition.converter = converter;
    definition.conversionVector = conversionVector;
    return definition;
  }

}
