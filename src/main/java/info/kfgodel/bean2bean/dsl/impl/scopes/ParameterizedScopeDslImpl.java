package info.kfgodel.bean2bean.dsl.impl.scopes;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.impl.conversion.BiFunctionAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.ConsumerAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.FunctionAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.SupplierAdapterConverter;
import info.kfgodel.bean2bean.core.impl.registry.definitions.VectorDefinition;
import info.kfgodel.bean2bean.dsl.api.scopes.ParameterizedScopeDsl;
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
 * Date: 11/03/19 - 22:16
 */
public class ParameterizedScopeDslImpl<I,O> implements ParameterizedScopeDsl<I,O> {

  private DomainVector domainVector;
  private ConfigureDslImpl parentDsl;

  @Override
  public ParameterizedScopeDsl<I, O> useConverter(Function<? super I, ? extends O> converterFunction) {
    FunctionAdapterConverter converter = FunctionAdapterConverter.create(converterFunction);
    return usingConverterFor(converter);
  }

  @Override
  public ParameterizedScopeDsl<I, O> useConverter(BiFunction<? super I, Bean2beanTask, ? extends O> converterFunction) {
    BiFunctionAdapterConverter converter = BiFunctionAdapterConverter.create(converterFunction);
    return usingConverterFor(converter);
  }

  @Override
  public ParameterizedScopeDsl<I, O> useConverter(Supplier<? extends O> converterFunction) {
    SupplierAdapterConverter converter = SupplierAdapterConverter.create(converterFunction);
    return usingConverterFor(converter);
  }

  @Override
  public ParameterizedScopeDsl<I, O> useConverter(Consumer<? super I> converterFunction) {
    ConsumerAdapterConverter converter = ConsumerAdapterConverter.create(converterFunction);
    return usingConverterFor(converter);
  }

  @Override
  public ParameterizedScopeDsl<I, O> useConverter(FunctionRef<? super I, ? extends O> converterFunctionRef) {
    return useConverter(converterFunctionRef.getFunction());
  }

  @Override
  public ParameterizedScopeDsl<I, O> useConverter(BiFunctionRef<? super I, Bean2beanTask, ? extends O> converterFunction) {
    return useConverter(converterFunction.getBiFunction());
  }

  @Override
  public ParameterizedScopeDsl<I, O> useConverter(SupplierRef<? extends O> converterFunction) {
    return useConverter(converterFunction.getSupplier());
  }

  @Override
  public ParameterizedScopeDsl<I, O> useConverter(ConsumerRef<? super I> converterFunction) {
    return useConverter(converterFunction.getConsumer());
  }

  private ParameterizedScopeDslImpl<I, O> usingConverterFor(Function<Bean2beanTask, Object> converter) {
    VectorDefinition definition = VectorDefinition.create(converter, domainVector);
    parentDsl.useDefinition(definition);
    return this;
  }

  public static <I,O> ParameterizedScopeDslImpl<I,O> create(DomainVector domainVector, ConfigureDslImpl parentDsl) {
    ParameterizedScopeDslImpl<I,O> dsl = new ParameterizedScopeDslImpl<>();
    dsl.domainVector = domainVector;
    dsl.parentDsl = parentDsl;
    return dsl;
  }

}
