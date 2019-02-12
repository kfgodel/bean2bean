package info.kfgodel.bean2bean.other;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This class serves as a way to reference parameterized types and retain its type parameters on runtime.<br>
 * By creating subclasses of this type you can get a {@link java.lang.reflect.Type} instance with full type arguments
 * and use it for conversions.<br>
 * <p>
 * <p>
 * <p>
 * Date: 12/02/19 - 20:09
 *
 * @param <T> Type variable defined by subclass
 */
public abstract class TypeRef<T> {

  /**
   * Extracts the type argument used in the subclass and returns it
   *
   * @return The complete generic type argument used to define "T"
   */
  public Type getReference() {
    checkSimpleInheritance();
    ParameterizedType parameterizedClass = (ParameterizedType) getClass().getGenericSuperclass();
    Type actualTypeArgument = parameterizedClass.getActualTypeArguments()[0];
    return actualTypeArgument;
  }

  private void checkSimpleInheritance() {
    Class<?> superclass = getClass().getSuperclass();
    if (!superclass.equals(TypeRef.class)) {
      throw new IllegalStateException("TypeRef should have only one level subclass. " + superclass + " is in the middle of the inheritance");
    }
  }
}
