package info.kfgodel.bean2bean.v3.core.impl.registry;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.v3.dsl.api.B2bTestContext;
import info.kfgodel.bean2bean.v3.dsl.impl.Dsl;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies that nested errors are properly informed in the stacktrace with sufficient context
 * Date: 17/03/19 - 13:11
 */
@RunWith(JavaSpecRunner.class)
public class NestedErrorReportingTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a failing converter", () -> {
      beforeEach(() -> {
        test().dsl().configure().scopingTo().accept(String.class).andProduce(Integer.class)
          .useConverter(this::naiveIntegerConverter);
      });
      test().dsl(Dsl::create);

      itThrows(NumberFormatException.class, "when an unexpected input is passed", () -> {
        test().dsl().convert().from("not a number").to(Integer.class);
      }, e -> {
        assertThat(e).hasMessage("For input string: \"not a number\"");
      });

      describe("when used from other converter", () -> {
        beforeEach(()->{
          test().dsl().configure().scopingTo().accept(Optional.class).andProduce(Integer.class)
            .useConverter(this::nestedOptionalConverter);
        });

        itThrows(ConversionException.class, "when an exception is thrown from the inner converter", ()->{
          test().dsl().convert().from(Optional.of("contained NaN")).to(Integer.class);
        }, e->{
          assertThat(e).hasMessage("Failed conversion from Optional[contained NaN] ∈ {java.util.Optional} to {java.lang.Integer}\n" +
            "\tdue to: For input string: \"contained NaN\"");
        });

        describe("when there's more than one nesting level", () -> {
          itThrows(ConversionException.class, "when an exception is thrown from the inner converter", ()->{
            test().dsl().convert().from(Optional.of(Optional.of("contained NaN"))).to(Integer.class);
          }, e->{
            assertThat(e).hasMessage("Failed conversion from Optional[Optional[contained NaN]] ∈ {java.util.Optional} to {java.lang.Integer}\n" +
              "\tdue to: Failed conversion from Optional[contained NaN] ∈ {java.util.Optional} to {java.lang.Integer}\n" +
              "\tdue to: For input string: \"contained NaN\"");
          });

          itThrows(ConversionException.class, "even on many nested levels", ()->{
            Optional<Optional<Optional<Optional<String>>>> source = Optional.of(Optional.of(Optional.of(Optional.of("nested NaN"))));
            test().dsl().convert().from(source).to(Integer.class);
          }, e->{
            assertThat(e).hasMessage("Failed conversion from Optional[Optional[Optional[Optional[nested NaN]]]] ∈ {java.util.Optional} to {java.lang.Integer}\n" +
              "\tdue to: Failed conversion from Optional[Optional[Optional[nested NaN]]] ∈ {java.util.Optional} to {java.lang.Integer}\n" +
              "\tdue to: Failed conversion from Optional[Optional[nested NaN]] ∈ {java.util.Optional} to {java.lang.Integer}\n" +
              "\tdue to: Failed conversion from Optional[nested NaN] ∈ {java.util.Optional} to {java.lang.Integer}\n" +
              "\tdue to: For input string: \"nested NaN\"");
          });
        });

      });

    });

  }


  private Integer naiveIntegerConverter(String source) {
    return Integer.parseInt(source);
  }

  private Integer nestedOptionalConverter(Optional<?> source, Bean2beanTask task) {
    return source
      .map(contained -> task.getDsl().convert().from(contained).to(Integer.class))
      .orElse(null);
  }

}
