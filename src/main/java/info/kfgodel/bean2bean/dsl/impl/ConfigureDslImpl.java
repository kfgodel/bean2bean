package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.api.registry.definitions.ConverterDefinition;
import info.kfgodel.bean2bean.core.impl.registry.domains.DomainCalculator;
import info.kfgodel.bean2bean.core.impl.registry.domains.DomainVectorExtractor;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.ConfigureDsl;
import info.kfgodel.bean2bean.dsl.api.ImplicitlyScopedConfigureDsl;
import info.kfgodel.bean2bean.dsl.api.ScopedConfigureDsl;
import info.kfgodel.bean2bean.dsl.impl.scopes.DomainVectorScopedConfigureDsl;
import info.kfgodel.bean2bean.dsl.impl.scopes.ImplicitScopeConfigureDslImpl;
import info.kfgodel.bean2bean.dsl.impl.scopes.PredicateScopedConfigureDsl;
import info.kfgodel.bean2bean.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.other.references.ConsumerRef;
import info.kfgodel.bean2bean.other.references.FunctionRef;
import info.kfgodel.bean2bean.other.references.SupplierRef;
import info.kfgodel.bean2bean.other.references.TypeRef;

import java.lang.reflect.Type;
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
  public ScopedConfigureDsl scopingTo(Type sourceType, Type targetType) {
    DomainVector domainVector = createVectorFor(sourceType, targetType);
    return DomainVectorScopedConfigureDsl.create(domainVector, this);
  }

  @Override
  public ScopedConfigureDsl scopingTo(TypeRef<?> sourceType, TypeRef<?> targetType) {
    return scopingTo(sourceType.getReference(), targetType.getReference());
  }

  private DomainVector createVectorFor(Type inputType, Type outputType) {
    return DomainVector.create(domainFor(inputType), domainFor(outputType));
  }

  private Domain domainFor(Type javaType) {
    return getDomainCalculator().forType(javaType);
  }


  @Override
  public ImplicitlyScopedConfigureDsl scopingByTypeArguments() {
    return ImplicitScopeConfigureDslImpl.create(this);
  }

  @Override
  public <I,O> ConfigureDsl useConverter(Function<I, O> converterFunction) {
    scopingByTypeArguments().useConverter(converterFunction);
    return this;
  }

  @Override
  public <I,O> ConfigureDsl useConverter(FunctionRef<I, O> converterFunctionRef) {
    scopingByTypeArguments().useConverter(converterFunctionRef);
    return this;
  }

  @Override
  public <I,O> ConfigureDsl useConverter(BiFunction<I, Bean2beanTask, O> converterFunction) {
    scopingByTypeArguments().useConverter(converterFunction);
    return this;
  }

  @Override
  public <I,O> ConfigureDsl useConverter(BiFunctionRef<I, Bean2beanTask, O> converterFunctionRef) {
    scopingByTypeArguments().useConverter(converterFunctionRef);
    return this;
  }

  @Override
  public <O> ConfigureDsl useConverter(Supplier<O> converterFunction) {
    scopingByTypeArguments().useConverter(converterFunction);
    return this;
  }

  @Override
  public <O> ConfigureDsl useConverter(SupplierRef<O> converterFunctionRef) {
    scopingByTypeArguments().useConverter(converterFunctionRef);
    return this;
  }

  @Override
  public <I> ConfigureDsl useConverter(Consumer<I> converterFunction) {
    scopingByTypeArguments().useConverter(converterFunction);
    return this;
  }

  @Override
  public <I> ConfigureDsl useConverter(ConsumerRef<I> converterFunctionRef) {
    scopingByTypeArguments().useConverter(converterFunctionRef);
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
