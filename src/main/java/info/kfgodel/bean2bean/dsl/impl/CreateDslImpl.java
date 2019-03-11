package info.kfgodel.bean2bean.dsl.impl;

import info.kfgodel.bean2bean.core.api.exceptions.Bean2BeanException;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.core.impl.descriptor.ObjectDescriptor;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.dsl.api.CreateDsl;
import info.kfgodel.bean2bean.dsl.api.Nothing;
import info.kfgodel.bean2bean.other.references.TypeRef;

import java.lang.reflect.Type;

/**
 * Implementation for the creation dsl
 * Date: 16/02/19 - 19:05
 */
public class CreateDslImpl implements CreateDsl {

  private B2bDsl dsl;

  @Override
  public <T> T anInstanceOf(Class<T> expectedTypeClass) throws Bean2BeanException {
    return anInstanceOf((Type) expectedTypeClass);
  }

  @Override
  public <T> T anInstanceOf(Type expectedType) throws Bean2BeanException {
    try {
      return dsl.convert().from(Nothing.INSTANCE).to(expectedType);
    } catch (ConversionException e) {
      String typeDescription = ObjectDescriptor.create().describeType(expectedType);
      throw new CreationException("Creation of " + typeDescription + " failed: " + e.getMessage(), expectedType ,e);
    }
  }

  @Override
  public <T> T anInstanceOf(TypeRef<T> expectedTypeRef) throws Bean2BeanException {
    return anInstanceOf(expectedTypeRef.getReference());
  }

  @Override
  public <E> E[] anArrayOf(int arraySize, Class<E[]> expectedArrayClass) throws Bean2BeanException {
    try {
      return dsl.convert().from(arraySize).to(expectedArrayClass);
    } catch (ConversionException e) {
      throw new CreationException("Creation from "+expectedArrayClass+" to " + expectedArrayClass.getTypeName() + " failed: " + e.getMessage(), expectedArrayClass,e);
    }
  }

  public static CreateDslImpl create(B2bDsl b2bDsl) {
    CreateDslImpl createDsl = new CreateDslImpl();
    createDsl.dsl = b2bDsl;
    return createDsl;
  }

}
