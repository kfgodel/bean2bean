package info.kfgodel.bean2bean.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.converters.datetimes.Temporal2StringConverter;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import org.junit.runner.RunWith;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class verifies the conversion between strings and java 8 dates and times
 * Date: 23/03/19 - 18:12
 */
@RunWith(JavaSpecRunner.class)
public class StringAndDateTimeConvertersTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("date and time converters for string when registered on b2b", () -> {
      beforeEach(() -> {
        test().dsl().configure().useConverter(Temporal2StringConverter.create());
      });
      test().dsl(Dsl::create);

      describe("when converting to string", () -> {
        test().result(() -> test().dsl().convert().from(test().source()).to(String.class));

        it("can convert a ZonedDateTime to an iso string", () -> {
          test().source(() -> ZonedDateTime.of(2000, 1, 1, 12, 30, 20, 10, ZoneId.of("UCT")));
          assertThat(test().result()).isEqualTo("2000-01-01T12:30:20.000000010Z[UCT]");
        });
        it("can convert a LocalDateTime to an iso string", () -> {
          test().source(() -> LocalDateTime.of(2000, 1, 1, 12, 30, 0, 0));
          assertThat(test().result()).isEqualTo("2000-01-01T12:30");
        });
        it("can convert a LocalDate to an iso string", () -> {
          test().source(() -> LocalDate.of(2000, 1, 1));
          assertThat(test().result()).isEqualTo("2000-01-01");
        });
        it("can convert a LocalTime to an iso string", () -> {
          test().source(() -> LocalTime.of(12, 30, 20));
          assertThat(test().result()).isEqualTo("12:30:20");
        });
      });


    });

  }
}
