package info.kfgodel.bean2bean.other;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This class serves as a way to reference parameterized types and retain its type parameters on runtime.<br>
 * By creating subclasses of this type you can get a {@link java.lang.reflect.Type} instance with full type arguments
 * and use it for conversions.<br>
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
    Type[] actualTypeArguments = getActualTypeArgumentsFrom(getClass(), TypeRef.class);
    Type actualTypeArgument = actualTypeArguments[0];
    return actualTypeArgument;
  }

  public static <T> Type[] getActualTypeArgumentsFrom(Class<? extends T> aClass, Class<? super T> expectedSuperType) {
    checkSimpleInheritance(aClass, expectedSuperType);
    ParameterizedType parameterizedClass = (ParameterizedType) aClass.getGenericSuperclass();
    return parameterizedClass.getActualTypeArguments();
  }

  private static void checkSimpleInheritance(Class aClass, Class expectedSuperType) {
    Class<?> superclass = aClass.getSuperclass();
    if (!superclass.equals(expectedSuperType)) {
      throw new IllegalStateException(expectedSuperType.getSimpleName() + " should have only one level subclass. " + superclass + " is in the middle of the inheritance");
    }
  }
}
