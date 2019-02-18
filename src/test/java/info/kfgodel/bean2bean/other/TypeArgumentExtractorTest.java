package info.kfgodel.bean2bean.other;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import info.kfgodel.bean2bean.dsl.api.converters.StringArrayGenerator;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class verifies that the extractor works well with different types
 * Date: 17/02/19 - 23:23
 */
@RunWith(JavaSpecRunner.class)
public class TypeArgumentExtractorTest extends JavaSpec<TypeRefTestContext> {
  @Override
  public void define() {
    describe("a type argument extractor", () -> {
      test().argumentExtractor(TypeArgumentExtractor::create);

      it("can extract the actual type arguments used to parameterize the supertype of a class", () -> {
        Stream<Type> arguments = test().argumentExtractor().getArgumentsUsedFor(Function.class, StringArrayGenerator.class);
        assertThat(arguments.collect(Collectors.toList()))
          .isEqualTo(Lists.newArrayList(Integer.class, String[].class));
      });

      it("returns empty if the concrete class doesn't have type arguments for the supertype",()->{
        Stream<Type> arguments = test().argumentExtractor().getArgumentsUsedFor(Function.class, NoArgFunction.class);
        assertThat(arguments.count()).isEqualTo(0);
      });

      it("offers a facility method when there's only 1 argument",()->{
        Optional<Type> argument = test().argumentExtractor().getArgumentUsedFor(Collection.class, List.class);
        TypeVariable actual = (TypeVariable) argument.get();
        assertThat(actual.getName()).isEqualTo("E");
      });


    });

  }
}