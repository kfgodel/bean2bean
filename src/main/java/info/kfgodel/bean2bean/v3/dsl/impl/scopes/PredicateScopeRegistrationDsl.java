package info.kfgodel.bean2bean.v3.dsl.impl.scopes;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.v3.core.impl.conversion.BiFunctionAdapterConverter;
import info.kfgodel.bean2bean.v3.core.impl.conversion.ConsumerAdapterConverter;
import info.kfgodel.bean2bean.v3.core.impl.conversion.FunctionAdapterConverter;
import info.kfgodel.bean2bean.v3.core.impl.conversion.SupplierAdapterConverter;
import info.kfgodel.bean2bean.v3.core.impl.registry.definitions.PredicateDefinition;
import info.kfgodel.bean2bean.v3.dsl.api.ConfigureDsl;
import info.kfgodel.bean2bean.v3.dsl.api.scopes.ScopedRegistrationDsl;
import info.kfgodel.bean2bean.v3.dsl.impl.ConfigureDslImpl;
import info.kfgodel.bean2bean.v3.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.v3.other.references.ConsumerRef;
import info.kfgodel.bean2bean.v3.other.references.FunctionRef;
import info.kfgodel.bean2bean.v3.other.references.SupplierRef;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class implements the scoped configuration using a predicate
 * Date: 05/03/19 - 16:43
 */
public class PredicateScopeRegistrationDsl implements ScopedRegistrationDsl {

  private Predicate<DomainVector> scopePredicate;
  private ConfigureDslImpl parentDsl;

  public static PredicateScopeRegistrationDsl create(Predicate<DomainVector> scopePredicate, ConfigureDslImpl configureDsl) {
    PredicateScopeRegistrationDsl dsl = new PredicateScopeRegistrationDsl();
    dsl.scopePredicate = scopePredicate;
    dsl.parentDsl = configureDsl;
    return dsl;
  }

  @Override
  public <I, O> ConfigureDsl useConverter(Function<I, O> converterFunction) {
    FunctionAdapterConverter converter = FunctionAdapterConverter.create(converterFunction);
    return usingConverterFor(converter);
  }

    @Override
  public <I, O> ConfigureDsl useConverter(BiFunction<I, Bean2beanTask, O> converterFunction) {
    BiFunctionAdapterConverter converter = BiFunctionAdapterConverter.create(converterFunction);
    return usingConverterFor(converter);
  }

  @Override
  public <O> ConfigureDsl useConverter(Supplier<O> converterFunction) {
    SupplierAdapterConverter converter = SupplierAdapterConverter.create(converterFunction);
    return usingConverterFor(converter);
  }

  @Override
  public <I> ConfigureDsl useConverter(Consumer<I> converterFunction) {
    ConsumerAdapterConverter converter = ConsumerAdapterConverter.create(converterFunction);
    return usingConverterFor(converter);
  }

  @Override
  public <I, O> ScopedRegistrationDsl useConverter(FunctionRef<I, O> converterFunctionRef) {
    return useConverter(converterFunctionRef.getFunction());
  }

  @Override
  public <I, O> ScopedRegistrationDsl useConverter(BiFunctionRef<I, Bean2beanTask, O> converterFunction) {
    return useConverter(converterFunction.getBiFunction());
  }

  @Override
  public <O> ScopedRegistrationDsl useConverter(SupplierRef<O> converterFunction) {
    return useConverter(converterFunction.getSupplier());
  }

  @Override
  public <I> ScopedRegistrationDsl useConverter(ConsumerRef<I> converterFunction) {
    return useConverter(converterFunction.getConsumer());
  }

  private ConfigureDsl usingConverterFor(Function<Bean2beanTask, Object> converter) {
    PredicateDefinition definition = PredicateDefinition.create(converter, scopePredicate);
    return parentDsl.useDefinition(definition);
  }

}
