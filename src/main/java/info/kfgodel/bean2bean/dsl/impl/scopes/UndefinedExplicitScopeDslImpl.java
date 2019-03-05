package info.kfgodel.bean2bean.dsl.impl.scopes;

import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.api.scopes.ScopedConfigureDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.SourceDefinedExplicitScopeDsl;
import info.kfgodel.bean2bean.dsl.api.scopes.UndefinedExplicitScopeDsl;
import info.kfgodel.bean2bean.dsl.impl.ConfigureDslImpl;

import java.lang.reflect.Type;

/**
 * This class implements the initial explicit dsl definition
 * Date: 05/03/19 - 19:46
 */
public class UndefinedExplicitScopeDslImpl implements UndefinedExplicitScopeDsl {

  private ConfigureDslImpl parentDsl;

  public static UndefinedExplicitScopeDslImpl create(ConfigureDslImpl configureDsl) {
    UndefinedExplicitScopeDslImpl dsl = new UndefinedExplicitScopeDslImpl();
    dsl.parentDsl = configureDsl;
    return dsl;
  }

  @Override
  public SourceDefinedExplicitScopeDsl accept(Type inputType) {
    Domain inputDomain = calculateDomainFor(inputType);
    return SourceDefinedExplicitScopeDslImpl.create(inputDomain, this);
  }

  public Domain calculateDomainFor(Type inputType) {
    return this.parentDsl.getDomainCalculator().forType(inputType);
  }

  @Override
  public ScopedConfigureDsl vector(DomainVector conversionVector) {
    return DomainVectorScopedConfigureDsl.create(conversionVector, this.parentDsl);
  }
}