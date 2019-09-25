package info.kfgodel.bean2bean.v4.impl.store;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.nary.api.optionals.Optional;
import info.kfgodel.bean2bean.v4.B2bTestContext;
import info.kfgodel.bean2bean.v4.impl.process.ConversionProcess;
import info.kfgodel.bean2bean.v4.impl.vector.ConversionVector;
import info.kfgodel.bean2bean.v4.impl.vector.Vector;
import org.junit.runner.RunWith;

import java.util.function.Function;

import static info.kfgodel.bean2bean.mockito.MockitoExtensions.anIrrelevant;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 24/9/19 - 18:33
 */
@RunWith(JavaSpecRunner.class)
public class DefaultStoreTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a converter store", () -> {
      test().store(DefaultStore::create);

      describe("given a vector", () -> {
        test().vector(()-> anIrrelevant(ConversionVector.class));

        describe("when created", () -> {
          it("has no converter", () -> {
            Optional<Function<ConversionProcess<Object>, Object>> found = test().store().retrieveFor(test().vector());
            assertThat(found).isEmpty();
          });
        });

        describe("when a converter is stored", () -> {
          test().converter(() -> anIrrelevant(Function.class));
          beforeEach(() -> {
            test().store().useFor(test().vector(), test().converter());
          });

          it("can retrieve the converter function", () -> {
            Optional<Function<ConversionProcess<Object>, Object>> found = test().store().retrieveFor(test().vector());
            assertThat(found).containsExactly(test().converter());
          });

          it("returns empty if other vector is used to retrieve the converter", () -> {
            ConversionVector otherVector = Vector.create(1, 2);
            Optional<Function<ConversionProcess<Object>, Object>> found = test().store().retrieveFor(otherVector);
            assertThat(found).isEmpty();
          });
        });

      });


    });

  }
}