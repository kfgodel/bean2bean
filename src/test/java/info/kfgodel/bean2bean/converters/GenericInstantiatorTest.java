package info.kfgodel.bean2bean.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.api.exceptions.CreationException;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.references.TypeRef;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 05/03/19 - 22:50
 */
@RunWith(JavaSpecRunner.class)
public class GenericInstantiatorTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("a generic instantiator", () -> {
      test().dsl(Dsl::create);

      describe("registered on b2b", () -> {
        beforeEach(() -> {
          test().dsl().configure().useConverter(GenericInstantiator.create());
        });

        it("creates an instance of the given target type using the niladic constructor", () -> {
          String created = test().dsl().generate().anInstanceOf(String.class);
          assertThat(created).isEqualTo("");
        });

        it("allows creation of generified classes",()->{
          ArrayList<String> created = test().dsl().generate().anInstanceOf(new TypeRef<ArrayList<String>>() {});
          assertThat(created).isEmpty();
        });

        itThrows(CreationException.class, "if a non instantiable class is used as target", ()->{
          test().dsl().generate().anInstanceOf(Iterable.class);
        }, e->{
          assertThat(e).hasMessage("Type[interface java.lang.Iterable] is not instantiable using a niladic constructor");
        });

        itThrows(CreationException.class, "if a primitive type class is used as target", ()->{
          test().dsl().generate().anInstanceOf(int.class);
        }, e->{
          assertThat(e).hasMessage("Type[int] is not instantiable using a niladic constructor");
        });

        itThrows(CreationException.class, "if an array class is used as target", ()->{
          test().dsl().generate().anInstanceOf(String[].class);
        }, e->{
          assertThat(e).hasMessage("Type[class [Ljava.lang.String;] is not instantiable using a niladic constructor");
        });
      });

    });

  }
}