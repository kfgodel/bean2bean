package info.kfgodel.bean2bean.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import org.junit.runner.RunWith;

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


    });

  }
}
