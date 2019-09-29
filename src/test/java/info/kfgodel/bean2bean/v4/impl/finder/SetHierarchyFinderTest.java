package info.kfgodel.bean2bean.v4.impl.finder;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.nary.api.optionals.Optional;
import info.kfgodel.bean2bean.v4.B2bTestContext;
import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.intent.Intent;
import info.kfgodel.bean2bean.v4.impl.sets.Set;
import info.kfgodel.bean2bean.v4.impl.sets.TypeBasedSet;
import info.kfgodel.bean2bean.v4.impl.store.DefaultStore;
import info.kfgodel.bean2bean.v4.impl.vector.Vector;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static info.kfgodel.bean2bean.mockito.MockitoExtensions.anIrrelevant;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 28/9/19 - 20:55
 */
@RunWith(JavaSpecRunner.class)
public class SetHierarchyFinderTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a set hierarchy finder", () -> {
      test().finder(()-> SetHierarchyFinder.create(test().store()));

      describe("given a converter store", () -> {
        test().store(DefaultStore::create);

        describe("when looking for the best converter for a set based intent", () -> {
          test().intent(()-> Intent.create(Vector.create(TypeBasedSet.create(List.class, String.class),TypeBasedSet.create(Double.class))));

          describe("when the store is empty", () -> {
            it("finds no converter",()->{
              Optional found = test().finder().findBestConverterFor(test().intent());
              assertThat(found).isEmpty();
            });
          });

          describe("when the store has a converter that matches exactly the intent's vector", () -> {
            beforeEach(()->{
              test().store().useFor(Vector.create(TypeBasedSet.create(List.class, String.class),TypeBasedSet.create(Double.class)), anIrrelevant(Function.class));
            });

            it("finds the converter",()->{
              Optional found = test().finder().findBestConverterFor(test().intent());
              assertThat(found).isNotEmpty();
            });

            it("finds no converter if the intent's vector has no relation with the set hierarchy of the converter",()->{
              ConversionIntent otherIntent = Intent.create(Vector.create(TypeBasedSet.create(Set.class, String.class),TypeBasedSet.create(Double.class)));
              Optional found = test().finder().findBestConverterFor(otherIntent);
              assertThat(found).isEmpty();
            });
          });

          describe("when the store has a converter that matches a super set of the intent's vector", () -> {
            beforeEach(()->{
              test().store().useFor(Vector.create(TypeBasedSet.create(Collection.class),TypeBasedSet.create(Number.class)), anIrrelevant(Function.class));
            });

            it("finds the converter",()->{
              Optional found = test().finder().findBestConverterFor(test().intent());
              assertThat(found).isNotEmpty();
            });
          });

        });

        describe("when looking for the best converter for an intent that is NOT set based", () -> {
          test().intent(()-> Intent.create(Vector.create(1,2)));

          describe("even if there's a matching converter on the store", () -> {
            beforeEach(()->{
              test().store().useFor(Vector.create(1,2), anIrrelevant(Function.class));
            });

            it("finds no converter",()->{
              Optional found = test().finder().findBestConverterFor(test().intent());
              assertThat(found).isEmpty();
            });
          });

        });

      });

    });
  }
}