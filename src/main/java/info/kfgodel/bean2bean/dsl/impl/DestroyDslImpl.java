package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.api.exceptions.DestructionException;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.DestroyDsl;

import javax.lang.model.type.NullType;

/**
 * This class is the default implemenmtation for the destruction dsl
 * Date: 17/02/19 - 12:36
 */
public class DestroyDslImpl implements DestroyDsl {

  private B2bDsl b2bDsl;

  @Override
  public DestroyDsl object(Object anObject) {
    try {
      b2bDsl.convert().from(anObject).to(NullType.class);
      return this;
    } catch (ConversionException e) {
      throw new DestructionException("Destruction from "+anObject+" to null failed: " + e.getMessage(),anObject,e);
    }
  }

  public static DestroyDslImpl create(B2bDsl dsl) {
    DestroyDslImpl destroyDsl = new DestroyDslImpl();
    destroyDsl.b2bDsl = dsl;
    return destroyDsl;
  }

}
