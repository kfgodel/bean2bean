package info.kfgodel.bean2bean.core.impl.registry;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.core.impl.conversion.ObjectConversion;
import info.kfgodel.bean2bean.core.impl.registry.definitions.DefaultDefinition;
import info.kfgodel.bean2bean.core.impl.registry.domains.DomainCalculator;
import info.kfgodel.bean2bean.dsl.api.B2bTestContext;
import info.kfgodel.bean2bean.other.TypeRef;
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
          Optional<Function<ObjectConversion, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
          assertThat(found).isEmpty();
        });
      });

      describe("when an exact converter is registered for the domain vector", () -> {
        beforeEach(() -> {
          test().registry().store(DefaultDefinition.create((in) -> "List<Integer> -> List<String>", listOfIntegersToListOfStrings()));
        });

        it("finds that converter", () -> {
          Optional<Function<ObjectConversion, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
          Object converterResult = found.get().apply(null);
          assertThat(converterResult).isEqualTo("List<Integer> -> List<String>");
        });

        it("finds no converter if a more generic domain conversion is attempted",()->{
          Optional<Function<ObjectConversion, Object>> found = test().registry().findBestConverterFor(listToList());
          assertThat(found).isEmpty();
        });
      });


      describe("when no exact converter is registered", () -> {

        describe("and other unrelated domain converter is registered", () -> {
          beforeEach(() -> {
            test().registry().store(DefaultDefinition.create((in) -> "List<Integer> -> List<Integer>", listOfIntegersToListOfIntegers()));
          });

          it("finds no converter", () -> {
            Optional<Function<ObjectConversion, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
            assertThat(found).isEmpty();
          });

        });

        describe("and a more generic domain converter is", () -> {
          beforeEach(() -> {
            test().registry().store(DefaultDefinition.create((in) -> "Object -> Object", objectToObject()));
          });

          it("finds that converter", () -> {
            Optional<Function<ObjectConversion, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("Object -> Object");
          });
        });

        describe("and two generic domain converters are registered with different level of specificity", () -> {
          beforeEach(() -> {
            test().registry().store(DefaultDefinition.create((in) -> "Object -> Object", objectToObject()));
            test().registry().store(DefaultDefinition.create((in) -> "List -> List", listToList()));
          });

          it("finds the more specific one", () -> {
            Optional<Function<ObjectConversion, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("List -> List");
          });
        });

        describe("and two generic domain converters are registered with equivalent level of specificity", () -> {
          beforeEach(() -> {
            test().registry().store(DefaultDefinition.create((in) -> "Object -> List", objectToList()));
            test().registry().store(DefaultDefinition.create((in) -> "List -> Object", listToObject()));
          });

          it("finds the one that's more specific to the expected output", () -> {
            Optional<Function<ObjectConversion, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("Object -> List");
          });
        });

        describe("even if there's a second converter closer to the input domain", () -> {
          beforeEach(() -> {
            test().registry().store(DefaultDefinition.create((in) -> "List -> List", listToList()));
            test().registry().store(DefaultDefinition.create((in) -> "Object -> List<String>", objectToListOfStrings()));
          });

          it("finds the one that's more specific to the expected output", () -> {
            Optional<Function<ObjectConversion, Object>> found = test().registry().findBestConverterFor(listOfIntegersToListOfStrings());
            Object converterResult = found.get().apply(null);
            assertThat(converterResult).isEqualTo("Object -> List<String>");
          });
        });

      });
    });
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