package info.kfgodel.bean2bean.v3.core.impl.registry;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.core.api.registry.Domain;
import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.v3.core.impl.registry.definitions.PredicateDefinition;
import info.kfgodel.bean2bean.v3.core.impl.registry.definitions.VectorDefinition;
import info.kfgodel.bean2bean.v3.core.impl.registry.domains.DomainCalculator;
import info.kfgodel.bean2bean.v3.dsl.api.B2bTestContext;
import info.kfgodel.reflect.references.TypeRef;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 18/02/19 - 20:02
 */
@RunWith(JavaSpecRunner.class)
public class DefaultRegistryTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a b2b registry", () -> {
      test().registry(DefaultRegistry::create);

      describe("when created", () -> {
        it("has no registered converter", () -> {
          Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
          assertThat(found).isEmpty();
        });
      });

      describe("when an exact converter is registered for the domain vector", () -> {
        beforeEach(() -> {
          test().registry().store(VectorDefinition.create((in) -> "List<Integer> -> List<String>", listOfIntegersToListOfStrings()));
        });

        it("finds that converter", () -> {
          Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
          Object converterResult = found.get().apply(null);
          assertThat(converterResult).isEqualTo("List<Integer> -> List<String>");
        });

        it("finds no converter if a more generic domain conversion is attempted",()->{
          Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(listToList());
          assertThat(found).isEmpty();
        });

        describe("even if a predicate based converter matches too", () -> {
          beforeEach(()->{
            test().registry().store(PredicateDefinition.create((in) -> "Predicate based", (vector)-> true));
          });

          it("finds the exact converter",()->{
            Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("List<Integer> -> List<String>");
          });
        });

      });

      describe("when a predicate based converter is registered", () -> {
        beforeEach(()->{
          test().registry().store(PredicateDefinition.create((in) -> "input == output", this::inputIsSameAsOutput));
        });

        it("finds the converter when the conversion vector matches the predicate",()->{
          Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(listToList());
          Object converterResult = found.get().apply(null);
          assertThat(converterResult).isEqualTo("input == output");
        });

        it("finds no converter if the conversion vector doesn't match the predicate",()->{
          Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(objectToList());
          assertThat(found).isEmpty();
        });

        describe("and a second predicate based converter is added", () -> {
          beforeEach(()->{
            test().registry().store(PredicateDefinition.create((in) -> "anything", (vector)-> true));
          });

          it("finds the first converter that matches the predicate",()->{
            Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(listToList());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("input == output");
          });

          it("finds the second if the vector doesn't match the first predicate",()->{
            Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(objectToList());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("anything");
          });
        });

        describe("and a second vector based definition is registered", () -> {
          beforeEach(()->{
            test().registry().store(VectorDefinition.create((in) -> "List -> List", listToList()));
          });

          it("finds a matching vector based converter over any predicate based",()->{
            Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(listToList());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("List -> List");
          });

          it("finds the predicate based converter if no vector based is applicable",()->{
            Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(objectToObject());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("input == output");
          });
        });
      });

      describe("when no exact converter is registered", () -> {

        describe("and other unrelated domain converter is registered", () -> {
          beforeEach(() -> {
            test().registry().store(VectorDefinition.create((in) -> "List<Integer> -> List<Integer>", listOfIntegersToListOfIntegers()));
          });

          it("finds no converter", () -> {
            Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
            assertThat(found).isEmpty();
          });

        });

        describe("and a more generic domain converter is", () -> {
          beforeEach(() -> {
            test().registry().store(VectorDefinition.create((in) -> "Object -> Object", objectToObject()));
          });

          it("finds that converter", () -> {
            Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("Object -> Object");
          });
        });

        describe("and two generic domain converters are registered with different level of specificity", () -> {
          beforeEach(() -> {
            test().registry().store(VectorDefinition.create((in) -> "Object -> Object", objectToObject()));
            test().registry().store(VectorDefinition.create((in) -> "List -> List", listToList()));
          });

          it("finds the more specific one", () -> {
            Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("List -> List");
          });
        });

        describe("and two generic domain converters are registered with equivalent level of specificity", () -> {
          beforeEach(() -> {
            test().registry().store(VectorDefinition.create((in) -> "Object -> List", objectToList()));
            test().registry().store(VectorDefinition.create((in) -> "List -> Object", listToObject()));
          });

          it("finds the one that's more specific to the expected output", () -> {
            Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("Object -> List");
          });
        });

        describe("even if there's a second converter closer to the input domain", () -> {
          beforeEach(() -> {
            test().registry().store(VectorDefinition.create((in) -> "List -> List", listToList()));
            test().registry().store(VectorDefinition.create((in) -> "Object -> List<String>", objectToListOfStrings()));
          });

          // This is based on the assumption that input can be loosened but output should be strict
          it("finds the one that's more specific to the expected output", () -> {
            Optional<Function<Bean2beanTask, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("Object -> List<String>");
          });
        });

      });
    });
  }

  private boolean inputIsSameAsOutput(DomainVector domainVector) {
    return domainVector.getSource().equals(domainVector.getTarget());
  }

  private DomainVector objectToListOfStrings() {
    return DomainVector.create(objectDomain(), listOfStringsDomain());
  }

  private DomainVector listToObject() {
    return DomainVector.create(listDomain(), objectDomain());
  }

  private DomainVector objectToList() {
    return DomainVector.create(objectDomain(), listDomain());
  }

  private DomainVector listToList() {
    return DomainVector.create(listDomain(), listDomain());
  }

  private DomainVector objectToObject() {
    return DomainVector.create(objectDomain(), objectDomain());
  }

  private DomainVector listOfIntegersToListOfStrings() {
    return DomainVector.create(listOfIntegersDomain(), listOfStringsDomain());
  }

  private DomainVector listOfIntegersToListOfIntegers() {
    return DomainVector.create(listOfIntegersDomain(), listOfIntegersDomain());
  }

  private Domain objectDomain(){
    return domainForType(Object.class);
  }

  private Domain listDomain(){
    return domainForType(List.class);
  }

  private Domain listOfIntegersDomain(){
    return domainForType(new TypeRef<List<Integer>>(){}.getReference());
  }

  private Domain listOfStringsDomain(){
    return domainForType(new TypeRef<List<String>>(){}.getReference());
  }

  private Domain domainForType(Type aType){
    return DomainCalculator.create().forType(aType);
  }

}