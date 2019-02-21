package info.kfgodel.bean2bean.other;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.other.references.BiFunctionRef;
import org.junit.runner.RunWith;

import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 16/02/19 - 20:04
 */
@RunWith(JavaSpecRunner.class)
public class BiFunctionRefTest extends JavaSpec<TypeRefTestContext> {
  @Override
  public void define() {
    describe("a bifunction reference", () -> {
      test().bifunctionRef(()-> new BiFunctionRef<CharSequence, String, Integer>(this::exampleLambda) {});

      it("allows access to the function type arguments",()->{
        assertThat(test().bifunctionRef().getFirstInputType()).isEqualTo(CharSequence.class);
        assertThat(test().bifunctionRef().getSecondInputType()).isEqualTo(String.class);
        assertThat(test().bifunctionRef().getOutputType()).isEqualTo(Integer.class);
      });

      it("allows access to the function",()->{
        BiFunction<CharSequence, String, Integer> biFunction = test().bifunctionRef().getBiFunction();
        Integer result = biFunction.apply("hi", "lo");
        assertThat(result).isEqualTo(4);
      });
    });

  }

  private Integer exampleLambda(CharSequence charSequence, String s) {
    return charSequence.length() + s.length();
  }
}