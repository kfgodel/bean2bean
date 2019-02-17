package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.api.exceptions.Bean2BeanException;
import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;
import info.kfgodel.bean2bean.core.impl.registry.DomainCalculator;
import info.kfgodel.bean2bean.core.impl.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.api.SourceDefinedConversionDsl;
import info.kfgodel.bean2bean.other.TypeRef;

import java.lang.reflect.Type;

/**
 * Partial definition of a conversion with the source object defined
 * Date: 10/02/19 - 23:20
 */
public class SourceDefinedConversionDslImpl<I> implements SourceDefinedConversionDsl<I> {

  private ConvertDslImpl parentDsl;
  private I value;
  private Domain sourceDomain;

  public static<I> SourceDefinedConversionDslImpl<I> create(ConvertDslImpl convertDsl, I value, Domain sourceDomain) {
    SourceDefinedConversionDslImpl<I> sourceDefinedDsl = new SourceDefinedConversionDslImpl<>();
    sourceDefinedDsl.parentDsl = convertDsl;
    sourceDefinedDsl.value = value;
    sourceDefinedDsl.sourceDomain = sourceDomain;
    return sourceDefinedDsl;
  }

  @Override
  public <O> O to(Class<O> outputClass) throws Bean2BeanException {
    Domain targetDomain = domainFor(outputClass);
    ObjectConversion conversion = createConversionTo(targetDomain);
    return processConversion(conversion);
  }

  @Override
  public <O> O to(TypeRef<O> outputTypeRef) throws Bean2BeanException {
    Domain targetDomain = domainFor(outputTypeRef.getReference());
    ObjectConversion conversion = createConversionTo(targetDomain);
    return processConversion(conversion);
  }

  private Domain domainFor(Type aType) {
    return getCalculator().forType(aType);
  }

  private ObjectConversion createConversionTo(Domain targetDomain) {
    DomainVector conversionVector = DomainVector.create(sourceDomain, targetDomain);
    return ObjectConversion.create(this.value, conversionVector);
  }

  private <O> O processConversion(ObjectConversion conversion) {
    return getCore().process(conversion);
  }

  private DomainCalculator getCalculator() {
    return parentDsl.getCalculator();
  }

  private Bean2bean getCore() {
    return parentDsl.getCore();
  }
}
