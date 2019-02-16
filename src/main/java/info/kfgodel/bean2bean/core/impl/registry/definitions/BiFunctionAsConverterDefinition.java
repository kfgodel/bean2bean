package info.kfgodel.bean2bean.core.impl.registry.definitions;

import info.kfgodel.bean2bean.core.impl.conversion.BiFunctionConverter;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.other.TypeVector;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This represents the
 * Date: 16/02/19 - 14:24
 */
public class BiFunctionAsConverterDefinition implements ConverterDefinition {

  private B2bDsl b2bDsl;
  private BiFunction function;
  private TypeVector typeVector;

  @Override
  public TypeVector getConversionVector() {
    return typeVector;
  }

  @Override
  public Function getConverter() {
    return BiFunctionConverter.create(function, b2bDsl);
  }

  public static BiFunctionAsConverterDefinition create(TypeVector conversionVector, BiFunction converterFunction, B2bDsl b2bDsl) {
    BiFunctionAsConverterDefinition definition = new BiFunctionAsConverterDefinition();
    definition.function = converterFunction;
    definition.typeVector = conversionVector;
    definition.b2bDsl = b2bDsl;
    return definition;
  }

}
