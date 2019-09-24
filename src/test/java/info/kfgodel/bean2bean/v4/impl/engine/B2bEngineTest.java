package info.kfgodel.bean2bean.v4.impl.engine;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.nary.api.optionals.Optional;
import info.kfgodel.bean2bean.v4.B2bTestContext;
import info.kfgodel.bean2bean.v4.api.exceptions.B2bException;
import info.kfgodel.bean2bean.v4.impl.finder.ConverterFunctionFinder;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static info.kfgodel.bean2bean.mockito.MockitoExtensions.mockear;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

/**
 * Date: 23/9/19 - 16:37
 */
@RunWith(JavaSpecRunner.class)
public class B2bEngineTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a b2b engine", () -> {
      test().engine(()-> DefaultEngine.create(test().finder()));

      describe("given a converter function finder", () -> {
        test().finder(()-> mockear(ConverterFunctionFinder.class));

        describe("when the finder finds no converter", () -> {
          beforeEach(()->{
            Mockito.when(test().finder().findBestConverterFor(any()))
              .thenReturn(Optional.empty());
          });

          itThrows(B2bException.class, "if the finder can't find a converter for the given intention",  ()->{
            test().engine().apply(mockear(ConversionIntent.class));
          }, e -> {
            assertThat(e.getMessage()).startsWith("No converter found for Mock for ConversionIntent");
          });
        });

        describe("when the finder finds a converter", () -> {
          beforeEach(()->{
            Mockito.when(test().finder().findBestConverterFor(any()))
              .thenReturn(Optional.of((input)-> 23));
          });

          it("uses the converter function to do the conversion",()->{
            Object result = test().engine().apply(mockear(ConversionIntent.class));
            assertThat(result).isEqualTo(23);
          });   
        });
      });

    });
  }
}