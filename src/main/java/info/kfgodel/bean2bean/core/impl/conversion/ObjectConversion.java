package info.kfgodel.bean2bean.core.impl.conversion;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.exceptions.Bean2BeanException;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.impl.descriptor.ObjectDescriptor;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;

import java.lang.reflect.Type;

/**
 * This class represents the task that {@link info.kfgodel.bean2bean.core.api.Bean2bean} can process
 * to convert an object from one domain to another domain (possibly generating a new object)
 *
 * Date: 12/02/19 - 00:06
 */
public class ObjectConversion implements Bean2beanTask {

  private Object source;
  private Type targetType;
  private DomainVector conversionVector;
  private B2bDsl dsl;

  public static ObjectConversion create(Object source, Type targetType, DomainVector conversionVector, B2bDsl dsl) {
    ObjectConversion conversion = new ObjectConversion();
    conversion.source = source;
    conversion.conversionVector = conversionVector;
    conversion.dsl = dsl;
    conversion.targetType = targetType;
    return conversion;
  }

  @Override
  public DomainVector getConversionVector(){
    return conversionVector;
  }

  @Override
  public Object getSource() {
    return source;
  }

  @Override
  public B2bDsl getDsl() {
    return dsl;
  }

  @Override
  public Object nestConversionFrom(Object sourceElement, Type expectedElementType) {
    try {
      return this.getDsl().convert().from(sourceElement).to(expectedElementType);
    } catch (Bean2BeanException e) {
      String message = "Failed conversion from " + describeSource() + " to " + describeTarget() + "\n"
        + "\tdue to: " + e.getMessage();
      throw new ConversionException(message, this, e);
    }
  }

  @Override
  public String describeSource() {
    ObjectDescriptor descriptor = ObjectDescriptor.create();
    return descriptor.describeSource(this.getSource(), this.getConversionVector().getSource());
  }

  @Override
  public String describeTarget() {
    ObjectDescriptor descriptor = ObjectDescriptor.create();
    return descriptor.describeTarget(this.getTargetType(), this.getConversionVector().getTarget());
  }

  @Override
  public Type getTargetType() {
    return targetType;
  }

  @Override
  public String toString() {
    return source + " -> " + targetType;
  }
}
