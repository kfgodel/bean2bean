package info.kfgodel.bean2bean.dsl.impl.scopes;

import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.api.scopes.ExplicitScopeWithDefinedSource;
import info.kfgodel.bean2bean.dsl.api.scopes.ExplicitScopeYetToBeDefinedDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.ImplicitlyScopedConfigureDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.ScopedConfigureDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.SourceDefinedExplicitScopeDsl;
import info.kfgodel.bean2bean.dsl.impl.ConfigureDslImpl;

import java.lang.reflect.Type;

/**
 * This class implements the initial explicit dsl definition
 * Date: 05/03/19 - 19:46
 */
public class ExplicitScopeYetToBeDefinedDslImpl implements ExplicitScopeYetToBeDefinedDsl {

  private ConfigureDslImpl parentDsl;

  public static ExplicitScopeYetToBeDefinedDslImpl create(ConfigureDslImpl configureDsl) {
    ExplicitScopeYetToBeDefinedDslImpl dsl = new ExplicitScopeYetToBeDefinedDslImpl();
    dsl.parentDsl = configureDsl;
    return dsl;
  }

  @Override
  public SourceDefinedExplicitScopeDsl accept(Type inputType) {
    Domain inputDomain = calculateDomainFor(inputType);
    return SourceDefinedExplicitScopeDslImpl.create(inputDomain, this);
  }

  @Override
  public <I> ExplicitScopeWithDefinedSource<I> accept(Class<I> inputType) {
    Domain inputDomain = calculateDomainFor(inputType);
    return ExplicitScopeWithDefinedSourceImpl.create(inputDomain, this);
  }


  public Domain calculateDomainFor(Type inputType) {
    return this.parentDsl.getDomainCalculator().forType(inputType);
  }

  @Override
  public ScopedConfigureDsl vector(DomainVector conversionVector) {
    return DomainVectorScopedConfigureDsl.create(conversionVector, this.parentDsl);
  }

  @Override
  public ImplicitlyScopedConfigureDsl implicitTypes() {
    return ImplicitScopeConfigureDslImpl.create(this.parentDsl);
  }

  public ConfigureDslImpl getParentDsl() {
    return parentDsl;
  }
}
