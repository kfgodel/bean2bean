package info.kfgodel.bean2bean.v4.impl.engine;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import info.kfgodel.bean2bean.v4.B2bTestContext;
import info.kfgodel.bean2bean.v4.impl.finder.ConverterFunctionFinder;
import info.kfgodel.bean2bean.v4.impl.finder.DirectStoreFinder;
import info.kfgodel.bean2bean.v4.impl.finder.SequentialFinder;
import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.store.DefaultStore;
import info.kfgodel.bean2bean.v4.impl.vector.Vector;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TODO: Reduce amount of verbosity is needed to setup the engine
 * Date: 25/9/19 - 18:47
 */
@RunWith(JavaSpecRunner.class)
public class EngineExampleTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("an engine", () -> {
      test().engine(()-> DefaultEngine.create(test().finder()));

      describe("given a finder with access to the store", () -> {
        test().finder(()->{
          ConverterFunctionFinder directFinder = DirectStoreFinder.create(test().store());
          return SequentialFinder.create(Lists.newArrayList(directFinder));
        });
        test().store(DefaultStore::create);

        describe("when there's a matching converter on the store", () -> {
          beforeEach(()->{
            test().store().useFor(Vector.create(String.class,Integer.class), (process)->{
              String text = process.getIntent().getInput();
              return Integer.parseInt(text);
            });
          });

          it("converts an input value using the converter into an output",()->{
            ConversionIntent<Integer> intent = TypeConversionIntent.create("88", Integer.class);
            Integer result = test().engine().apply(intent);
            assertThat(result).isEqualTo(88);
          });
        });
      });

    });
  }
}