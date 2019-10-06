package info.kfgodel.bean2bean.v4.impl.engine;

import info.kfgodel.bean2bean.v4.api.exceptions.B2bException;
import info.kfgodel.bean2bean.v4.impl.finder.ConverterFunctionFinder;
import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.process.ConversionProcess;
import info.kfgodel.bean2bean.v4.impl.process.DefaultProcess;

import java.util.function.Function;

/**
 * Default implementation for the b2b engine
 * Date: 23/9/19 - 16:39
 */
public class DefaultEngine implements B2bEngine {

  private ConverterFunctionFinder finder;

  @Override
  public <O> O apply(ConversionIntent<O> intent) throws B2bException {
    Function<ConversionProcess<O>, O> converter = finder.findBestConverterFor(intent)
      .orElseThrow(() -> new B2bException("No converter found for " + intent));
    DefaultProcess<O> conversionProcess = DefaultProcess.create(intent, converter, this);
    O result = conversionProcess.execute();
    return result;
  }

  public static DefaultEngine create(ConverterFunctionFinder finder) {
    DefaultEngine engine = new DefaultEngine();
    engine.finder = finder;
    return engine;
  }

}
