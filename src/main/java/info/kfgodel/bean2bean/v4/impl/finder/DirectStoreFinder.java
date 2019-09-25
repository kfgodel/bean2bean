package info.kfgodel.bean2bean.v4.impl.finder;

import ar.com.kfgodel.nary.api.optionals.Optional;
import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.process.ConversionProcess;
import info.kfgodel.bean2bean.v4.impl.store.ConverterStore;
import info.kfgodel.bean2bean.v4.impl.vector.ConversionVector;

import java.util.function.Function;

/**
 * This finder looks in the store for the exact match of the conversion vector
 * Date: 24/9/19 - 19:27
 */
public class DirectStoreFinder implements ConverterFunctionFinder {

  private ConverterStore store;

  @Override
  public <O> Optional<Function<ConversionProcess<O>, O>> findBestConverterFor(ConversionIntent<O> intent) {
    ConversionVector conversionVector = intent.getVector();
    return store.retrieveFor(conversionVector);
  }

  public static DirectStoreFinder create(ConverterStore store) {
    DirectStoreFinder finder = new DirectStoreFinder();
    finder.store = store;
    return finder;
  }

}
