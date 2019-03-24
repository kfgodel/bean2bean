package info.kfgodel.bean2bean.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.references.TypeRef;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class tests the conversion interaction between default converters when are all added to the config
 * Date: 24/03/19 - 14:22
 */
@RunWith(JavaSpecRunner.class)
public class DefaultConvertersTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("a b2b dsl with default converters", () -> {
      beforeEach(() -> {
        test().dsl().configure().usingDefaultConverters();
      });
      test().dsl(Dsl::create);

      it("can convert and object to itself", () -> {
        String source = "Hello World!";
        String result = test().dsl().convert().from(source).to(String.class);
        assertThat(result).isSameAs(source);
      });

      describe("for optionals", () -> {
        it("can extract the element inside an optional", () -> {
          String text = "Hello World!";
          String result = test().dsl().convert().from(Optional.of(text)).to(String.class);
          assertThat(result).isSameAs(text);
        });

        it("can wrap an object into an optional", () -> {
          String text = "Hello World!";
          Optional<String> result = test().dsl().convert().from(text).to(new TypeRef<Optional<String>>() {});
          assertThat(result.get()).isSameAs(text);
        });
      });


    });

  }
}
