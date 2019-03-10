package info.kfgodel.bean2bean.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.references.TypeRef;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class tests the uses cases for Optional conversions and converters
 * Date: 07/03/19 - 00:55
 */
@RunWith(JavaSpecRunner.class)
public class OptionalConvertersTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("optional converters registered to b2b", () -> {
      beforeEach(()->{
        test().dsl().configure().useConverter(Object2OptionalConverter.create());
        test().dsl().configure().useConverter(Optional2ObjectConverter.create());
      });
      test().dsl(Dsl::create);

      describe("when converting from an optional", () -> {

        it("returns null if its empty",()->{
          String result = test().dsl().convert().from(Optional.empty()).to(String.class);
          assertThat(result).isNull();
        });

        itThrows(ConversionException.class, "when there's no converter for the contained element to the target type",()->{
          test().dsl().convert().from(Optional.of("hola")).to(String.class);
        }, e->{
          assertThat(e).hasMessage("No converter found from hola{java.lang.String} to {java.lang.String}");
        });

        describe("when a converter for the contained element to the target type is registered", () -> {
          beforeEach(()->{
            test().dsl().configure().scopingWith(NoConversionConverter::shouldBeUsed).useConverter(NoConversionConverter.create());
          });

          it("returns the contained element if it's not empty",()->{
            String result = test().dsl().convert().from(Optional.of("hola")).to(String.class);
            assertThat(result).isEqualTo("hola");
          });

          it("allows using a supertype as target for a contained element",()->{
            Object result = test().dsl().convert().from(Optional.of("hola")).to(Object.class);
            assertThat(result).isEqualTo("hola");
          });

          itThrows(ConversionException.class, "if the target type doesn't match the contained type and there's no converter", ()->{
            test().dsl().convert().from(Optional.of("2")).to(Integer.class);
          }, e -> {
            assertThat(e).hasMessage("No converter found from 2{java.lang.String} to {java.lang.Integer}");
          });
        });

      });

      describe("when converting to an optional", () -> {

        itThrows(ConversionException.class, "when there's no converter for the contained type",()->{
          test().dsl().convert().from("chau").to(new TypeRef<Optional<String>>() {});
        }, e->{
          assertThat(e).hasMessage("No converter found from chau{java.lang.String} to {java.lang.String}");
        });

        describe("when a converter is registered for the contained type", () -> {
          beforeEach(()->{
            test().dsl().configure().scopingWith(NoConversionConverter::shouldBeUsed).useConverter(NoConversionConverter.create());
          });

          it("returns empty if null is used as source",()->{
            Optional<Object> result = test().dsl().convert().from(null).to(new TypeRef<Optional<Object>>() {});
            assertThat(result).isEmpty();
          });

          it("returns the optional element if source is not null",()->{
            Optional<String> result = test().dsl().convert().from("chau").to(new TypeRef<Optional<String>>() {});
            assertThat(result).contains("chau");
          });

          it("allows using a supertype as the contained type",()->{
            Optional<Object> result = test().dsl().convert().from("chau").to(new TypeRef<Optional<Object>>() {});
            assertThat(result).contains("chau");
          });

          it("uses Object as the contained type if non is specified",()->{
            Optional result = test().dsl().convert().from(12.0).to(Optional.class);
            assertThat(result).contains(12.0);
          });

          itThrows(ConversionException.class, "if the expected contained type doesn't match the source type and there's no converter", ()->{
            test().dsl().convert().from("3").to(new TypeRef<Optional<Integer>>() {});
          }, e -> {
            assertThat(e).hasMessage("No converter found from 3{java.lang.String} to {java.lang.Integer}");
          });
        });

      });
    });
  }
}