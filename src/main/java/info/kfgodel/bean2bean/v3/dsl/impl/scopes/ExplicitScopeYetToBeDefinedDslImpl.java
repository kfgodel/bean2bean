package info.kfgodel.bean2bean.v3.dsl.impl.scopes;

import info.kfgodel.bean2bean.v3.core.api.registry.Domain;
import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.v3.dsl.api.scopes.ExplicitScopeWithParameterizedSourceDsl;
import info.kfgodel.bean2bean.v3.dsl.api.scopes.ExplicitScopeWithTypeSourceDsl;
import info.kfgodel.bean2bean.v3.dsl.api.scopes.ExplicitScopeYetToBeDefinedDsl;
import info.kfgodel.bean2bean.v3.dsl.api.scopes.ImplicitScopeRegistrationDsl;
import info.kfgodel.bean2bean.v3.dsl.api.scopes.ScopedRegistrationDsl;
import info.kfgodel.bean2bean.v3.dsl.impl.ConfigureDslImpl;
import info.kfgodel.reflect.references.TypeRef;

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
  public ScopedRegistrationDsl vector(DomainVector conversionVector) {
    return DomainVectorScopeRegistrationDsl.create(conversionVector, this.parentDsl);
  }

  @Override
  public ImplicitScopeRegistrationDsl implicitTypes() {
    return ImplicitScopeRegistrationDslImpl.create(this.parentDsl);
  }

  public ConfigureDslImpl getParentDsl() {
    return parentDsl;
  }
}
