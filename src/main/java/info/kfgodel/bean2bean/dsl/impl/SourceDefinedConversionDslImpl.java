package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.Bean2bean;
import info.kfgodel.bean2bean.core.api.exceptions.B2bException;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;
import info.kfgodel.bean2bean.dsl.api.SourceDefinedConversionDsl;

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
  public <O> O to(Class<O> outputClass) throws B2bException {
    ObjectConversion conversion = ObjectConversion.create(source, outputClass);
    return getCore().process(conversion);
  }

  private Bean2bean getCore() {
    return parentDsl.getCore();
  }
}
