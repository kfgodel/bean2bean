package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.impl.registry.VectorExtractor;
import info.kfgodel.bean2bean.core.impl.registry.definitions.BiFunctionAsConverterDefinition;
import info.kfgodel.bean2bean.core.impl.registry.definitions.ConverterDefinition;
import info.kfgodel.bean2bean.core.impl.registry.definitions.FunctionAsConverterDefinition;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.ConfigureDsl;
import info.kfgodel.bean2bean.other.FunctionRef;
import info.kfgodel.bean2bean.other.TypeVector;

import java.util.function.BiFunction;
import java.util.function.Function;

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
    TypeVector implicitVector = VectorExtractor.create().extractFrom(converterFunction);
    return usingConverter(implicitVector, converterFunction);
  }

  @Override
  public ConfigureDsl usingConverter(BiFunction<?, B2bDsl, ?> converterFunction) {
    TypeVector implicitVector = VectorExtractor.create().extractFrom(converterFunction);
    return usingConverter(implicitVector, converterFunction);
  }

  @Override
  public ConfigureDsl usingConverter(FunctionRef<?, ?> converterFunctionRef) {
    TypeVector implicitVector = converterFunctionRef.getInputOutputVector();
    Function<?, ?> function = converterFunctionRef.getFunction();
    return usingConverter(implicitVector, function);
  }

  private ConfigureDsl usingConverter(TypeVector conversionVector, Function converterFunction) {
    ConverterDefinition converterDefinition = FunctionAsConverterDefinition.create(conversionVector, converterFunction);
    usingConverter(converterDefinition);
    return this;
  }

  private ConfigureDsl usingConverter(TypeVector conversionVector, BiFunction converterFunction) {
    ConverterDefinition converterDefinition = BiFunctionAsConverterDefinition.create(conversionVector, converterFunction, b2bDsl);
    return usingConverter(converterDefinition);
  }

  private ConfigureDsl usingConverter(ConverterDefinition converterDefinition) {
    b2bDsl.getCore().getRegistry().store(converterDefinition);
    return this;
  }

}
