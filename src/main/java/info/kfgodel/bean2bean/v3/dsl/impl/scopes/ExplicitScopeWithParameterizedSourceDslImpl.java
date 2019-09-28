package info.kfgodel.bean2bean.v3.dsl.impl.scopes;

import info.kfgodel.bean2bean.v3.core.api.registry.Domain;
import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.v3.dsl.api.scopes.ExplicitScopeWithParameterizedSourceDsl;
import info.kfgodel.bean2bean.v3.dsl.api.scopes.ParameterizedScopeDsl;
import info.kfgodel.bean2bean.v3.dsl.impl.ConfigureDslImpl;
import info.kfgodel.reflect.references.TypeRef;

import java.lang.reflect.Type;

/**
 * Default implementation
 * Date: 11/03/19 - 21:43
 */
public class ExplicitScopeWithParameterizedSourceDslImpl<I> implements ExplicitScopeWithParameterizedSourceDsl<I> {

  private Domain inputDomain;
  private ExplicitScopeYetToBeDefinedDslImpl parentDsl;

  public static<I> ExplicitScopeWithParameterizedSourceDslImpl<I> create(Domain inputDomain, ExplicitScopeYetToBeDefinedDslImpl parentDsl) {
    ExplicitScopeWithParameterizedSourceDslImpl dsl = new ExplicitScopeWithParameterizedSourceDslImpl();
    dsl.inputDomain = inputDomain;
    dsl.parentDsl = parentDsl;
    return dsl;
  }

  @Override
  public <O> ParameterizedScopeDsl<I, O> andProduce(Class<O> targetType) {
    return andProduce((Type)targetType);
  }

  @Override
  public <O> ParameterizedScopeDsl<I, O> andProduce(Type targetType) {
    Domain targetDomain = parentDsl.calculateDomainFor(targetType);
    DomainVector conversionVector = DomainVector.create(inputDomain, targetDomain);
    return ParameterizedScopeDslImpl.<I,O>create(conversionVector, getConfigureDsl());
  }

  @Override
  public <O> ParameterizedScopeDsl<I, O> andProduce(TypeRef<O> targetTypeRef) {
    return andProduce(targetTypeRef.getReference());
  }

  private ConfigureDslImpl getConfigureDsl() {
    return this.parentDsl.getParentDsl();
  }
}
