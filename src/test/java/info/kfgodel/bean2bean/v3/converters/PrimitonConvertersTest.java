package info.kfgodel.bean2bean.v3.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.v3.dsl.impl.Dsl;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 17/03/19 - 19:59
 */
@RunWith(JavaSpecRunner.class)
public class PrimitonConvertersTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("primiton converter functions when registered", () -> {
      beforeEach(() -> {
        PrimitonConverters.registerOn(test().dsl().configure());
      });
      test().dsl(Dsl::create);

      it("allows converting between numeric types", () -> {
        assertThat(test().dsl().convert().from(1).to(Double.class)).isEqualTo(1.0);
        assertThat(test().dsl().convert().from((short)3).to(float.class)).isEqualTo(3.0f);
      });

      it("allows boxing primitives",()->{
        // Due to auto-boxing this looks silly, but an actual conversion is needed for this to work
        assertThat(test().dsl().convert().from(1).to(Integer.class)).isEqualTo(Integer.valueOf(1));
        assertThat(test().dsl().convert().from((short)3).to(Short.class)).isEqualTo(Short.valueOf((short)3));
      });

      it("allows unboxing boxed types",()->{
        // Due to auto-unboxing this looks silly, but an actual conversion is needed for this to work
        assertThat(test().dsl().convert().from(Long.valueOf(4)).to(long.class)).isEqualTo(4L);
        assertThat(test().dsl().convert().from(Character.valueOf('r')).to(char.class)).isEqualTo('r');
      });

      it("adds identity converters for primitive types",()->{
        String aString = "a string";
        assertThat(test().dsl().convert().from(aString).to(String.class)).isSameAs(aString);
        assertThat(test().dsl().convert().from(Boolean.TRUE).to(Boolean.class)).isSameAs(Boolean.TRUE);
      });

    });

  }
}