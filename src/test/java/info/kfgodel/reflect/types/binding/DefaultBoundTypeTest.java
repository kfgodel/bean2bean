package info.kfgodel.reflect.types.binding;

import info.kfgodel.jspek.api.JavaSpec;
import info.kfgodel.jspek.api.JavaSpecRunner;
import info.kfgodel.reflect.ReflectTestContext;
import info.kfgodel.reflect.types.binding.helpers.Level0;
import info.kfgodel.reflect.types.binding.helpers.Level1;
import info.kfgodel.reflect.types.binding.helpers.Level2;
import info.kfgodel.reflect.types.binding.helpers.Level3;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Date: 29/9/19 - 15:17
 */
@RunWith(JavaSpecRunner.class)
public class DefaultBoundTypeTest extends JavaSpec<ReflectTestContext> {
  @Override
  public void define() {
    describe("a bound type", () -> {
      test().boundType(() -> DefaultBoundType.create(ArrayList.class, String.class));

      it("has a raw type that is bound to its arguments", () -> {
        verifyBindings(test().boundType(), ArrayList.class, String.class);
      });

      it("uses its type arguments to infer its super class and interfaces bound types", () -> {
        final List<BoundType> directSupertypes = test().boundType().getDirectSupertypes()
          .collect(Collectors.toList());

        assertThat(directSupertypes).hasSize(5);

        verifyBindings(directSupertypes.get(0), AbstractList.class, String.class);
        verifyBindings(directSupertypes.get(1), List.class, String.class);
        verifyBindings(directSupertypes.get(2), RandomAccess.class);
        verifyBindings(directSupertypes.get(3), Cloneable.class);
        verifyBindings(directSupertypes.get(4), Serializable.class);
      });

      it("allows access to all its bound type hierarchy",()->{
        final List<BoundType> allSupertypes = test().boundType().getUpwardHierarchy()
          .collect(Collectors.toList());

        assertThat(allSupertypes).hasSize(10);

        verifyBindings(allSupertypes.get(0), ArrayList.class, String.class);
        verifyBindings(allSupertypes.get(1), AbstractList.class, String.class);
        verifyBindings(allSupertypes.get(2), List.class, String.class);
        verifyBindings(allSupertypes.get(3), RandomAccess.class);
        verifyBindings(allSupertypes.get(4), Cloneable.class);
        verifyBindings(allSupertypes.get(5), Serializable.class);
        verifyBindings(allSupertypes.get(6), AbstractCollection.class, String.class);
        verifyBindings(allSupertypes.get(7), Collection.class, String.class);
        verifyBindings(allSupertypes.get(8), Iterable.class, String.class);
        verifyBindings(allSupertypes.get(9), Object.class);
      });

      it("can infer complex type variable replacements",()->{
        final List<BoundType> hierarchy = DefaultBoundType.create(Level0.class, String.class, Long.class, Character.class)
          .getUpwardHierarchy()
          .collect(Collectors.toList());

        assertThat(hierarchy).hasSize(8);

        verifyBindings(hierarchy.get(0), Level0.class, String.class, Long.class, Character.class);
        verifyBindings(hierarchy.get(1), Level1.class, Long.class, Character.class, String.class);
        verifyBindings(hierarchy.get(2), List.class, Character.class);
        verifyBindings(hierarchy.get(3), Level2.class, String.class, Long.class, String.class);
        verifyBindings(hierarchy.get(4), Collection.class, Character.class);
        verifyBindings(hierarchy.get(5), Level3.class, Long.class, String.class);
        verifyBindings(hierarchy.get(6), Iterable.class, Character.class);
        verifyBindings(hierarchy.get(7), Object.class);
      });

    });

  }

  private void verifyBindings(BoundType boundType, Type expectedRaw, Type... expectedTypeArguments) {
    assertThat(boundType.getRawType()).isEqualTo(expectedRaw);
    assertThat(boundType.getTypeArguments()).containsExactly(expectedTypeArguments);
  }
}