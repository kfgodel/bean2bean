package info.kfgodel.bean2bean.v4.impl.sets;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.v3.other.references.TypeRef;
import info.kfgodel.bean2bean.v4.B2bTestContext;
import org.junit.runner.RunWith;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Queue;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Date: 27/9/19 - 00:09
 */
@RunWith(JavaSpecRunner.class)
public class TypeBasedSetTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a type based set", () -> {
      test().typeSet(() -> TypeBasedSet.create(List.class, String.class));

      it("is equal to other set for the same type and arguments", () -> {
        assertThat(test().typeSet()).isEqualTo(TypeBasedSet.create(List.class, String.class));
      });

      it("is different to a set created for a different type", () -> {
        assertThat(test().typeSet()).isNotEqualTo(TypeBasedSet.create(Queue.class, String.class));
      });

      it("is different to a set with same type but different arguments", () -> {
        assertThat(test().typeSet()).isNotEqualTo(TypeBasedSet.create(List.class, Integer.class));
      });

      it("has same hashcode than other equal type", () -> {
        final TypeBasedSet equivalentType = TypeBasedSet.create(List.class, String.class);
        assertThat(test().typeSet().hashCode()).isEqualTo(equivalentType.hashCode());
      });

      it("is equal to another natively parameterized type", () -> {
        assertThat(test().typeSet()).isEqualTo(TypeBasedSet.create(getReflectionParameterizedListOfStrings()));
      });

      describe("#toString", () -> {
        it("is equal to the type name including arguments", () -> {
          assertThat(test().typeSet().toString()).isEqualTo("java.util.List<java.lang.String>");
        });

        it("excludes the generics notation if the type is not parameterized", () -> {
          assertThat(TypeBasedSet.create(String.class).toString()).isEqualTo("java.lang.String");
        });

        it("includes brackets if the type is an array",()->{
          assertThat(TypeBasedSet.create(String[].class).toString()).isEqualTo("java.lang.String[]");
        });
      });

    });

  }

  private ParameterizedType getReflectionParameterizedListOfStrings() {
    final Type parameterizedType = new TypeRef<List<String>>() {
    }.getReference();
    return (ParameterizedType) parameterizedType;
  }
}