package info.kfgodel.bean2bean.dsl.impl.scopes;

import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.api.scopes.ExplicitScopeWithDefinedSource;
import info.kfgodel.bean2bean.dsl.api.scopes.ScopedRegistrationDsl;
import info.kfgodel.bean2bean.dsl.impl.ConfigureDslImpl;
import info.kfgodel.bean2bean.other.references.TypeRef;

import java.lang.reflect.Type;

/**
 * Default implementation
 * Date: 11/03/19 - 21:43
 */
public class ExplicitScopeWithDefinedSourceImpl<I> implements ExplicitScopeWithDefinedSource<I> {

  private Domain inputDomain;
  private ExplicitScopeYetToBeDefinedDslImpl parentDsl;

  public static<I> ExplicitScopeWithDefinedSourceImpl<I> create(Domain inputDomain, ExplicitScopeYetToBeDefinedDslImpl parentDsl) {
    ExplicitScopeWithDefinedSourceImpl dsl = new ExplicitScopeWithDefinedSourceImpl();
    dsl.inputDomain = inputDomain;
    dsl.parentDsl = parentDsl;
    return dsl;
  }

  @Override
  public <O> ScopedRegistrationDsl<I, O> andProduce(Class<O> targetType) {
    return this.andProduce((Type)targetType);
  }

  @Override
  public <O> ScopedRegistrationDsl<I, O> andProduce(TypeRef<O> targetTypeRef) {
    return andProduce(targetTypeRef.getReference());
  }

  private <O> ScopedRegistrationDsl<I, O> andProduce(Type targetType) {
    Domain targetDomain = parentDsl.calculateDomainFor(targetType);
    DomainVector conversionVector = DomainVector.create(inputDomain, targetDomain);
    return ScopedRegistrationDslImpl.<I,O>create(conversionVector, getConfigureDsl());
  }


  private ConfigureDslImpl getConfigureDsl() {
    return this.parentDsl.getParentDsl();
  }
}
