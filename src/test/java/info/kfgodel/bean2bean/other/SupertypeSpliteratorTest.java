package info.kfgodel.bean2bean.other;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
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

      describe("given a class", () -> {
        test().type(()-> List.class);

        it("allows access to its super type hierarchy",()->{
          List<String> superTypes = StreamSupport.stream(test().spliterator(), false)
            .map(Type::getTypeName)
            .collect(Collectors.toList());
          assertThat(superTypes).isEqualTo(Lists.newArrayList(
            "java.util.List",
            "java.util.Collection<E>",
            "java.util.Collection",
            "java.lang.Iterable<E>",
            "java.lang.Iterable"
          ));
        });
      });

      describe("given a parameterized type", () -> {
        test().type(()-> new TypeRef<List<Integer>>(){}.getReference());

        it("allows access to its super type hierarchy excluding type parameters except for root type",()->{
          List<String> superTypes = StreamSupport.stream(test().spliterator(), false)
            .map(Type::getTypeName)
            .collect(Collectors.toList());
          assertThat(superTypes).isEqualTo(Lists.newArrayList(
            "java.util.List<java.lang.Integer>",
            "java.util.List",
            "java.util.Collection<E>",
            "java.util.Collection",
            "java.lang.Iterable<E>",
            "java.lang.Iterable"
          ));
        });
      });


    });

  }
}