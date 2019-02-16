package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.api.exceptions.Bean2BeanException;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;
import info.kfgodel.bean2bean.dsl.api.SourceDefinedConversionDsl;
import info.kfgodel.bean2bean.other.TypeRef;

/**
 * Partial definition of a conversion with the source object defined
 * Date: 10/02/19 - 23:20
 */
public class SourceDefinedConversionDslImpl<I> implements SourceDefinedConversionDsl<I> {

  private ConvertDslImpl parentDsl;
  private I source;

  public static<I> SourceDefinedConversionDslImpl<I> create(ConvertDslImpl convertDsl, I source) {
    SourceDefinedConversionDslImpl<I> sourceDefinedConversionDsl = new SourceDefinedConversionDslImpl<>();
    sourceDefinedConversionDsl.parentDsl = convertDsl;
    sourceDefinedConversionDsl.source = source;
    return sourceDefinedConversionDsl;
  }

  @Override
  public <O> O to(Class<O> outputClass) throws Bean2BeanException {
    ObjectConversion conversion = ObjectConversion.create(source, outputClass);
    return processConversion(conversion);
  }

  @Override
  public <O> O to(TypeRef<O> outputTypeRef) throws Bean2BeanException {
    ObjectConversion conversion = ObjectConversion.create(source, outputTypeRef.getReference());
    return processConversion(conversion);
  }

  private <O> O processConversion(ObjectConversion conversion) {
    return getCore().process(conversion);
  }

  private Bean2bean getCore() {
    return parentDsl.getCore();
  }
}
