package info.kfgodel.bean2bean.v3.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import info.kfgodel.bean2bean.v3.converters.json.JsonString2ObjectConverter;
import info.kfgodel.bean2bean.v3.converters.json.Object2JsonStringConverter;
import info.kfgodel.bean2bean.v3.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.v3.dsl.impl.Dsl;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class verifies json converters between strings and objects
 * Date: 19/03/19 - 19:58
 */
@RunWith(JavaSpecRunner.class)
public class JsonConvertersTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("the Json converters", () -> {
      beforeEach(() -> {
        test().dsl().configure().scopingWith(Object2JsonStringConverter::shouldBeUsed).useConverter(Object2JsonStringConverter.create());
        test().dsl().configure().scopingWith(JsonString2ObjectConverter::shouldBeUsed).useConverter(JsonString2ObjectConverter.create());
      });
      test().dsl(Dsl::create);

      describe("when converting from a string", () -> {

        it("creates an object from the JSON string", () -> {
          Map result = test().dsl().convert().from("{}").to(Map.class);
          assertThat(result).isEmpty();
        });

        itThrows(ConversionException.class, "if the string is not JSON", () -> {
          test().dsl().convert().from("not a json string").to(Object.class);
        }, e -> {
          assertThat(e).hasMessage("Failed to parse JSON from \"not a json string\" into {java.lang.Object}: Unrecognized token 'not': was expecting 'null', 'true', 'false' or NaN\n" +
            " at [Source: (String)\"not a json string\"; line: 1, column: 4]");
        });
      });

      describe("when converting to a string", () -> {

        it("creates a string JSON from the object state", () -> {
          String result = test().dsl().convert().from(new HashMap()).to(String.class);
          assertThat(result).isEqualTo("{}");
        });

        it("represents strings with double quotes",()->{
          String result = test().dsl().convert().from("a string").to(String.class);
          assertThat(result).isEqualTo("\"a string\"");
        });

        it("represents lists and arrays as json arrays",()->{
          assertThat(test().dsl().convert().from(new String[]{"1","2"}).to(String.class)).isEqualTo("[\"1\",\"2\"]");
          assertThat(test().dsl().convert().from(Lists.newArrayList(1,2)).to(String.class)).isEqualTo("[1,2]");
        });

        itThrows(ConversionException.class, "if the object can't be converted to json", () -> {
          test().dsl().convert().from(new Object()).to(String.class);
        }, e -> {
          assertThat(e.getMessage())
            .startsWith("Failed to generate JSON for java.lang.Object")
            .endsWith(" ∈ {java.lang.Object}: No serializer found for class java.lang.Object and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)");
        });
      });
    });


  }
}