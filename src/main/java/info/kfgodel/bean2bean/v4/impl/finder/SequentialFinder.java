package info.kfgodel.bean2bean.v4.impl.finder;

import ar.com.kfgodel.nary.api.optionals.Optional;
import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.process.ConversionProcess;

import java.util.List;
import java.util.function.Function;

/**
 * This class implements a finder using multiple strategies, it tries one strategy after the other until it finds
 * a converter function or it runs out of strategies
 *
 * Date: 23/9/19 - 23:29
 */
public class SequentialFinder implements ConverterFunctionFinder {

  private List<ConverterFunctionFinder> strategies;

  @Override
  public <O> Optional<Function<ConversionProcess<O>, O>> findBestConverterFor(ConversionIntent<O> intent) {
    for (ConverterFunctionFinder strategy : strategies) {
      Optional<Function<ConversionProcess<O>, O>> found = strategy.findBestConverterFor(intent);
      if(found.isPresent()){
        return found;
      }
    }
    return Optional.empty();
  }

  public static SequentialFinder create(List<ConverterFunctionFinder> strategies) {
    SequentialFinder finder = new SequentialFinder();
    finder.strategies = strategies;
    return finder;
  }

}
