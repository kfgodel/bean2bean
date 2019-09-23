package info.kfgodel.bean2bean.v3.other;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import info.kfgodel.bean2bean.v3.other.references.TypeRef;
import info.kfgodel.bean2bean.v3.other.types.SupertypeSpliterator;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 17/02/19 - 21:22
 */
@RunWith(JavaSpecRunner.class)
public class SupertypeSpliteratorTest<E> extends JavaSpec<TypeRefTestContext> {
  @Override
  public void define() {
    describe("a supertype spliterator", () -> {
      test().spliterator(()-> SupertypeSpliterator.create(test().type()));

      describe("used to collect supertypes", () -> {
        test().supertypes(()-> StreamSupport.stream(test().spliterator(), false)
          .map(Type::getTypeName)
          .collect(Collectors.toList()));

        describe("given a concrete class", () -> {
          test().type(()-> String.class);

          it("allows access to its super type hierarchy with object as the last type",()->{
            assertThat(test().supertypes()).isEqualTo(Lists.newArrayList(
              "java.lang.String",
              "java.io.Serializable",
              "java.lang.Comparable<java.lang.String>",
              "java.lang.CharSequence",
              "java.lang.Comparable",
              "java.lang.Object"
            ));
          });
        });

        describe("given Object class", () -> {
          test().type(()-> Object.class);

          it("can only traverse Object",()->{
            assertThat(test().supertypes()).isEqualTo(Lists.newArrayList(
              "java.lang.Object"
            ));
          });
        });

        describe("given an interface class", () -> {
          test().type(()-> List.class);

          it("allows access to its super type hierarchy adding Object to the hierarchy",()->{
            assertThat(test().supertypes()).isEqualTo(Lists.newArrayList(
              "java.util.List",
              "java.util.Collection<E>",
              "java.util.Collection",
              "java.lang.Iterable<E>",
              "java.lang.Iterable",
              "java.lang.Object"
            ));
          });
        });

        describe("given a parameterized type", () -> {
          test().type(()-> new TypeRef<List<Integer>>(){}.getReference());

          it("allows access to its super type hierarchy including type arguments only for initial type",()->{
            assertThat(test().supertypes()).isEqualTo(Lists.newArrayList(
              "java.util.List<java.lang.Integer>",
              "java.util.List",
              "java.util.Collection<E>",
              "java.util.Collection",
              "java.lang.Iterable<E>",
              "java.lang.Iterable",
              "java.lang.Object"
            ));
          });
        });

        describe("given an unbound type variable", () -> {
          test().type(this::getUnboundTypeVariable);

          it("allows access to its super type hierarchy including type arguments only for initial type",()->{
            assertThat(test().supertypes()).isEqualTo(Lists.newArrayList(
              "E",
              "java.lang.Object"
            ));
          });
        });

        describe("given a multi bonded type variable", () -> {
          test().type(this::getMultiUpperBondedTypeVariable);

          it("allows access to its super type hierarchy including type arguments only for initial type",()->{
            assertThat(test().supertypes()).isEqualTo(Lists.newArrayList(
              "E",
              "java.lang.String",
              "java.lang.Iterable",
              "java.io.Serializable",
              "java.lang.Comparable<java.lang.String>",
              "java.lang.CharSequence",
              "java.lang.Comparable",
              "java.lang.Object"
            ));
          });
        });

        describe("given a parameterized generic array", () -> {
          test().type(this::getParameterizedArray);

          it("allows access to its super type hierarchy including type arguments only for initial type",()->{
            assertThat(test().supertypes()).isEqualTo(Lists.newArrayList(
              "java.util.List<java.lang.String>[]",
              "java.util.List[]",
              "java.lang.Cloneable",
              "java.io.Serializable",
              "java.lang.Object"
            ));
          });
        });

      });

    });
  }

  private <E> Type getUnboundTypeVariable() {
    return new TypeRef<E>() {}.getReference();
  }

  private <E extends String & Iterable> Type getMultiUpperBondedTypeVariable() {
    return new TypeRef<E>() {}.getReference();
  }

  private Type getParameterizedArray() {
    return new TypeRef<List<String>[]>() {}.getReference();
  }

}