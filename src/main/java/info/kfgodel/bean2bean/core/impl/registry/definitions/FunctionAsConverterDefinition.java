package info.kfgodel.bean2bean.core.impl.registry.definitions;

import info.kfgodel.bean2bean.core.impl.conversion.FunctionConverter;
import info.kfgodel.bean2bean.core.impl.registry.TypeVector;

import java.util.function.Function;

/**
 * This type represents the definition for a simple function used as converter
 * Date: 13/02/19 - 19:30
 */
public class FunctionAsConverterDefinition implements ConverterDefinition {

  private Function function;
  private TypeVector typeVector;

  @Override
  public TypeVector getConversionVector() {
    return typeVector;
  }

  @Override
  public Function getConverter() {
    return FunctionConverter.create(function);
  }

  public static FunctionAsConverterDefinition create(TypeVector conversionVector, Function converterFunction) {
    FunctionAsConverterDefinition simpleDefinition = new FunctionAsConverterDefinition();
    simpleDefinition.function = converterFunction;
    simpleDefinition.typeVector = conversionVector;
    return simpleDefinition;
  }

}
