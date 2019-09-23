package info.kfgodel.bean2bean.v3.dsl.impl;

import info.kfgodel.bean2bean.v3.core.api.Bean2bean;
import info.kfgodel.bean2bean.v3.core.api.exceptions.Bean2BeanException;
import info.kfgodel.bean2bean.v3.core.api.registry.Domain;
import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.v3.core.impl.conversion.ObjectConversion;
import info.kfgodel.bean2bean.v3.core.impl.registry.domains.DomainCalculator;
import info.kfgodel.bean2bean.v3.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.v3.dsl.api.SourceDefinedConversionDsl;
import info.kfgodel.bean2bean.v3.other.references.TypeRef;

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
    return to((Type) outputClass);
  }

  @Override
  public <O> O to(TypeRef<O> outputTypeRef) throws Bean2BeanException {
    Type reference = outputTypeRef.getReference();
    return to(reference);
  }

  @Override
  public <O> O to(Type outputType) throws Bean2BeanException {
    Domain targetDomain = domainFor(outputType);
    ObjectConversion conversion = createConversionTo(outputType, targetDomain);
    return processConversion(conversion);
  }

  private Domain domainFor(Type aType) {
    return getCalculator().forType(aType);
  }

  private ObjectConversion createConversionTo(Type targetType, Domain targetDomain) {
    DomainVector conversionVector = DomainVector.create(sourceDomain, targetDomain);
    return ObjectConversion.create(this.value, targetType, conversionVector, getCore());
  }

  private B2bDsl getDsl() {
    return parentDsl.getDsl();
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
