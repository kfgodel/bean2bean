package info.kfgodel.bean2bean.v3.dsl.impl.scopes;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.v3.core.impl.conversion.BiFunctionAdapterConverter;
import info.kfgodel.bean2bean.v3.core.impl.conversion.ConsumerAdapterConverter;
import info.kfgodel.bean2bean.v3.core.impl.conversion.FunctionAdapterConverter;
import info.kfgodel.bean2bean.v3.core.impl.conversion.SupplierAdapterConverter;
import info.kfgodel.bean2bean.v3.core.impl.registry.definitions.VectorDefinition;
import info.kfgodel.bean2bean.v3.core.impl.registry.domains.DomainVectorExtractor;
import info.kfgodel.bean2bean.v3.dsl.api.ConfigureDsl;
import info.kfgodel.bean2bean.v3.dsl.api.scopes.ImplicitScopeRegistrationDsl;
import info.kfgodel.bean2bean.v3.dsl.impl.ConfigureDslImpl;
import info.kfgodel.bean2bean.v3.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.v3.other.references.ConsumerRef;
import info.kfgodel.bean2bean.v3.other.references.FunctionRef;
import info.kfgodel.bean2bean.v3.other.references.SupplierRef;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class implements the implciit scope applicability using reflection to deduce acceptable
 * conversion vectors for each converter
 * Date: 05/03/19 - 17:57
 */
public class ImplicitScopeRegistrationDslImpl implements ImplicitScopeRegistrationDsl {

  private ConfigureDslImpl parentDsl;

  public static ImplicitScopeRegistrationDslImpl create(ConfigureDslImpl configureDsl) {
    ImplicitScopeRegistrationDslImpl dsl = new ImplicitScopeRegistrationDslImpl();
    dsl.parentDsl = configureDsl;
    return dsl;
  }

  public DomainVectorExtractor getVectorExtractor() {
    return parentDsl.getVectorExtractor();
  }

  @Override
  public <I, O> ConfigureDsl useConverter(Function<I, O> converterFunction) {
    DomainVector implicitVector = getVectorExtractor().extractFrom(converterFunction);
    return useConverter(implicitVector, converterFunction);
  }

  @Override
  public <I, O> ConfigureDsl useConverter(BiFunction<I, Bean2beanTask, O> converterFunction) {
    DomainVector implicitVector = getVectorExtractor().extractFrom(converterFunction);
    return useConverter(implicitVector, converterFunction);
  }

  @Override
  public <O> ConfigureDsl useConverter(Supplier<O> converterFunction) {
    DomainVector implicitVector = getVectorExtractor().extractFrom(converterFunction);
    return useConverter(implicitVector, converterFunction);
  }

  @Override
  public <I> ConfigureDsl useConverter(Consumer<I> converterFunction) {
    DomainVector implicitVector = getVectorExtractor().extractFrom(converterFunction);
    return useConverter(implicitVector, converterFunction);
  }

  @Override
  public <I, O> ConfigureDsl useConverter(FunctionRef<I, O> converterFunctionRef) {
    DomainVector implicitVector = getVectorExtractor().extractFrom(converterFunctionRef);
    Function<?, ?> function = converterFunctionRef.getFunction();
    return useConverter(implicitVector, function);
  }

  @Override
  public <I, O> ConfigureDsl useConverter(BiFunctionRef<I, Bean2beanTask, O> converterFunction) {
    DomainVector implicitVector = getVectorExtractor().extractFrom(converterFunction);
    return useConverter(implicitVector, converterFunction.getBiFunction());
  }

  @Override
  public <O> ConfigureDsl useConverter(SupplierRef<O> converterFunction) {
    DomainVector implicitVector = getVectorExtractor().extractFrom(converterFunction);
    return useConverter(implicitVector, converterFunction.getSupplier());
  }

  @Override
  public <I> ConfigureDsl useConverter(ConsumerRef<I> converterFunction) {
    DomainVector implicitVector = getVectorExtractor().extractFrom(converterFunction);
    return useConverter(implicitVector, converterFunction.getConsumer());
  }

  private ConfigureDsl useConverter(DomainVector conversionVector, Function converterFunction) {
    FunctionAdapterConverter converter = FunctionAdapterConverter.create(converterFunction);
    return usingConverterFor(conversionVector, converter);
  }

  private ConfigureDsl useConverter(DomainVector conversionVector, Supplier<?> converterFunction) {
    SupplierAdapterConverter converter = SupplierAdapterConverter.create(converterFunction);
    return usingConverterFor(conversionVector, converter);
  }

  private ConfigureDsl useConverter(DomainVector conversionVector, Consumer<?> converterFunction) {
    ConsumerAdapterConverter converter = ConsumerAdapterConverter.create(converterFunction);
    return usingConverterFor(conversionVector, converter);
  }
  private ConfigureDsl useConverter(DomainVector conversionVector, BiFunction converterFunction) {
    BiFunctionAdapterConverter converter = BiFunctionAdapterConverter.create(converterFunction);
    return usingConverterFor(conversionVector, converter);
  }

  private ConfigureDsl usingConverterFor(DomainVector conversionVector, Function<Bean2beanTask, Object> converter) {
    VectorDefinition definition = VectorDefinition.create(converter, conversionVector);
    return parentDsl.useDefinition(definition);
  }

}
