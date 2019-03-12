package info.kfgodel.bean2bean.dsl.impl.scopes;

import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.api.scopes.ExplicitScopeWithDefinedSource;
import info.kfgodel.bean2bean.dsl.api.scopes.ScopedRegistrationDsl;

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
    Domain targetDomain = parentDsl.calculateDomainFor(targetType);
    DomainVector conversionVector = DomainVector.create(inputDomain, targetDomain);
    return ScopedRegistrationDslImpl.<I,O>create(conversionVector, this);
  }
}
