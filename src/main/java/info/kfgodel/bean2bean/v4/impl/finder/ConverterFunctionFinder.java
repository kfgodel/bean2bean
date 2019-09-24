package info.kfgodel.bean2bean.v4.impl.finder;

import ar.com.kfgodel.nary.api.optionals.Optional;
import info.kfgodel.bean2bean.v4.impl.engine.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.process.ConversionProcess;

import java.util.function.Function;

/**
 * This interface represents the finder that can look into a converter store for the best converter function that
 * statisfies a conversion process
 * Date: 23/9/19 - 21:51
 */
public interface ConverterFunctionFinder {

  /**
   * Looks into the store of converters to find the best suited for the given process
   * @param process The process that needs a converter to be fulfilled
   * @param <O> The type of expected result
   * @return The function that, when invoked, can make the conversion
   */
  <O> Optional<Function<ConversionProcess<O>, O>> findBestConverterFor(ConversionIntent<O> process);

}
