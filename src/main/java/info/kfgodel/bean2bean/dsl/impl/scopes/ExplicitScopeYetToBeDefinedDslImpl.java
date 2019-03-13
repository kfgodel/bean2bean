package info.kfgodel.bean2bean.dsl.impl.scopes;

import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.api.scopes.ExplicitScopeWithParameterizedSourceDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.ExplicitScopeWithTypeSourceDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.ExplicitScopeYetToBeDefinedDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.ImplicitScopeDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.ScopeDsl;
import info.kfgodel.bean2bean.dsl.impl.ConfigureDslImpl;
import info.kfgodel.bean2bean.other.references.TypeRef;

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
  public ExplicitScopeWithTypeSourceDsl accept(Type inputType) {
    Domain inputDomain = calculateDomainFor(inputType);
    return ExplicitScopeWithTypeSourceDslImpl.create(inputDomain, this);
  }

  @Override
  public <I> ExplicitScopeWithParameterizedSourceDsl<I> accept(Class<I> inputType) {
    Domain inputDomain = calculateDomainFor(inputType);
    return ExplicitScopeWithParameterizedSourceDslImpl.create(inputDomain, this);
  }

  @Override
  public <I> ExplicitScopeWithParameterizedSourceDsl<I> accept(TypeRef<I> inputTypeRef) {
    Domain inputDomain = calculateDomainFor(inputTypeRef.getReference());
    return ExplicitScopeWithParameterizedSourceDslImpl.create(inputDomain, this);
  }


  public Domain calculateDomainFor(Type inputType) {
    return this.parentDsl.getDomainCalculator().forType(inputType);
  }

  @Override
  public ScopeDsl vector(DomainVector conversionVector) {
    return DomainVectorScopeDsl.create(conversionVector, this.parentDsl);
  }

  @Override
  public ImplicitScopeDsl implicitTypes() {
    return ImplicitScopeDslImpl.create(this.parentDsl);
  }

  public ConfigureDslImpl getParentDsl() {
    return parentDsl;
  }
}