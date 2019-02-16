package info.kfgodel.bean2bean.dsl.api;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 16/02/19 - 18:46
 */
@RunWith(JavaSpecRunner.class)
public class InstanceCreationTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a b2b dsl", () -> {
      context().dsl(Dsl::create);

      describe("with a default configuration", () -> {

        itThrows(ConversionException.class, "when any creation is attempted", () -> {
          test().dsl().convert().from(null).to(List.class);
        }, e -> {
          assertThat(e).hasMessage("No converter found from null(javax.lang.model.type.NullType) to java.util.List");
        });
      });


    });

  }
}