package info.kfgodel.bean2bean.dsl.impl.scopes;

import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.api.scopes.ExplicitScopeWithTypeSourceDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.ScopedRegistrationDsl;

import java.lang.reflect.Type;

/**
 * Date: 05/03/19 - 19:46
 */
public class ExplicitScopeWithTypeSourceDslImpl implements ExplicitScopeWithTypeSourceDsl {

  private ExplicitScopeYetToBeDefinedDslImpl parentDsl;

  private Domain inputDomain;

  @Override
  public ScopedRegistrationDsl andProduce(Type targetType) {
    Domain targetDomain = parentDsl.calculateDomainFor(targetType);
    DomainVector conversionVector = DomainVector.create(inputDomain, targetDomain);
    return parentDsl.vector(conversionVector);
  }

  public static ExplicitScopeWithTypeSourceDslImpl create(Domain inputDomain, ExplicitScopeYetToBeDefinedDslImpl parentDsl) {
    ExplicitScopeWithTypeSourceDslImpl dsl = new ExplicitScopeWithTypeSourceDslImpl();
    dsl.parentDsl = parentDsl;
    dsl.inputDomain = inputDomain;
    return dsl;
  }

}
