package info.kfgodel.bean2bean.dsl.api;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import info.kfgodel.bean2bean.core.api.exceptions.B2bException;
import info.kfgodel.bean2bean.dsl.impl.DefaultDslConfiguration;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.FunctionRef;
import info.kfgodel.bean2bean.other.TypeRef;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 10/02/19 - 22:58
 */
@RunWith(JavaSpecRunner.class)
public class ConversionUsingFunctionsTest extends JavaSpec<B2bTestContext> {

  @Override
  public void define() {
    describe("a b2b dsl", () -> {
      test().dsl(() -> Dsl.create(test().config()));

      describe("with a default configuration", () -> {
        test().config(DefaultDslConfiguration::create);

        itThrows(B2bException.class, "when any conversion is attempted", () -> {
          test().dsl().convert().from("1").to(Integer.class);
        }, e -> {
          assertThat(e).hasMessage("Impossible conversion: No converter found from 1(java.lang.String) to java.lang.Integer");
        });
      });

      describe("when a converter is pre-defined in the config using a function", () -> {
        test().config(() -> DefaultDslConfiguration.create()
          .usingConverter(new FunctionRef<String,Integer>(Integer::parseInt){})
        );

        it("can convert the input value if it matches the functions input's type", () -> {
          Integer result = test().dsl().convert().from("1").to(Integer.class);
          assertThat(result).isEqualTo(1);
        });

        itThrows(B2bException.class, "if the input value types doesn't match the function", () -> {
          test().dsl().convert().from(1.0).to(Integer.class);
        }, e -> {
          assertThat(e).hasMessage("Impossible conversion: No converter found from 1.0(java.lang.Double) to java.lang.Integer");
        });
      });

      describe("when the pre-defined converter function declares parameterized types", () -> {
        test().config(() -> DefaultDslConfiguration.create()
          .usingConverter(ArrayListToListOfStringsConverter.create())
        );

        it("can do the conversion if exact parameterized types are expected", () -> {
          List<String> result = test().dsl().convert().from(Lists.newArrayList(1)).to(new TypeRef<List<String>>(){});
          assertThat(result).isEqualTo(Lists.newArrayList("1"));
        });

        itThrows(B2bException.class, "if un generified types are expected", () -> {
          test().dsl().convert().from(Lists.newArrayList(1)).to(List.class);
        }, e -> {
          assertThat(e).hasMessage("Impossible conversion: No converter found from [1](java.util.ArrayList) to java.util.List");
        });
      });


    });
  }
}