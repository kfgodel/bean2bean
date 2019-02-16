package info.kfgodel.bean2bean.other;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import org.junit.runner.RunWith;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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

      it("can be instantiated only through subclasses", () -> {
        Class instantiableClass = test().typeRef().getClass();
        assertThat(instantiableClass.getSuperclass()).isEqualTo(TypeRef.class);
      });

      it("retains the actual type argument used to parameterize the subclass", () -> {
        Type referencedType = test().typeRef().getReference();
        assertThat(referencedType).isEqualTo(String.class);
      });

      describe("when a nested parameterization is used as type argument", () -> {
        test().typeRef(() -> new TypeRef<List<Integer>>() {});

        it("retains the complete parameterized type argument", () -> {
          ParameterizedType referencedType = (ParameterizedType) test().typeRef().getReference();
          assertThat(referencedType.getRawType()).isEqualTo(List.class);
          assertThat(referencedType.getActualTypeArguments()[0]).isEqualTo(Integer.class);
        });
      });

      itThrows(IllegalStateException.class, "if more than one level of inheritance is used", () -> {
        new OtherSubTypeRef<String>() {}.getReference();
      }, e -> {
        assertThat(e).hasMessage("TypeRef should have only one level subclass. class info.kfgodel.bean2bean.other.OtherSubTypeRef is in the middle of the inheritance");
      });
    });

  }
}