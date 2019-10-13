package info.kfgodel.bean2bean.v4;

import info.kfgodel.bean2bean.v4.impl.engine.B2bEngine;
import info.kfgodel.bean2bean.v4.impl.finder.ConverterFunctionFinder;
import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.process.ConversionProcess;
import info.kfgodel.bean2bean.v4.impl.sets.Set;
import info.kfgodel.bean2bean.v4.impl.sets.TypeBasedSet;
import info.kfgodel.bean2bean.v4.impl.store.ConverterStore;
import info.kfgodel.bean2bean.v4.impl.vector.ConversionVector;
import info.kfgodel.jspek.api.contexts.TestContext;

import java.util.List;
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

  List<ConverterFunctionFinder> strategies();
  void strategies(Supplier<List<ConverterFunctionFinder>> definition);

  ConversionVector vector();
  void vector(Supplier<ConversionVector> definition);

  ConverterStore store();
  void store(Supplier<ConverterStore> definition);

  TypeBasedSet typeSet();
  void typeSet(Supplier<TypeBasedSet> definition);

  List<Set> supersets();
  void supersets(Supplier<List<Set>> definition);


}
