package info.kfgodel.bean2bean.dsl.impl.scopes;

import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.api.scopes.ScopedConfigureDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.SourceDefinedExplicitScopeDsl;

import java.lang.reflect.Type;

/**
 * Date: 05/03/19 - 19:46
 */
public class SourceDefinedExplicitScopeDslImpl implements SourceDefinedExplicitScopeDsl {

  private UndefinedExplicitScopeDslImpl parentDsl;

  private Domain inputDomain;

  @Override
  public ScopedConfigureDsl andProduce(Type targetType) {
    Domain targetDomain = parentDsl.calculateDomainFor(targetType);
    DomainVector conversionVector = DomainVector.create(inputDomain, targetDomain);
    return parentDsl.vector(conversionVector);
  }

  public static SourceDefinedExplicitScopeDslImpl create(Domain inputDomain, UndefinedExplicitScopeDslImpl explicitScopeDefinitionDsl) {
    SourceDefinedExplicitScopeDslImpl dsl = new SourceDefinedExplicitScopeDslImpl();
    dsl.parentDsl = explicitScopeDefinitionDsl;
    dsl.inputDomain = inputDomain;
    return dsl;
  }

}
