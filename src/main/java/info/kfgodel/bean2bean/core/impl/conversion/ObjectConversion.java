package info.kfgodel.bean2bean.core.impl.conversion;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.Bean2BeanException;
import info.kfgodel.bean2bean.other.TypeVector;

import java.lang.reflect.Type;

/**
 * This class represents the definition of an expected conversion
 * Date: 12/02/19 - 00:06
 */
public class ObjectConversion implements Bean2beanTask {

  private Object source;
  private Type destinationType;

  public static ObjectConversion create(Object source, Type destination) {
    ObjectConversion conversion = new ObjectConversion();
    conversion.source = source;
    conversion.destinationType = destination;
    return conversion;
  }

  public TypeVector getConversionVector() {
    return TypeVector.create(getSourceType(), destinationType);
  }

  private Class<?> getSourceType() {
    return source.getClass();
  }

  public Bean2BeanException exceptionForMissingConverter() {
    return new Bean2BeanException("No converter found from "+source+"("+getSourceType().getName()+") to " + destinationType.getTypeName());
  }

  public Object getSource() {
    return source;
  }
}
