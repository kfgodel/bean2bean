package info.kfgodel.bean2bean.v4.impl.finder;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.nary.api.optionals.Optional;
import info.kfgodel.bean2bean.v4.B2bTestContext;
import info.kfgodel.bean2bean.v4.impl.intent.ConversionIntent;
import info.kfgodel.bean2bean.v4.impl.process.ConversionProcess;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.runner.RunWith;

import java.util.function.Function;

import static info.kfgodel.bean2bean.mockito.MockitoExtensions.anIrrelevant;
import static info.kfgodel.bean2bean.mockito.MockitoExtensions.mockear;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 23/9/19 - 23:31
 */
@RunWith(JavaSpecRunner.class)
public class SequentialFinderTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a sequential finder", () -> {
      test().finder(()-> SequentialFinder.create(test().strategies()));

      describe("when no strategies are defined", () -> {
        test().strategies(Lists::emptyList);

        it("returns empty",()->{
          Optional converter = test().finder().findBestConverterFor(anIrrelevant(ConversionIntent.class));
          assertThat(converter).isEmpty();
        });
      });

      describe("when a successful strategy is defined", () -> {
        test().strategies(()-> Lists.newArrayList(this::successfulStrategy));

        it("finds a converter",()->{
          Optional foundConverter = test().finder().findBestConverterFor(anIrrelevant(ConversionIntent.class));
          assertThat(foundConverter).isNotEmpty();
        });
      });

      describe("when an unsuccessful strategy is defined", () -> {
        test().strategies(()-> Lists.newArrayList(this::unsuccessfulStrategy));

        it("returns empty",()->{
          Optional converter = test().finder().findBestConverterFor(anIrrelevant(ConversionIntent.class));
          assertThat(converter).isEmpty();
        });
      });

      describe("when multiple strategies are defined", () -> {
        test().strategies(()-> Lists.newArrayList(this::unsuccessfulStrategy, this::successfulStrategy, this::failIfCalled));

        it("picks the first working strategy converter",()->{
          Optional foundConverter = test().finder().findBestConverterFor(anIrrelevant(ConversionIntent.class));
          assertThat(foundConverter).isNotEmpty();
        });
      });


    });

  }

  private <O> Optional<Function<ConversionProcess<O>, O>> failIfCalled(ConversionIntent<O> oConversionIntent) {
    Assertions.fail("This strategy shouldn't be called. A previous strategy should have found a converter");
    return null;
  }

  private <O> Optional<Function<ConversionProcess<O>, O>> unsuccessfulStrategy(ConversionIntent<O> oConversionIntent) {
    // Simulate not finding a converter
    return Optional.empty();
  }

  private <O> Optional<Function<ConversionProcess<O>, O>> successfulStrategy(ConversionIntent<O> oConversionIntent) {
    // Simulate finding a converter
    return Optional.of(mockear(Function.class));
  }
}