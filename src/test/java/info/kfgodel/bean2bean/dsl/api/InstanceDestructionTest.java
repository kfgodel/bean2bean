package info.kfgodel.bean2bean.dsl.api;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.api.exceptions.DestructionException;
import info.kfgodel.bean2bean.dsl.api.converters.StringDestructor;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.references.ConsumerRef;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class defines examples of instance destruction and its behavior
 * Date: 17/02/19 - 12:17
 */
@RunWith(JavaSpecRunner.class)
public class InstanceDestructionTest extends JavaSpec<B2bTestContext> {
  public static Logger LOG = LoggerFactory.getLogger(InstanceDestructionTest.class);

  @Override
  public void define() {
    describe("a b2b dsl", () -> {
      context().dsl(Dsl::create);

      describe("with a default configuration", () -> {

        itThrows(DestructionException.class, "when any destruction is attempted", () -> {
          test().dsl().destroy().object("any object");
        }, e -> {
          assertThat(e).hasMessage("Destruction of \"any object\" failed: No converter found from \"any object\" ∈ {java.lang.String} to {info.kfgodel.bean2bean.dsl.api.Nothing}");
        });
      });

      describe("when a destructor is configured", () -> {
        StringDestructor converterFunction = StringDestructor.create();

        beforeEach(() -> {
          test().dsl().configure().useConverter(converterFunction);
        });

        it("executes the destructor with the destroyed instance", () -> {
          test().dsl().destroy().object("an object");
          assertThat(converterFunction.getLastArgument()).isEqualTo("an object");
        });
      });

      describe("when a destructor lambda is configured", () -> {
        AtomicReference<String> argument = new AtomicReference<>();

        beforeEach(() -> {
          test().dsl().configure().useConverter(new ConsumerRef<String>(argument::set){});
        });

        it("executes the destructor with the destroyed instance", () -> {
          test().dsl().destroy().object("an object");
          assertThat(argument.get()).isEqualTo("an object");
        });

      });


    });
  }

}