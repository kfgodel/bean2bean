package info.kfgodel.bean2bean.other.types.descriptors;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * This class describes types that represent generified arrays
 * Date: 03/03/19 - 18:42
 */
public class GenericArrayTypeDescriptor extends GeneralTypeDescriptor {

  private GenericArrayType arrayType;

  @Override
  public Type getType() {
    return arrayType;
  }

  @Override
  public Optional<Class> getComponentType() {
    Type genericComponent = arrayType.getGenericComponentType();
    return JavaTypeDescriptor.createFor(genericComponent)
      .getErasuredType();
  }

  @Override
  public Optional<Class> getAssignableClass() {
    return getComponentType()
      .map(this::getArrayClassForComponentOf);
  }

  private Class getArrayClassForComponentOf(Class componentClass) {
    // Creating an array is the only way we have to know the array class
    Object temporalArray = Array.newInstance(componentClass, 0);
    return temporalArray.getClass();
  }

  public static GenericArrayTypeDescriptor create(GenericArrayType arrayType) {
    GenericArrayTypeDescriptor descriptor = new GenericArrayTypeDescriptor();
    descriptor.arrayType = arrayType;
    return descriptor;
  }

}
