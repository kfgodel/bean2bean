package info.kfgodel.reflect.types.binding;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.reflect.ReflectTestContext;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
      test().boundType(() -> DefaultBoundType.create(HashMap.class, String.class, Integer.class));

      it("has a raw type that is bound to its arguments", () -> {
        verifyBindings(test().boundType(), HashMap.class, String.class, Integer.class);
      });

      it("uses its type arguments to infer its super class and interfaces bound types", () -> {
        final List<BoundType> superInterfaceTypes = test().boundType().getSupertypes()
          .collect(Collectors.toList());
        // Map<K,V>, Cloneable, Serializable
        assertThat(superInterfaceTypes).hasSize(4);

        verifyBindings(superInterfaceTypes.get(0), AbstractMap.class, String.class, Integer.class);
        verifyBindings(superInterfaceTypes.get(1), Map.class, String.class, Integer.class);
        verifyBindings(superInterfaceTypes.get(2), Cloneable.class);
        verifyBindings(superInterfaceTypes.get(3), Serializable.class);
      });

    });

  }

  private void verifyBindings(BoundType boundType, Type expectedRaw, Type... expectedTypeArguments) {
    assertThat(boundType.getRawType()).isEqualTo(expectedRaw);
    assertThat(boundType.getTypeArguments()).containsExactly(expectedTypeArguments);
  }
}