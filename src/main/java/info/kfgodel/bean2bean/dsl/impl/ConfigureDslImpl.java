package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.api.registry.definitions.VectorBasedDefinition;
import info.kfgodel.bean2bean.core.impl.conversion.BiFunctionAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.ConsumerAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.FunctionAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;
import info.kfgodel.bean2bean.core.impl.conversion.SupplierAdapterConverter;
import info.kfgodel.bean2bean.core.impl.registry.definitions.DefaultDefinition;
import info.kfgodel.bean2bean.core.impl.registry.domains.DomainVectorExtractor;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.ConfigureDsl;
import info.kfgodel.bean2bean.other.BiFunctionRef;
import info.kfgodel.bean2bean.other.ConsumerRef;
import info.kfgodel.bean2bean.other.FunctionRef;
import info.kfgodel.bean2bean.other.SupplierRef;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This type defines the defaults for a b2b configuration
 * Date: 10/02/19 - 23:06
 */
public class ConfigureDslImpl implements ConfigureDsl {

  private B2bDsl b2bDsl;
  private DomainVectorExtractor vectorExtractor;

  public static ConfigureDslImpl create(B2bDsl b2bDsl) {
    ConfigureDslImpl config = new ConfigureDslImpl();
    config.b2bDsl = b2bDsl;
    config.vectorExtractor = DomainVectorExtractor.create(b2bDsl.getCalculator());
    return config;
  }

  @Override
  public <I,O> ConfigureDsl usingConverter(Function<I, O> converterFunction) {
    DomainVector implicitVector = vectorExtractor.extractFrom(converterFunction);
    return usingConverter(implicitVector, converterFunction);
  }

  @Override
  public <I,O> ConfigureDsl usingConverter(FunctionRef<I, O> converterFunctionRef) {
    DomainVector implicitVector = vectorExtractor.extractFrom(converterFunctionRef);
    Function<?, ?> function = converterFunctionRef.getFunction();
    return usingConverter(implicitVector, function);
  }

  @Override
  public <I,O> ConfigureDsl usingConverter(BiFunction<I, B2bDsl, O> converterFunction) {
    DomainVector implicitVector = vectorExtractor.extractFrom(converterFunction);
    return usingConverter(implicitVector, converterFunction);
  }

  @Override
  public <I,O> ConfigureDsl usingConverter(BiFunctionRef<I, B2bDsl, O> converterFunction) {
    DomainVector implicitVector = vectorExtractor.extractFrom(converterFunction);
    return usingConverter(implicitVector, converterFunction.getBiFunction());
  }

  @Override
  public <O> ConfigureDsl usingConverter(Supplier<O> converterFunction) {
    DomainVector implicitVector = vectorExtractor.extractFrom(converterFunction);
    return usingConverter(implicitVector, converterFunction);
  }


  @Override
  public <O> ConfigureDsl usingConverter(SupplierRef<O> converterFunction) {
    DomainVector implicitVector = vectorExtractor.extractFrom(converterFunction);
    return usingConverter(implicitVector, converterFunction.getSupplier());
  }

  @Override
  public <I> ConfigureDsl usingConverter(Consumer<I> converterFunction) {
    DomainVector implicitVector = vectorExtractor.extractFrom(converterFunction);
    return usingConverter(implicitVector, converterFunction);
  }

  @Override
  public <I> ConfigureDsl usingConverter(ConsumerRef<I> converterFunction) {
    DomainVector implicitVector = vectorExtractor.extractFrom(converterFunction);
    return usingConverter(implicitVector, converterFunction.getConsumer());
  }

  private ConfigureDsl usingConverter(DomainVector conversionVector, Function converterFunction) {
    FunctionAdapterConverter converter = FunctionAdapterConverter.create(converterFunction);
    return usingConverterFor(conversionVector, converter);
  }

  private ConfigureDsl usingConverter(DomainVector conversionVector, Supplier<?> converterFunction) {
    SupplierAdapterConverter converter = SupplierAdapterConverter.create(converterFunction);
    return usingConverterFor(conversionVector, converter);
  }

  private ConfigureDsl usingConverter(DomainVector conversionVector, Consumer<?> converterFunction) {
    ConsumerAdapterConverter converter = ConsumerAdapterConverter.create(converterFunction);
    return usingConverterFor(conversionVector, converter);
  }
  private ConfigureDsl usingConverter(DomainVector conversionVector, BiFunction converterFunction) {
    BiFunctionAdapterConverter converter = BiFunctionAdapterConverter.create(converterFunction, b2bDsl);
    return usingConverterFor(conversionVector, converter);
  }

  private ConfigureDsl usingConverterFor(DomainVector conversionVector, Function<ObjectConversion, Object> converter) {
    DefaultDefinition definition = DefaultDefinition.create(converter, conversionVector);
    return usingConverter(definition);
  }

  private ConfigureDsl usingConverter(VectorBasedDefinition converterDefinition) {
    b2bDsl.getCore().getRegistry().store(converterDefinition);
    return this;
  }

}
