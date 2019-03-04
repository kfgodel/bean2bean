package info.kfgodel.bean2bean.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.references.TypeRef;
import org.junit.runner.RunWith;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 03/03/19 - 17:33
 */
@RunWith(JavaSpecRunner.class)
public class ArrayInstantiatorTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("an array instantiator converter registered with b2b", () -> {
      test().dsl(Dsl::create);

      describe("when registered with the predicate", () -> {
        beforeEach(() -> {
          test().dsl().configure().usingConverter(ArrayInstantiator.create(), ArrayInstantiator::shouldBeUsed);
        });

        itThrows(ConversionException.class, "if input is not an integer", ()->{
          test().dsl().convert().from(1.5).to(String[].class);
        }, e ->{
          assertThat(e).hasMessage("No converter found from 1.5{java.lang.Double} to {java.lang.String[]}");
        });

        itThrows(ConversionException.class, "because it's not used",()->{
          test().dsl().convert().from(1).to(Object.class);
        }, e->{
          assertThat(e).hasMessage("No converter found from 1{java.lang.Integer} to {java.lang.Object}");
        });

        it("can create the expected array with the input size",()->{
          String[] result = test().dsl().convert().from(1).to(String[].class);
          assertThat(result).hasSize(1);
        });

        it("can create a parameterized array with the input size",()->{
          List<String>[] result = test().dsl().convert().from(1).to(new TypeRef<List<String>[]>() {});
          assertThat(result).hasSize(1);
        });
      });

      describe("when registered without a predicate", () -> {
        beforeEach(() -> {
          test().dsl().configure().usingConverter(ArrayInstantiator.create());
        });

        itThrows(ConversionException.class, "if input is not an integer", ()->{
          test().dsl().convert().from(1.5).to(String[].class);
        }, e ->{
          assertThat(e).hasMessage("No converter found from 1.5{java.lang.Double} to {java.lang.String[]}");
        });

        itThrows(CreationException.class, "if a non array type is used as target",()->{
          test().dsl().convert().from(1).to(Object.class);
        }, e->{
          assertThat(e).hasMessage("Can't instantiate array for non array type: class java.lang.Object");
        });

      });
    });

  }
}