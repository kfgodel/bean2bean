package info.kfgodel.bean2bean.other;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies the implementation for {@link TypeRef}
 * Date: 12/02/19 - 20:11
 */
@RunWith(JavaSpecRunner.class)
public class TypeRefTest extends JavaSpec<TypeRefTestContext> {
  @Override
  public void define() {
    describe("a type reference", () -> {
      test().typeRef(() -> new TypeRef<String>() {});

      it("retains the actual type argument used to parameterize the subclass", () -> {
        Type referencedType = test().typeRef().getReference();
        assertThat(referencedType).isEqualTo(String.class);
      });

      it("can be instantiated only through subclasses", () -> {
        Class instantiableClass = test().typeRef().getClass();
        assertThat(instantiableClass.getSuperclass()).isEqualTo(TypeRef.class);
      });

      itThrows(IllegalStateException.class, "when accessing the reference and extended without a type argument",()->{
        new TypeRef(){}.getReference();
      }, e->{
        assertThat(e).hasMessage("TypeRef should be parameterized when extended");
      });

      describe("when a nested parameterization is used as type argument", () -> {
        test().typeRef(() -> new TypeRef<List<Integer>>() {});

        it("retains the complete parameterized type argument", () -> {
          ParameterizedType referencedType = (ParameterizedType) test().typeRef().getReference();
          assertThat(referencedType.getRawType()).isEqualTo(List.class);
          assertThat(referencedType.getActualTypeArguments()[0]).isEqualTo(Integer.class);
        });
      });

      describe("when more than one level of inheritance is used", () -> {
        test().typeRef(() -> new OtherSubTypeRef<String>() {});

        it("only retains as reference the type used to parameterize TypeRef, not its subclasses",()->{
          assertThat(test().typeRef().getReference()).isInstanceOf(TypeVariable.class);
        });
      });
    });

  }
}