package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.impl.conversion.BiFunctionAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.ConsumerAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.FunctionAdapterConverter;
import info.kfgodel.bean2bean.core.impl.conversion.SupplierAdapterConverter;
import info.kfgodel.bean2bean.core.impl.registry.definitions.PredicateDefinition;
import info.kfgodel.bean2bean.dsl.api.ConfigureDsl;
import info.kfgodel.bean2bean.dsl.api.ScopedConfigureDsl;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class implements the scoped configuration using a predicate
 * Date: 05/03/19 - 16:43
 */
public class PredicateScopedConfigureDsl implements ScopedConfigureDsl {

  private Predicate<DomainVector> scopePredicate;
  private ConfigureDslImpl parentDsl;

  public static PredicateScopedConfigureDsl create(Predicate<DomainVector> scopePredicate, ConfigureDslImpl configureDsl) {
    PredicateScopedConfigureDsl dsl = new PredicateScopedConfigureDsl();
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

  private ConfigureDsl usingConverterFor(Function<Bean2beanTask, Object> converter) {
    PredicateDefinition definition = PredicateDefinition.create(converter, scopePredicate);
    return parentDsl.useDefinition(definition);
  }

}
