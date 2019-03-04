package info.kfgodel.bean2bean.other.references;

import info.kfgodel.bean2bean.other.types.extraction.TypeArgumentExtractor;

import java.lang.reflect.Type;
import java.util.function.Supplier;

/**
 * This class serves as a way to declare supplier lambdas without losing
 * its type parameter. Subclasses of this type allows you to retain the
 * type argument erased by the compiler
 *
 * Date: 16/02/19 - 19:52
 */
public abstract class SupplierRef<O> {

  private final Supplier<O> supplier;

  public SupplierRef(Supplier<? extends O> supplier) {
    this.supplier = (Supplier<O>) supplier;
  }

  public Supplier<O> getSupplier() {
    return supplier;
  }

  public Type getOutputType() {
    return TypeArgumentExtractor.create()
      .getArgumentUsedFor(SupplierRef.class, getClass())
      .orElseThrow(()-> new IllegalArgumentException(SupplierRef.class.getSimpleName() + " should be parameterized when extended"));
  }
}
