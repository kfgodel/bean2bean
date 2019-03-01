package info.kfgodel.bean2bean.core.impl.conversion;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;

/**
 * This class represents the task that {@link info.kfgodel.bean2bean.core.api.Bean2bean} can process
 * to convert an object from one domain to another domain (possibly generating a new object)
 * 
 * Date: 12/02/19 - 00:06
 */
public class ObjectConversion implements Bean2beanTask {

  private Object source;
  private DomainVector conversionVector;
  private B2bDsl dsl;

  public static ObjectConversion create(Object source, DomainVector conversionVector, B2bDsl dsl) {
    ObjectConversion conversion = new ObjectConversion();
    conversion.source = source;
    conversion.conversionVector = conversionVector;
    conversion.dsl = dsl;
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
}
