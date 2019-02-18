package info.kfgodel.bean2bean.core.impl.registry;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;
import info.kfgodel.bean2bean.core.impl.registry.definitions.DefaultDefinition;
import info.kfgodel.bean2bean.dsl.api.B2bTestContext;
import org.junit.runner.RunWith;

import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 18/02/19 - 20:02
 */
@RunWith(JavaSpecRunner.class)
public class DefaultRegistryTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a b2b registry", () -> {
      test().registry(DefaultRegistry::create);

      describe("when created", () -> {
        it("has no registered converter", () -> {
          Optional<Function<ObjectConversion, Object>> found = test().registry().findBestConverterFor(aDomainVector());
          assertThat(found).isEmpty();
        });
      });

      describe("when a converter is registerd for the specific domain", () -> {
        beforeEach(() -> {
          test().registry().store(DefaultDefinition.create((in) -> in, aDomainVector()));
        });

        it("uses finds that converter", () -> {
          Optional<Function<ObjectConversion, Object>> found = test().registry().findBestConverterFor(aDomainVector());
          assertThat(found).isNotEmpty();
        });
      });

    });

  }

  private DomainVector aDomainVector() {
    return DomainVector.create(NamedDomain.create("any"), NamedDomain.create("any"));
  }
}