package info.kfgodel.bean2bean.dsl.impl.scopes;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.api.scopes.ScopedRegistrationDsl;
import info.kfgodel.bean2bean.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.other.references.ConsumerRef;
import info.kfgodel.bean2bean.other.references.FunctionRef;
import info.kfgodel.bean2bean.other.references.SupplierRef;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Date: 11/03/19 - 22:16
 */
public class ScopedRegistrationDslImpl<I,O> implements ScopedRegistrationDsl<I,O> {

  private DomainVector domainVector;
  private ExplicitScopeWithDefinedSourceImpl<I> parentDsl;

  @Override
  public ScopedRegistrationDsl<I, O> useConverter(Function<? super I, ? extends O> converterFunction) {
    return this;
  }

  @Override
  public ScopedRegistrationDsl<I, O> useConverter(BiFunction<? super I, Bean2beanTask, ? extends O> converterFunction) {
    return this;
  }

  @Override
  public ScopedRegistrationDsl<I, O> useConverter(Supplier<? extends O> converterFunction) {
    return this;
  }

  @Override
  public ScopedRegistrationDsl<I, O> useConverter(Consumer<? super I> converterFunction) {
    return this;
  }

  @Override
  public ScopedRegistrationDsl<I, O> useConverter(FunctionRef<? super I, ? extends O> converterFunctionRef) {
    return this;
  }

  @Override
  public ScopedRegistrationDsl<I, O> useConverter(BiFunctionRef<? super I, Bean2beanTask, ? extends O> converterFunction) {
    return this;
  }

  @Override
  public ScopedRegistrationDsl<I, O> useConverter(SupplierRef<? extends O> converterFunction) {
    return this;
  }

  @Override
  public ScopedRegistrationDsl<I, O> useConverter(ConsumerRef<? super I> converterFunction) {
    return this;
  }

  public static <I,O> ScopedRegistrationDslImpl<I,O> create(DomainVector domainVector, ExplicitScopeWithDefinedSourceImpl<I> parentDsl) {
    ScopedRegistrationDslImpl<I,O> dsl = new ScopedRegistrationDslImpl<>();
    dsl.domainVector = domainVector;
    dsl.parentDsl = parentDsl;
    return dsl;
  }

}
