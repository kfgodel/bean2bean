package info.kfgodel.bean2bean.v4.impl.finder;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.nary.api.optionals.Optional;
import info.kfgodel.bean2bean.v4.B2bTestContext;
import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.intent.Intent;
import info.kfgodel.bean2bean.v4.impl.store.DefaultStore;
import info.kfgodel.bean2bean.v4.impl.vector.Vector;
import org.junit.runner.RunWith;

import java.util.function.Function;

import static info.kfgodel.bean2bean.mockito.MockitoExtensions.anIrrelevant;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 24/9/19 - 19:34
 */
@RunWith(JavaSpecRunner.class)
public class DirectStoreFinderTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a direct store finder", () -> {
      test().finder(()-> DirectStoreFinder.create(test().store()));

      describe("given a converter store", () -> {
        test().store(DefaultStore::create);

        describe("when looking for the best converter for an intent", () -> {
          test().intent(()-> Intent.create(Vector.create(1,2)));

          describe("when the store is empty", () -> {
            it("finds no converter",()->{
              Optional found = test().finder().findBestConverterFor(test().intent());
              assertThat(found).isEmpty();
            });
          });

          describe("when the store has a converter that matches exactly the intent's vector", () -> {
            beforeEach(()->{
              test().store().useFor(Vector.create(1,2), anIrrelevant(Function.class));
            });

            it("finds the converter",()->{
              Optional found = test().finder().findBestConverterFor(test().intent());
              assertThat(found).isNotEmpty();
            });

            it("finds no converter if the intent's vector is different",()->{
              ConversionIntent otherIntent = Intent.create(Vector.create(3, 4));
              Optional found = test().finder().findBestConverterFor(otherIntent);
              assertThat(found).isEmpty();
            });
          });
        });

      });
    });
  }
}