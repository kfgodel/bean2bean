package info.kfgodel.bean2bean.v3.other;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import info.kfgodel.bean2bean.v3.other.types.extraction.TypeArgumentExtractor;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
        Stream<Type> arguments = test().argumentExtractor().getArgumentsUsedFor(Map.class, MapOfIntegersToString.class);
        assertThat(arguments.collect(Collectors.toList()))
          .isEqualTo(Lists.newArrayList(Integer.class, String.class));
      });

      it("returns empty if the concrete class doesn't have type arguments for the supertype",()->{
        Stream<Type> arguments = test().argumentExtractor().getArgumentsUsedFor(Map.class, MissingArgumentsMap.class);
        assertThat(arguments.count()).isEqualTo(0);
      });

      it("offers a facility method when there's only 1 argument",()->{
        Optional<Type> argument = test().argumentExtractor().getArgumentUsedFor(Set.class, SetOfStrings.class);
        assertThat(argument.get()).isEqualTo(String.class);
      });

      describe("when the argument is not directly defined", () -> {
        
        it("can deduce the indirectly defined type arguments",()->{
          Stream<Type> arguments = test().argumentExtractor().getArgumentsUsedFor(Map.class, IndirectlyParameterizedMap.class);
          assertThat(arguments.collect(Collectors.toList()))
            .isEqualTo(Lists.newArrayList(Integer.class, String.class));
        });

        it("can deduce the arguments with more than one level of indirection",()->{
          Optional<Type> argument = test().argumentExtractor().getArgumentUsedFor(Iterable.class, SetOfStrings.class);
          assertThat(argument.get()).isEqualTo(String.class);
        });

        it("will deduce type variable replacements over the hierarchy",()->{
          Stream<Type> arguments = test().argumentExtractor().getArgumentsUsedFor(Map.class, SubClassOfMapOfIntegersToVariable.class);
          List<Type> typeArguments = arguments.collect(Collectors.toList());
          assertThat(typeArguments.get(0)).isEqualTo(Integer.class);
          assertThat(typeArguments.get(1).getTypeName()).isEqualTo("Replaced");
        });

        it("can deduce actual arguments even when more than one definition is done in the hierarchy",()->{
          Stream<Type> arguments = test().argumentExtractor().getArgumentsUsedFor(Map.class, MultiArgumentDefinition.class);
          assertThat(arguments.collect(Collectors.toList()))
            .isEqualTo(Lists.newArrayList(Integer.class, String.class));
        });

        it("deduces the actual argument, even if it's a variable",()->{
          Stream<Type> arguments = test().argumentExtractor().getArgumentsUsedFor(Map.class, getVariableArgType());
          List<Type> typeArguments = arguments.collect(Collectors.toList());
          assertThat(typeArguments.get(0)).isEqualTo(Integer.class);
          assertThat(typeArguments.get(1).getTypeName()).isEqualTo("DefinedInMethod");
        });

      });



    });

  }

  public abstract static class MapOfIntegersToString implements Map<Integer, String> {}
  public abstract static class MissingArgumentsMap implements Map{}
  public abstract static class SetOfStrings implements Set<String> {}
  public abstract static class IndirectlyParameterizedMap extends AbstractMap<Integer, String> {}

  public abstract static class SubClassOfMapOfIntegersToVariable<Replaced> extends AbstractMap<Integer, Replaced> {}

  public interface MapOfVariableKeyToStrings<K> extends Map<K, String> {}
  public interface MapOfIntegerToVariableValue<V> extends Map<Integer, V> {}
  public abstract static class MultiArgumentDefinition
    implements MapOfVariableKeyToStrings<Integer>, MapOfIntegerToVariableValue<String>  {}


  public <DefinedInMethod> Type getVariableArgType(){
    abstract class MapOfIntegersToE extends AbstractMap<Integer, DefinedInMethod>{};
    return MapOfIntegersToE.class;
  }


}