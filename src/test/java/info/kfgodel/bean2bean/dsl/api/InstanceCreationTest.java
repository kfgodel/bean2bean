package info.kfgodel.bean2bean.dsl.api;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.dsl.api.converters.ArrayListGenerator;
import info.kfgodel.bean2bean.dsl.api.converters.StringArrayGenerator;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class defines examples of instance creation and its behavior
 * Date: 16/02/19 - 18:46
 */
@RunWith(JavaSpecRunner.class)
public class InstanceCreationTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a b2b dsl", () -> {
      context().dsl(Dsl::create);

      describe("with a default configuration", () -> {

        itThrows(CreationException.class, "when any creation is attempted", () -> {
          test().dsl().generate().anInstanceOf(List.class);
        }, e -> {
          assertThat(e).hasMessage("Creation from null to java.util.List failed: No converter found from null(javax.lang.model.type.NullType) to java.util.List");
        });
      });

      describe("when a converter from null is configured", () -> {
        beforeEach(() -> {
          test().dsl().configure().usingConverter(ArrayListGenerator.create());
        });

        it("is used for instance creation", () -> {
          List list = test().dsl().generate().anInstanceOf(List.class);
          assertThat(list)
            .isNotNull()
            .isEmpty();
        });
      });

      describe("when a converter from integer is configured", () -> {
        beforeEach(()->{
          test().dsl().configure().usingConverter(StringArrayGenerator.create());
        });

        it("is used for array creation",()->{
          String[] createdArray = test().dsl().generate().anArrayOf(3, String[].class);
          assertThat(createdArray)
            .isNotNull()
            .hasSize(3);
        });
      });


    });

  }
}