package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.impl.conversion.BiFunctionAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.FunctionAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.SupplierAdapterConverter;
import info.kfgodel.bean2bean.core.impl.registry.TypeVectorExtractor;
import info.kfgodel.bean2bean.core.impl.registry.definitions.ConverterDefinition;
import info.kfgodel.bean2bean.core.impl.registry.definitions.DefaultDefinition;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.ConfigureDsl;
import info.kfgodel.bean2bean.other.FunctionRef;
import info.kfgodel.bean2bean.other.TypeVector;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This type defines the defaults for a b2b configuration
 * Date: 10/02/19 - 23:06
 */
public class ConfigureDslImpl implements ConfigureDsl {

  private B2bDsl b2bDsl;

  public static ConfigureDslImpl create(B2bDsl b2bDsl) {
    ConfigureDslImpl config = new ConfigureDslImpl();
    config.b2bDsl = b2bDsl;
    return config;
  }

  @Override
  public ConfigureDsl usingConverter(Function<?, ?> converterFunction) {
    TypeVector implicitVector = TypeVectorExtractor.create().extractFrom(converterFunction);
    return usingConverter(implicitVector, converterFunction);
  }

  @Override
  public ConfigureDsl usingConverter(BiFunction<?, B2bDsl, ?> converterFunction) {
    TypeVector implicitVector = TypeVectorExtractor.create().extractFrom(converterFunction);
    return usingConverter(implicitVector, converterFunction);
  }

  @Override
  public ConfigureDsl usingConverter(FunctionRef<?, ?> converterFunctionRef) {
    TypeVector implicitVector = converterFunctionRef.getInputOutputVector();
    Function<?, ?> function = converterFunctionRef.getFunction();
    return usingConverter(implicitVector, function);
  }

  @Override
  public ConfigureDsl usingConverter(Supplier<?> converterFunction) {
    TypeVector implicitVector = TypeVectorExtractor.create().extractFrom(converterFunction);
    SupplierAdapterConverter converter = SupplierAdapterConverter.create(converterFunction);
    return usingConverter(implicitVector, converter);
  }

  private ConfigureDsl usingConverter(TypeVector conversionVector, Function converterFunction) {
    Function converter = FunctionAdapterConverter.create(converterFunction);
    return usingConverterFor(conversionVector, converter);
  }

  private ConfigureDsl usingConverter(TypeVector conversionVector, BiFunction converterFunction) {
    BiFunctionAdapterConverter converter = BiFunctionAdapterConverter.create(converterFunction, b2bDsl);
    return usingConverterFor(conversionVector, converter);
  }

  private ConfigureDsl usingConverterFor(TypeVector conversionVector, Function converter) {
    DefaultDefinition definition = DefaultDefinition.create(converter, conversionVector);
    return usingConverter(definition);
  }

  private ConfigureDsl usingConverter(ConverterDefinition converterDefinition) {
    b2bDsl.getCore().getRegistry().store(converterDefinition);
    return this;
  }

}
