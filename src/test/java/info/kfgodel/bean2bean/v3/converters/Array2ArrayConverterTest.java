package info.kfgodel.bean2bean.v3.converters;

import info.kfgodel.bean2bean.v3.converters.collections.Array2ArrayConverter;
import info.kfgodel.bean2bean.v3.converters.generators.ArrayInstantiator;
import info.kfgodel.bean2bean.v3.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.v3.core.api.exceptions.NestedConversionException;
import info.kfgodel.bean2bean.v3.dsl.impl.Dsl;
import info.kfgodel.bean2bean.v3.other.references.FunctionRef;
import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 03/03/19 - 19:44
 */
@RunWith(JavaSpecRunner.class)
public class Array2ArrayConverterTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("an array 2 array converter on b2b", () -> {
      test().dsl(Dsl::create);

      describe("when registered using the predicate", () -> {
        beforeEach(() -> {
          test().dsl().configure().scopingWith(Array2ArrayConverter::shouldBeUsed).useConverter(Array2ArrayConverter.create());
        });

        describe("given a registered array instantiator", () -> {
          beforeEach(() -> {
            test().dsl().configure().scopingWith(ArrayInstantiator::shouldBeUsed).useConverter(ArrayInstantiator.create());
          });

          describe("given a registered element converter", () -> {
            beforeEach(()->{
              test().dsl().configure().useConverter(new FunctionRef<Integer, String>(String::valueOf) {});
            });

            it("creates a new array",()->{
              String[] result = test().dsl().convert().from(new Integer[]{1, 2}).to(String[].class);
              assertThat(result).isEqualTo(new String[]{"1","2"});
            });

          });


          itThrows(NestedConversionException.class, "if no element converter is registered",()->{
            test().dsl().convert().from(new Integer[]{1,2}).to(String[].class);
          }, e->{
            assertThat(e).hasMessage("Failed conversion from [1, 2] ∈ {java.lang.Integer[]} to {java.lang.String[]}\n" +
              "\tdue to: No converter found from 1 ∈ {java.lang.Integer} to {java.lang.String}");
          });

        });

        itThrows(NestedConversionException.class, "if no instantiator handles the array creation",()->{
          test().dsl().convert().from(new Integer[]{1,2}).to(String[].class);
        }, e->{
          assertThat(e).hasMessage("Failed conversion from [1, 2] ∈ {java.lang.Integer[]} to {java.lang.String[]}\n" +
            "\tdue to: No converter found from 2 ∈ {java.lang.Integer} to {java.lang.String[]}");
        });


      });

      describe("when registered without a predicate", () -> {
        beforeEach(() -> {
          test().dsl().configure().useConverter(Array2ArrayConverter.create());
        });

        itThrows(ConversionException.class, "if input is null", ()->{
          test().dsl().convert().from(null).to(String[].class);
        }, e ->{
          assertThat(e).hasMessage("Source is not an array: null");
        });

        itThrows(ConversionException.class, "if input is not an array", ()->{
          test().dsl().convert().from(new Object()).to(String[].class);
        }, e ->{
          assertThat(e.getMessage())
            .startsWith("Source is not an array: java.lang.Object")
          .endsWith("{java.lang.Object}");
        });

        itThrows(NestedConversionException.class, "if no instantiator handles the array creation",()->{
          test().dsl().convert().from(new Integer[]{1,2}).to(Object.class);
        }, e->{
          assertThat(e).hasMessage("Failed conversion from [1, 2] ∈ {java.lang.Integer[]} to {java.lang.Object}\n" +
            "\tdue to: Source is not an array: 2 ∈ {java.lang.Integer}");
        });

        describe("when a specific array instantiator is registered", () -> {
          beforeEach(()->{
            // Force the instantiator to the specific conversion
            test().dsl().configure().scopingTo().accept(Integer.class).andProduce(String[].class)
              .useConverter((BiFunction) ArrayInstantiator.create());
          });

          itThrows(NestedConversionException.class, "if target type is not an array",()->{
            test().dsl().configure().useConverter(ArrayInstantiator.create());
            test().dsl().convert().from(new Integer[]{1,2}).to(Object.class);
          }, e->{
            assertThat(e).hasMessage("Failed conversion from [1, 2] ∈ {java.lang.Integer[]} to {java.lang.Object}\n" +
              "\tdue to: Can't instantiate array for non array type: class java.lang.Object");
          });

          itThrows(NestedConversionException.class, "if no element converter is registered because it calls itself",()->{
            test().dsl().convert().from(new Integer[]{1,2}).to(String[].class);
          }, e->{
            assertThat(e).hasMessage("Failed conversion from [1, 2] ∈ {java.lang.Integer[]} to {java.lang.String[]}\n" +
              "\tdue to: Source is not an array: 1 ∈ {java.lang.Integer}");
          });


          describe("when an element converter is registered", () -> {
            beforeEach(()->{
              test().dsl().configure().useConverter(new FunctionRef<Integer, String>(String::valueOf) {});
            });
            it("creates a new array",()->{
              String[] result = test().dsl().convert().from(new Integer[]{1, 2}).to(String[].class);
              assertThat(result).isEqualTo(new String[]{"1","2"});
            });
          });

        });

      });

    });
  }
}