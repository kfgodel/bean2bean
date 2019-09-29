package info.kfgodel.bean2bean.v4.impl.sets;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import info.kfgodel.bean2bean.v4.B2bTestContext;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 28/9/19 - 19:40
 */
@RunWith(JavaSpecRunner.class)
public class TypeBasedHierarchyTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a type based set", () -> {
      test().typeSet(() -> TypeBasedSet.create(ArrayList.class, String.class));

      describe("when asked for its supersets", () -> {
        test().supersets(() -> test().typeSet().getSuperSets().collect(Collectors.toList()));

        it("includes itself as the first element", () -> {
          assertThat(test().supersets()).startsWith(TypeBasedSet.create(ArrayList.class));
        });

        it("includes includes Object as the last superset",()->{
          assertThat(test().supersets()).endsWith(TypeBasedSet.create(Object.class));
        });

        it("includes all the superinterfaces and superclasses in the middle",()->{
          assertThat(test().supersets()).contains(
            TypeBasedSet.create(ArrayList.class),
            TypeBasedSet.create(RandomAccess.class),
            TypeBasedSet.create(Cloneable.class),
            TypeBasedSet.create(Serializable.class),
            TypeBasedSet.create(AbstractList.class),
            TypeBasedSet.create(List.class),
            TypeBasedSet.create(AbstractCollection.class),
            TypeBasedSet.create(Collection.class),
            TypeBasedSet.create(Iterable.class),
            TypeBasedSet.create(Object.class)
          );
        });

        // TODO: Implement the tests that requiere type argument inference in the hiearchy
        xdescribe("when type argument inference is implemented", () -> {

          it("includes itself as the first element", () -> {
            assertThat(test().supersets()).startsWith(TypeBasedSet.create(ArrayList.class, String.class));
          });

          it("includes all the superinterfaces and superclasses in the middle",()->{
            assertThat(test().supersets()).isEqualTo(Lists.newArrayList(
              TypeBasedSet.create(ArrayList.class, String.class),
              TypeBasedSet.create(ArrayList.class),
              TypeBasedSet.create(AbstractList.class, String.class),
              TypeBasedSet.create(AbstractList.class),
              TypeBasedSet.create(List.class, String.class),
              TypeBasedSet.create(List.class),
              TypeBasedSet.create(RandomAccess.class),
              TypeBasedSet.create(Cloneable.class),
              TypeBasedSet.create(Serializable.class),
              TypeBasedSet.create(AbstractCollection.class, String.class),
              TypeBasedSet.create(AbstractCollection.class),
              TypeBasedSet.create(Collection.class, String.class),
              TypeBasedSet.create(Collection.class),
              TypeBasedSet.create(Iterable.class, String.class),
              TypeBasedSet.create(Iterable.class),
              TypeBasedSet.create(Object.class)
            ));
          });
        });

      });

    });

  }
}