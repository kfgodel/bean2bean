package info.kfgodel.bean2bean.v3.dsl.impl;

import info.kfgodel.bean2bean.v3.core.api.exceptions.Bean2BeanException;
import info.kfgodel.bean2bean.v3.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.v3.core.api.exceptions.NestedConversionException;
import info.kfgodel.bean2bean.v3.core.impl.descriptor.ObjectDescriptor;
import info.kfgodel.bean2bean.v3.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.v3.dsl.api.CreateDsl;
import info.kfgodel.bean2bean.v3.dsl.api.Nothing;
import info.kfgodel.reflect.references.TypeRef;

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
    } catch (NestedConversionException e) {
      throw e;
    } catch (Exception e) {
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
    } catch (NestedConversionException e) {
      throw e;
    } catch (Exception e) {
      throw new CreationException("Creation from "+expectedArrayClass+" to " + expectedArrayClass.getTypeName() + " failed: " + e.getMessage(), expectedArrayClass,e);
    }
  }

  public static CreateDslImpl create(B2bDsl b2bDsl) {
    CreateDslImpl createDsl = new CreateDslImpl();
    createDsl.dsl = b2bDsl;
    return createDsl;
  }

}
