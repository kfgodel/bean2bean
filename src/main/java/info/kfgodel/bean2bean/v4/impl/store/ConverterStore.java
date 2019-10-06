package info.kfgodel.bean2bean.v4.impl.store;

import ar.com.kfgodel.nary.api.optionals.Optional;
import info.kfgodel.bean2bean.v4.impl.process.ConversionProcess;
import info.kfgodel.bean2bean.v4.impl.vector.ConversionVector;

import java.util.function.Function;

/**
 * This interface represents a store where converters are available to be retrieved when needed
 * Date: 24/9/19 - 17:53
 */
public interface ConverterStore {

  <O> Optional<Function<ConversionProcess<O>, O>> retrieveFor(ConversionVector vector);

  <O> ConverterStore useFor(ConversionVector vector, Function<ConversionProcess<O>, O> converterFunction);
}
