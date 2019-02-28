package info.kfgodel.bean2bean.core.impl.conversion;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;

import java.lang.reflect.Type;

/**
 * This class represents the definition of an expected conversion
 * Date: 12/02/19 - 00:06
 */
public class ObjectConversion implements Bean2beanTask {

  private Object source;
  private Type targetType;
  private DomainVector conversionVector;

  public static ObjectConversion create(Object source, Type targetType, DomainVector conversionVector) {
    ObjectConversion conversion = new ObjectConversion();
    conversion.source = source;
    conversion.conversionVector = conversionVector;
    conversion.targetType = targetType;
    return conversion;
  }

  public DomainVector getConversionVector(){
    return conversionVector;
  }

  public ConversionException exceptionForMissingConverter() {
    return new ConversionException("No converter found from " + source + getConversionVector().getSource() + " to " + getConversionVector().getTarget(), source, conversionVector);
  }

  public Object getSource() {
    return source;
  }

  public Type getTargetType() {
    return targetType;
  }
}
