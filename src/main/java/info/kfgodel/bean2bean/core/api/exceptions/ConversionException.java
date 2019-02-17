package info.kfgodel.bean2bean.core.api.exceptions;

import info.kfgodel.bean2bean.core.impl.registry.DomainVector;

/**
 * This type represents an error during a conversion attempt
 * Date: 16/02/19 - 18:50
 */
public class ConversionException extends Bean2BeanException {

  private final Object source;
  private final DomainVector conversionVector;

  public ConversionException(String message, Object source, DomainVector conversionVector) {
    super(message);
    this.conversionVector = conversionVector;
    this.source = source;
  }

  public Object getSource() {
    return source;
  }

  public DomainVector getConversionVector() {
    return conversionVector;
  }
}
