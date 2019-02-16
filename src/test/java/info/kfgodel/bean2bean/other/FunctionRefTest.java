package info.kfgodel.bean2bean.other;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type verifies the behavior for a function reference
 * Date: 16/02/19 - 13:04
 */
@RunWith(JavaSpecRunner.class)
public class FunctionRefTest extends JavaSpec<TypeRefTestContext> {
  @Override
  public void define() {
    describe("a function reference", () -> {
      test().functionRef(() -> new FunctionRef<String, Integer>(Integer::parseInt) {});

      it("allows access to the function type arguments", () -> {
        assertThat(test().functionRef().getInputType()).isEqualTo(String.class);
        assertThat(test().functionRef().getActualTypeArguments()[0]).isEqualTo(String.class);

        assertThat(test().functionRef().getOutputType()).isEqualTo(Integer.class);
        assertThat(test().functionRef().getActualTypeArguments()[1]).isEqualTo(Integer.class);
      });

      it("allows access to the function", () -> {
        Function<String, Integer> referencedFunction = test().functionRef().getFunction();

        Integer result = referencedFunction.apply("23");

        assertThat(result).isSameAs(23);
      });
    });

  }

}