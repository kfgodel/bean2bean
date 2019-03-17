package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.api.exceptions.DestructionException;
import info.kfgodel.bean2bean.core.api.exceptions.NestedConversionException;
import info.kfgodel.bean2bean.core.impl.descriptor.ObjectDescriptor;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.DestroyDsl;
import info.kfgodel.bean2bean.dsl.api.Nothing;

/**
 * This class is the default implemenmtation for the destruction dsl
 * Date: 17/02/19 - 12:36
 */
public class DestroyDslImpl implements DestroyDsl {

  private B2bDsl b2bDsl;

  @Override
  public DestroyDsl object(Object anObject) {
    try {
      b2bDsl.convert().from(anObject).to(Nothing.class);
      return this;
    } catch (NestedConversionException e) {
      throw e;
    } catch (ConversionException e) {
      String objectDescription = ObjectDescriptor.create().describeInstance(anObject);
      throw new DestructionException("Destruction of "+ objectDescription + " failed: " + e.getMessage(),anObject,e);
    }
  }

  public static DestroyDslImpl create(B2bDsl dsl) {
    DestroyDslImpl destroyDsl = new DestroyDslImpl();
    destroyDsl.b2bDsl = dsl;
    return destroyDsl;
  }

}
