package info.kfgodel.bean2bean.v4.impl.engine;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import info.kfgodel.bean2bean.v4.B2bTestContext;
import info.kfgodel.bean2bean.v4.impl.finder.ConverterFunctionFinder;
import info.kfgodel.bean2bean.v4.impl.finder.ExactVectorFinder;
import info.kfgodel.bean2bean.v4.impl.finder.SequentialFinder;
import info.kfgodel.bean2bean.v4.impl.finder.SetHierarchyFinder;
import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.sets.TypeBasedSet;
import info.kfgodel.bean2bean.v4.impl.store.DefaultStore;
import info.kfgodel.bean2bean.v4.impl.vector.Vector;
import org.junit.runner.RunWith;

import java.util.Collection;

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
          // TODO: This won't be needed when correct type argument inference is implemented
          ConverterFunctionFinder directFinder = ExactVectorFinder.create(test().store());
          ConverterFunctionFinder hierarchyFinder = SetHierarchyFinder.create(test().store());
          return SequentialFinder.create(Lists.newArrayList(directFinder, hierarchyFinder));
        });
        test().store(DefaultStore::create);

        describe("when there's an exact matching converter on the store", () -> {
          beforeEach(()->{
            test().store().useFor(Vector.create(TypeBasedSet.create(String.class),TypeBasedSet.create(Integer.class)), (process)->{
              String text = process.getIntent().getInput();
              return Integer.parseInt(text);
            });
          });

          it("converts an input value using the converter into an output",()->{
            ConversionIntent<Integer> intent = TypeConversionIntent.create("88", TypeBasedSet.create(Integer.class));
            Integer result = test().engine().apply(intent);
            assertThat(result).isEqualTo(88);
          });
        });

        describe("when there's a matching supertype converter on the store", () -> {
          beforeEach(()->{

            // Replace line when type argument inference is correctly implemented
            //test().store().useFor(Vector.create(TypeBasedSet.create(Collection.class, String.class),TypeBasedSet.create(Number.class)), (process)->{
            test().store().useFor(Vector.create(TypeBasedSet.create(Collection.class),TypeBasedSet.create(Number.class)), (process)->{
              // This converter takes the first element and parses it as double, just as an example for this test
              Collection<String> collection = process.getIntent().getInput();
              final String firstTextInTheCollection = collection.stream().findFirst().get();
              return Double.parseDouble(firstTextInTheCollection);
            });
          });

          it("converts a subtype input value using the converter",()->{
            ConversionIntent<Double> intent = TypeConversionIntent.create(Lists.newArrayList("3.1459"), TypeBasedSet.create(Double.class));
            Double result = test().engine().apply(intent);
            assertThat(result).isEqualTo(3.1459);
          });
        });

      });

    });
  }
}