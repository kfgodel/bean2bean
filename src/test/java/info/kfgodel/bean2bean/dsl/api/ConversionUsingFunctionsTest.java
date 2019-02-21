package info.kfgodel.bean2bean.dsl.api;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.dsl.api.converters.ArrayListToListOfStringsConverter;
import info.kfgodel.bean2bean.dsl.api.converters.ArrayListToListOfStringsNestedConverter;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.references.FunctionRef;
import info.kfgodel.bean2bean.other.references.SupplierRef;
import info.kfgodel.bean2bean.other.references.TypeRef;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 10/02/19 - 22:58
 */
@RunWith(JavaSpecRunner.class)
public class ConversionUsingFunctionsTest extends JavaSpec<B2bTestContext> {

  @Override
  public void define() {
    describe("a b2b dsl", () -> {
      test().dsl(Dsl::create);

      describe("with a default configuration", () -> {

        itThrows(ConversionException.class, "when any conversion is attempted", () -> {
          test().dsl().convert().from("1").to(Integer.class);
        }, e -> {
          assertThat(e).hasMessage("No converter found from 1{java.lang.String} to {java.lang.Integer}");
        });
      });

      describe("when a converter is configured from a function", () -> {
        beforeEach(() -> {
          // Function ref is needed to avoid losing function type arguments through erasure
          test().dsl().configure().usingConverter(new FunctionRef<String, Integer>(Integer::parseInt) {});
        });

        it("can convert the input value if it matches the functions input's type", () -> {
          Integer result = test().dsl().convert().from("1").to(Integer.class);
          assertThat(result).isEqualTo(1);
        });

        itThrows(ConversionException.class, "if the input value types doesn't match the function", () -> {
          test().dsl().convert().from(1.0).to(Integer.class);
        }, e -> {
          assertThat(e).hasMessage("No converter found from 1.0{java.lang.Double} to {java.lang.Integer}");
        });
      });

      describe("when the converter function declares parameterized types", () -> {
        beforeEach(() -> {
          // Function ref is needed to avoid losing function type arguments through erasure
          test().dsl().configure().usingConverter(ArrayListToListOfStringsConverter.create());
        });

        it("can do the conversion if exact parameterized types are expected", () -> {
          List<String> result = test().dsl().convert().from(Lists.newArrayList(1)).to(new TypeRef<List<String>>() {});
          assertThat(result).isEqualTo(Lists.newArrayList("1"));
        });

        itThrows(ConversionException.class, "if un generified types are expected", () -> {
          test().dsl().convert().from(Lists.newArrayList(1)).to(List.class);
        }, e -> {
          assertThat(e).hasMessage("No converter found from [1]{java.util.ArrayList} to {java.util.List}");
        });
      });

      describe("when the converter function needs internal access to b2b for delegating part of the conversion", () -> {
        beforeEach(() -> {
          test().dsl().configure().usingConverter(ArrayListToListOfStringsNestedConverter.create());
        });

        describe("and b2b doesn't have a converter for the part conversion", () -> {

          itThrows(ConversionException.class, "when the conversion is attempted", () -> {
            test().dsl().convert().from(Lists.newArrayList(8)).to(new TypeRef<List<String>>() {});
          }, e -> {
            assertThat(e).hasMessage("No converter found from 8{java.lang.Integer} to {java.lang.String}");
          });
        });

        describe("and b2b has a converter defined for the part conversion", () -> {
          beforeEach(()->{
            test().dsl().configure().usingConverter(new FunctionRef<Integer, String>(String::valueOf) {});
          });

          it("nests conversions sucessfully",()->{
            List<String> result = test().dsl().convert().from(Lists.newArrayList(8)).to(new TypeRef<List<String>>() {});
            assertThat(result).isEqualTo(Lists.newArrayList("8"));
          });
        });
      });

      describe("when no input is needed for the converter", () -> {
        beforeEach(()->{
          test().dsl().configure().usingConverter(new SupplierRef<List>(ArrayList::new){});
        });

        it("allows instance creation",()->{
          List result = test().dsl().convert().from(null).to(List.class);
          assertThat(result)
            .isNotNull()
            .isEmpty();
        });
      });


    });
  }


}