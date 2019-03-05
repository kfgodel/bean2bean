package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.api.registry.definitions.ConverterDefinition;
import info.kfgodel.bean2bean.core.impl.registry.domains.DomainCalculator;
import info.kfgodel.bean2bean.core.impl.registry.domains.DomainVectorExtractor;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.ConfigureDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.ScopedConfigureDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.UndefinedExplicitScopeDsl;
import info.kfgodel.bean2bean.dsl.impl.scopes.PredicateScopedConfigureDsl;
import info.kfgodel.bean2bean.dsl.impl.scopes.UndefinedExplicitScopeDslImpl;
import info.kfgodel.bean2bean.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.other.references.ConsumerRef;
import info.kfgodel.bean2bean.other.references.FunctionRef;
import info.kfgodel.bean2bean.other.references.SupplierRef;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
  public ScopedConfigureDsl scopingWith(Predicate<DomainVector> scopePredicate) {
    return PredicateScopedConfigureDsl.create(scopePredicate, this);
  }

  @Override
  public UndefinedExplicitScopeDsl scopingTo() {
    return UndefinedExplicitScopeDslImpl.create(this);
  }

  @Override
  public <I,O> ConfigureDsl useConverter(Function<I, O> converterFunction) {
    scopingTo().implicitTypes().useConverter(converterFunction);
    return this;
  }

  @Override
  public <I,O> ConfigureDsl useConverter(FunctionRef<I, O> converterFunctionRef) {
    scopingTo().implicitTypes().useConverter(converterFunctionRef);
    return this;
  }

  @Override
  public <I,O> ConfigureDsl useConverter(BiFunction<I, Bean2beanTask, O> converterFunction) {
    scopingTo().implicitTypes().useConverter(converterFunction);
    return this;
  }

  @Override
  public <I,O> ConfigureDsl useConverter(BiFunctionRef<I, Bean2beanTask, O> converterFunctionRef) {
    scopingTo().implicitTypes().useConverter(converterFunctionRef);
    return this;
  }

  @Override
  public <O> ConfigureDsl useConverter(Supplier<O> converterFunction) {
    scopingTo().implicitTypes().useConverter(converterFunction);
    return this;
  }

  @Override
  public <O> ConfigureDsl useConverter(SupplierRef<O> converterFunctionRef) {
    scopingTo().implicitTypes().useConverter(converterFunctionRef);
    return this;
  }

  @Override
  public <I> ConfigureDsl useConverter(Consumer<I> converterFunction) {
    scopingTo().implicitTypes().useConverter(converterFunction);
    return this;
  }

  @Override
  public <I> ConfigureDsl useConverter(ConsumerRef<I> converterFunctionRef) {
    scopingTo().implicitTypes().useConverter(converterFunctionRef);
    return this;
  }

  @Override
  public ConfigureDsl useDefinition(ConverterDefinition converterDefinition) {
    getB2bDsl().getCore().getRegistry().store(converterDefinition);
    return this;
  }

  public B2bDsl getB2bDsl() {
    return b2bDsl;
  }

  public DomainVectorExtractor getVectorExtractor() {
    return vectorExtractor;
  }

  public DomainCalculator getDomainCalculator(){
    return this.b2bDsl.getCalculator();
  }
}
