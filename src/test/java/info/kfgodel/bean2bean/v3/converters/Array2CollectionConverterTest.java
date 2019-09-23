package info.kfgodel.bean2bean.v3.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Sets;
import info.kfgodel.bean2bean.v3.converters.collections.Array2CollectionConverter;
import info.kfgodel.bean2bean.v3.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.v3.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.v3.core.api.exceptions.NestedConversionException;
import info.kfgodel.bean2bean.v3.dsl.impl.Dsl;
import info.kfgodel.bean2bean.v3.other.references.FunctionRef;
import info.kfgodel.bean2bean.v3.other.references.SupplierRef;
import info.kfgodel.bean2bean.v3.other.references.TypeRef;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 17/03/19 - 15:21
 */
@RunWith(JavaSpecRunner.class)
public class Array2CollectionConverterTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("an array 2 collection converter", () -> {
      beforeEach(()->{
        test().dsl().configure().scopingWith(Array2CollectionConverter::shouldBeUsed).useConverter(Array2CollectionConverter.create());
      });
      test().dsl(Dsl::create);

      describe("by itself", () -> {

        itThrows(NestedConversionException.class, "if no collection creator converter is registered", () -> {
          test().dsl().convert().from(arrayOfStrings1And2()).to(setOfIntegers());
        }, e -> {
          assertThat(e).hasMessage("Failed conversion from [1, 2] ∈ {java.lang.String[]} to {java.util.Set<java.lang.Integer>}\n" +
            "\tdue to: No converter found from nothing ∈ {info.kfgodel.bean2bean.v3.dsl.api.Nothing} to {java.util.Set<java.lang.Integer>}");
        });

        itThrows(CreationException.class, "if the registered creation converter doesn't produce a collection", () -> {
          //Create a map for every creation
          test().dsl().configure().scopingWith((vector) -> true).useConverter((Supplier) HashMap::new);

          test().dsl().convert().from(arrayOfStrings1And2()).to(setOfIntegers());
        }, e -> {
          assertThat(e).hasMessage("Created instance of type[class java.util.HashMap] can't be used as target collection");
        });

      });

      describe("when a collection creator is registered", () -> {
        beforeEach(() -> {
          test().dsl().configure().useConverter(new SupplierRef<Set>(HashSet::new) {});
        });

        itThrows(NestedConversionException.class, "if no element converter is registered", () -> {
          test().dsl().convert().from(arrayOfStrings1And2()).to(setOfIntegers());
        }, e -> {
          assertThat(e).hasMessage("Failed conversion from [1, 2] ∈ {java.lang.String[]} to {java.util.Set<java.lang.Integer>}\n" +
            "\tdue to: No converter found from \"1\" ∈ {java.lang.String} to {java.lang.Integer}");
        });

        describe("when an element converter is registered", () -> {
          beforeEach(() -> {
            test().dsl().configure().useConverter(new FunctionRef<String, Integer>(Integer::parseInt) {});
          });

          it("converts the input array into the expected output collection", () -> {
            Set<Integer> result = test().dsl().convert().from(arrayOfStrings1And2()).to(setOfIntegers());
            assertThat(result).isEqualTo(Sets.newHashSet(1, 2));
          });

          itThrows(NestedConversionException.class, "if a different element converter is needed", () -> {
            test().dsl().convert().from(arrayOfStrings1And2()).to(setOfStrings());
          }, e -> {
            assertThat(e).hasMessage("Failed conversion from [1, 2] ∈ {java.lang.String[]} to {java.util.Set<java.lang.String>}\n" +
              "\tdue to: No converter found from \"1\" ∈ {java.lang.String} to {java.lang.String}");
          });

          itThrows(ConversionException.class, "if source is not an array", ()->{
            test().dsl().convert().from("not an array").to(setOfStrings());
          }, e ->{
            assertThat(e).hasMessage("No converter found from \"not an array\" ∈ {java.lang.String} to {java.util.Set<java.lang.String>}");
          });
        });
      });


    });

  }

  private TypeRef<Set<Integer>> setOfIntegers() {
    return new TypeRef<Set<Integer>>() {};
  }
  private TypeRef<Set<String>> setOfStrings() {
    return new TypeRef<Set<String>>() {};
  }

  private String[] arrayOfStrings1And2() {
    return new String[]{"1","2"};
  }
}