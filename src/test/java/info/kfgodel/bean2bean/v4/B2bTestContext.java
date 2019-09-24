package info.kfgodel.bean2bean.v4;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import info.kfgodel.bean2bean.v4.impl.engine.B2bEngine;
import info.kfgodel.bean2bean.v4.impl.engine.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.finder.ConverterFunctionFinder;
import info.kfgodel.bean2bean.v4.impl.process.ConversionProcess;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Date: 23/9/19 - 16:37
 */
public interface B2bTestContext extends TestContext {

  B2bEngine engine();
  void engine(Supplier<B2bEngine> definition);

  ConverterFunctionFinder finder();
  void finder(Supplier<ConverterFunctionFinder> definition);

  ConversionProcess process();
  void process(Supplier<ConversionProcess> definition);

  ConversionIntent intent();
  void intent(Supplier<ConversionIntent> definition);

  Function converter();
  void converter(Supplier<Function> definition);

  RuntimeException exception();
  void exception(Supplier<RuntimeException> definition);


}
