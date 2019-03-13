package info.kfgodel.bean2bean.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class verifies the options for enum converters
 * Date: 12/03/19 - 21:42
 */
@RunWith(JavaSpecRunner.class)
public class EnumConvertersTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("the converters for enum instances when registered to b2b", () -> {
      beforeEach(()->{
        test().dsl().configure().useConverter(Enum2StringConverter.create());
        test().dsl().configure().useConverter(String2EnumConverter.create());
      });
      test().dsl(Dsl::create);

      describe("when converting from an enum instance", () -> {
        it("returns its name",()->{
          String result = test().dsl().convert().from(TestEnum.FIRST_ENUM).to(String.class);
          assertThat(result).isEqualTo("FIRST_ENUM");
        });

        itThrows(ConversionException.class, "if the source is not an enum instance", ()->{
          test().dsl().convert().from("not an enum").to(String.class);
        }, e->{
          assertThat(e).hasMessage("No converter found from \"not an enum\" ∈ {java.lang.String} to {java.lang.String}");
        });
      });

      describe("when converting to an enum instance", () -> {
        it("returns the enum value whose name is equal to the source string",()->{
          TestEnum result = test().dsl().convert().from("FIRST_ENUM").to(TestEnum.class);
          assertThat(result).isEqualTo(TestEnum.FIRST_ENUM);
        });

        itThrows(ConversionException.class, "if the string value doesn't match an enum value name",()->{
          test().dsl().convert().from("not an enum name").to(TestEnum.class);
        }, e->{
          assertThat(e).hasMessage("No enum value named \"not an enum name\" found in class info.kfgodel.bean2bean.converters.EnumConvertersTest$TestEnum");
        });

        itThrows(ConversionException.class, "if the target class is not an enum sub-type", ()->{
          test().dsl().convert().from("FIRST_ENUM").to(Enum.class);
        }, e->{
          assertThat(e).hasMessage("Target type[{java.lang.Enum}] does not have enum values");
        });
      });
    });
  }

  public static enum TestEnum {
    FIRST_ENUM,
    SECOND_ENUM;
  }
}