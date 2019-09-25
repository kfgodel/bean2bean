package info.kfgodel.bean2bean.v4.impl.process;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.v4.B2bTestContext;
import info.kfgodel.bean2bean.v4.api.exceptions.B2bException;
import info.kfgodel.bean2bean.v4.impl.engine.B2bEngine;
import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import org.junit.runner.RunWith;

import static info.kfgodel.bean2bean.mockito.MockitoExtensions.anIrrelevant;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 23/9/19 - 22:43
 */
@RunWith(JavaSpecRunner.class)
public class DefaultProcessTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a conversion process", () -> {
      test().process(() -> DefaultProcess.create(test().intent(), test().converter(), test().engine()));

      describe("given a converter and an intent with the engines", () -> {
        test().intent(() -> anIrrelevant(ConversionIntent.class));
        test().engine(() -> anIrrelevant(B2bEngine.class));
        test().converter(() ->
          (input) -> "a conversion result"
        );

        it("uses the converter when executed", () -> {
          Object result = test().process().execute();
          assertThat(result).isEqualTo("a conversion result");
        });

        describe("when the converter fails", () -> {
          test().converter(()->
            (input) -> {throw test().exception();}
          );

          itThrows(B2bException.class, "wrapping the exception if not a b2b exception", ()->{
            test().exception(()-> new RuntimeException("converter error"));
            test().process().execute();
          }, e-> {
            assertThat(e.getMessage()).startsWith("Converter function failed with: \"converter error\" when processing info.kfgodel.bean2bean.v4.impl.process.DefaultProcess");
          });

          itThrows(B2bException.class, "forwarding the original exception if a b2b exception", ()->{
            test().exception(()-> new B2bException("a bubbling error"));
            test().process().execute();
          }, e->{
            assertThat(e).hasMessage("a bubbling error");
          });
        });

      });

    });

  }

}