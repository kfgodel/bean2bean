package info.kfgodel.bean2bean.v3.other.references;

import info.kfgodel.bean2bean.v3.other.types.extraction.TypeArgumentExtractor;

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
    return TypeArgumentExtractor.create()
      .getArgumentUsedFor(TypeRef.class, getClass())
      .orElseThrow(()-> new IllegalStateException(TypeRef.class.getSimpleName() + " should be parameterized when extended"));
  }

}
