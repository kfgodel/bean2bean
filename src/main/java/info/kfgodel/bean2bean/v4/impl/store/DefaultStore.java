package info.kfgodel.bean2bean.v4.impl.store;

import ar.com.kfgodel.nary.api.optionals.Optional;
import info.kfgodel.bean2bean.v4.impl.process.ConversionProcess;
import info.kfgodel.bean2bean.v4.impl.vector.ConversionVector;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class is the default implementation using conversion vector as key for each converter
 * Date: 24/9/19 - 18:32
 */
public class DefaultStore implements ConverterStore {

  private Map<ConversionVector, Function> convertersByVector;

  public static DefaultStore create() {
    DefaultStore store = new DefaultStore();
    store.convertersByVector = new HashMap<>();
    return store;
  }

  @Override
  public <O> Optional<Function<ConversionProcess<O>, O>> retrieveFor(ConversionVector vector) {
    return Optional.ofNullable(convertersByVector.get(vector));
  }

  @Override
  public <O> ConverterStore storeFor(ConversionVector vector, Function<ConversionProcess<O>, O> converterFunction) {
    convertersByVector.put(vector, converterFunction);
    return this;
  }
}
