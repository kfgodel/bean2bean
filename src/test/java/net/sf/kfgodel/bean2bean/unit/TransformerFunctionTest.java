package net.sf.kfgodel.bean2bean.unit;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class specifies the expected behavior of a transformer function
 *
 * Created by kfgodel on 03/07/16.
 */
@RunWith(JavaSpecRunner.class)
public class TransformerFunctionTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a transformer function", () -> {
      context().transfunction(() -> String::valueOf);

      it("generates output from its input", () -> {
        Object output = context().transfunction().apply(1);
        assertThat(output).isEqualTo("1");
      });

      it("is a java 8 function [to improve integration with others]",()->{
        assertThat(context().transfunction()).isInstanceOfAny(Function.class);
      });
      
      it("could take null as input",()->{
        Object apply = context().transfunction().apply(null);
        assertThat(apply).isEqualTo("null");
      });

      it("could generate null as result",()->{
        context().transfunction(()-> (input)-> null);

        Object output = context().transfunction().apply("something");

        assertThat(output).isNull();
      });

    });

  }
}