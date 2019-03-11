package info.kfgodel.bean2bean.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.references.FunctionRef;
import info.kfgodel.bean2bean.other.references.SupplierRef;
import info.kfgodel.bean2bean.other.references.TypeRef;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 28/02/19 - 19:48
 */
@RunWith(JavaSpecRunner.class)
public class Collection2CollectionConverterTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("a collection converter registered with b2b", () -> {
      beforeEach(() -> {
        test().dsl().configure().useConverter(Collection2CollectionConverter.create());
      });

      context().dsl(Dsl::create);

      describe("by itself", () -> {

        itThrows(CreationException.class, "if no creation converter is registered", () -> {
          test().dsl().convert().from(listWith12And2()).to(setOfStrings());
        }, e -> {
          assertThat(e).hasMessage("Creation of java.util.Set<java.lang.String> failed: No converter found from null ∈ {javax.lang.model.type.NullType} to {java.util.Set<java.lang.String>}");
        });

        itThrows(CreationException.class, "if the registered creation converter doesn't produce a collection", () -> {
          //Create a map for every creation
          test().dsl().configure().scopingWith((vector) -> true).useConverter((Supplier) HashMap::new);

          test().dsl().convert().from(listWith12And2()).to(setOfStrings());
        }, e -> {
          assertThat(e).hasMessage("Created instance[java.util.HashMap] can't be used as target collection");
        });

      });

      describe("when the correct creator converter is registered", () -> {
        beforeEach(() -> {
          test().dsl().configure().useConverter(new SupplierRef<Set>(HashSet::new) {
          });
        });

        itThrows(ConversionException.class, "if no element converter is registered", () -> {
          test().dsl().convert().from(listWith12And2()).to(setOfStrings());
        }, e -> {
          assertThat(e).hasMessage("No converter found from 1 ∈ {java.lang.Integer} to {java.lang.String}");
        });

        describe("when the correct element converter is registered", () -> {
          beforeEach(() -> {
            test().dsl().configure().useConverter(new FunctionRef<Integer, String>(String::valueOf) {
            });
          });

          it("converts the input collection into the expected output collection", () -> {
            Set<String> result = test().dsl().convert().from(listWith12And2()).to(setOfStrings());
            assertThat(result).isEqualTo(Sets.newHashSet("1", "2"));
          });

          itThrows(ConversionException.class, "if a different element converter is needed", () -> {
            test().dsl().convert().from(listWith12And2()).to(setOfNumber());
          }, e -> {
            assertThat(e).hasMessage("No converter found from 1 ∈ {java.lang.Integer} to {java.lang.Number}");
          });
        });

      });


    });

  }

  private TypeRef<Set<String>> setOfStrings() {
    return new TypeRef<Set<String>>() {
    };
  }
  private TypeRef<Set<Number>> setOfNumber() {
    return new TypeRef<Set<Number>>() {
    };
  }

  private List<Integer> listWith12And2() {
    return Lists.newArrayList(1, 2, 2);
  }
}