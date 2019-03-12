package info.kfgodel.bean2bean.dsl.impl.scopes;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.impl.conversion.BiFunctionAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.ConsumerAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.FunctionAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.SupplierAdapterConverter;
import info.kfgodel.bean2bean.core.impl.registry.definitions.VectorDefinition;
import info.kfgodel.bean2bean.dsl.api.ConfigureDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.ScopedConfigureDsl;
import info.kfgodel.bean2bean.dsl.impl.ConfigureDslImpl;
import info.kfgodel.bean2bean.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.other.references.ConsumerRef;
import info.kfgodel.bean2bean.other.references.FunctionRef;
import info.kfgodel.bean2bean.other.references.SupplierRef;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class implements the explicit domain vector scoped configuration
 * Date: 05/03/19 - 18:23
 */
public class DomainVectorScopedConfigureDsl implements ScopedConfigureDsl {

  private DomainVector domainVector;
  private ConfigureDslImpl parentDsl;

  public static DomainVectorScopedConfigureDsl create(DomainVector domainVector, ConfigureDslImpl configureDsl) {
    DomainVectorScopedConfigureDsl dsl = new DomainVectorScopedConfigureDsl();
    dsl.domainVector = domainVector;
    dsl.parentDsl = configureDsl;
    return dsl;
  }

  @Override
  public <I, O> ScopedConfigureDsl useConverter(Function<I, O> converterFunction) {
    FunctionAdapterConverter converter = FunctionAdapterConverter.create(converterFunction);
    return usingConverterFor(converter);
  }

  @Override
  public <I, O> ScopedConfigureDsl useConverter(BiFunction<I, Bean2beanTask, O> converterFunction) {
    BiFunctionAdapterConverter converter = BiFunctionAdapterConverter.create(converterFunction);
    return usingConverterFor(converter);
  }

  @Override
  public <O> ScopedConfigureDsl useConverter(Supplier<O> converterFunction) {
    SupplierAdapterConverter converter = SupplierAdapterConverter.create(converterFunction);
    return usingConverterFor(converter);
  }

  @Override
  public <I> ScopedConfigureDsl useConverter(Consumer<I> converterFunction) {
    ConsumerAdapterConverter converter = ConsumerAdapterConverter.create(converterFunction);
    return usingConverterFor(converter);
  }

  @Override
  public <I, O> ScopedConfigureDsl useConverter(FunctionRef<I, O> converterFunctionRef) {
    return useConverter(converterFunctionRef.getFunction());
  }

  @Override
  public <I, O> ScopedConfigureDsl useConverter(BiFunctionRef<I, Bean2beanTask, O> converterFunction) {
    return useConverter(converterFunction.getBiFunction());
  }

  @Override
  public <O> ScopedConfigureDsl useConverter(SupplierRef<O> converterFunction) {
    return useConverter(converterFunction.getSupplier());
  }

  @Override
  public <I> ScopedConfigureDsl useConverter(ConsumerRef<I> converterFunction) {
    return useConverter(converterFunction.getConsumer());
  }

  private ConfigureDsl usingConverterFor(Function<Bean2beanTask, Object> converter) {
    VectorDefinition definition = VectorDefinition.create(converter, domainVector);
    return parentDsl.useDefinition(definition);
  }
}
