package info.kfgodel.bean2bean.v3.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.v3.converters.datetimes.String2LocalDateConverter;
import info.kfgodel.bean2bean.v3.converters.datetimes.String2LocalDateTimeConverter;
import info.kfgodel.bean2bean.v3.converters.datetimes.String2LocalTimeConverter;
import info.kfgodel.bean2bean.v3.converters.datetimes.String2ZonedDateTimeConverter;
import info.kfgodel.bean2bean.v3.converters.datetimes.Temporal2StringConverter;
import info.kfgodel.bean2bean.v3.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.v3.dsl.impl.Dsl;
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
        test().dsl().configure().useConverter(String2ZonedDateTimeConverter.create());
        test().dsl().configure().useConverter(String2LocalDateTimeConverter.create());
        test().dsl().configure().useConverter(String2LocalDateConverter.create());
        test().dsl().configure().useConverter(String2LocalTimeConverter.create());
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

      describe("when converting from string", () -> {
        test().result(() -> test().dsl().convert().from(test().source()).to(context().targetType()));

        describe("to ZonedDateTime", () -> {
          test().targetType(()-> ZonedDateTime.class);
          it("can convert an iso string into a ZonedDateTime", () -> {
            test().source(() -> "2000-01-01T12:30:20.000000010Z[UCT]");
            assertThat(test().result()).isEqualTo(ZonedDateTime.of(2000, 1, 1, 12, 30, 20, 10, ZoneId.of("UCT")));
          });
          itThrows(ConversionException.class, "when the string doesn't represent a zonedDatetime",()->{
            test().source(() -> "2000-01-01T12:30");
            test().result(); // Excercise
          }, e ->{
            assertThat(e).hasMessage("Failed to parse [2000-01-01T12:30] into a java.time.ZonedDateTime: Text '2000-01-01T12:30' could not be parsed at index 16");
          });
        });

        describe("to LocalDateTime", () -> {
          test().targetType(()-> LocalDateTime.class);
          it("can convert an iso string into a LocalDateTime", () -> {
            test().source(() -> "2000-01-01T12:30");
            assertThat(test().result()).isEqualTo(LocalDateTime.of(2000, 1, 1, 12, 30, 0, 0));
          });
          itThrows(ConversionException.class, "when the string doesn't represent a LocalDateTime",()->{
            test().source(() -> "2000-01-01");
            test().result(); // Excercise
          }, e ->{
            assertThat(e).hasMessage("Failed to parse [2000-01-01] into a java.time.LocalDateTime: Text '2000-01-01' could not be parsed at index 10");
          });
        });

        describe("to LocalDate", () -> {
          test().targetType(()-> LocalDate.class);
          it("can convert an iso string into a LocalDate", () -> {
            test().source(() -> "2000-01-01");
            assertThat(test().result()).isEqualTo(LocalDate.of(2000, 1, 1));
          });
          itThrows(ConversionException.class, "when the string doesn't represent a LocalDate",()->{
            test().source(() -> "12:30:20");
            test().result(); // Excercise
          }, e ->{
            assertThat(e).hasMessage("Failed to parse [12:30:20] into a java.time.LocalDate: Text '12:30:20' could not be parsed at index 0");
          });
        });

        describe("to LocalTime", () -> {
          test().targetType(()-> LocalTime.class);
          it("can convert an iso string into a LocalTime", () -> {
            test().source(() -> "12:30:20");
            assertThat(test().result()).isEqualTo(LocalTime.of(12, 30, 20));
          });
          itThrows(ConversionException.class, "when the string doesn't represent a LocalTime",()->{
            test().source(() -> "2000-01-01");
            test().result(); // Excercise
          }, e ->{
            assertThat(e).hasMessage("Failed to parse [2000-01-01] into a java.time.LocalTime: Text '2000-01-01' could not be parsed at index 2");
          });
        });

      });

    });
  }
}
