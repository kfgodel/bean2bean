package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.exceptions.Bean2BeanException;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.CreateDsl;

/**
 * Implementation for the creation dsl
 * Date: 16/02/19 - 19:05
 */
public class CreateDslImpl implements CreateDsl {

  private B2bDsl dsl;

  @Override
  public <T> T anInstanceOf(Class<T> expectedTypeClass) throws Bean2BeanException {
    try {
      return dsl.convert().from(null).to(expectedTypeClass);
    } catch (ConversionException e) {
      throw new CreationException("Creation from null to " + expectedTypeClass.getTypeName() + " failed: " + e.getMessage(), expectedTypeClass ,e);
    }
  }

  public static CreateDslImpl create(B2bDsl b2bDsl) {
    CreateDslImpl createDsl = new CreateDslImpl();
    createDsl.dsl = b2bDsl;
    return createDsl;
  }

}
