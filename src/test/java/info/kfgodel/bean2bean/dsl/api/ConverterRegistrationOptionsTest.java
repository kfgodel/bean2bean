package info.kfgodel.bean2bean.dsl.api;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.other.references.ConsumerRef;
import info.kfgodel.bean2bean.other.references.FunctionRef;
import info.kfgodel.bean2bean.other.references.SupplierRef;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.type.NullType;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 20/02/19 - 22:35
 */
@RunWith(JavaSpecRunner.class)
public class ConverterRegistrationOptionsTest extends JavaSpec<B2bTestContext> {
  public static Logger LOG = LoggerFactory.getLogger(ConverterRegistrationOptionsTest.class);

  @Override
  public void define() {
    describe("a b2b dsl configuration", () -> {
      test().configure(()-> test().dsl().configure());
      test().dsl(Dsl::create);

      describe("used to register vector scoped converters", () -> {

        it("accepts a function as converter", () -> {
          test().configure().usingConverter((in) -> in);
          assertThat(test().dsl().convert().from("an object").to(Object.class)).isEqualTo("an object");
        });

        it("accepts a function reference as converter", () -> {
          test().configure().usingConverter(new FunctionRef<Object, Object>((in) -> in) {});
          assertThat(test().dsl().convert().from("an object").to(Object.class)).isEqualTo("an object");
        });

        it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
          test().configure().usingConverter((input, b2b)-> input);
          assertThat(test().dsl().convert().from("an object").to(Object.class)).isEqualTo("an object");
        });

        it("accepts a bifunction reference that takes the dsl as second arg as a converter",()->{
          test().configure().usingConverter(new BiFunctionRef<Object, B2bDsl, Object>((input, b2b)-> input) {});
          assertThat(test().dsl().convert().from("an object").to(Object.class)).isEqualTo("an object");
        });

        it("accepts a supplier as a converter",()->{
          test().configure().usingConverter(() -> "a value");
          assertThat(test().dsl().convert().from(null).to(Object.class)).isEqualTo("a value");
        });

        it("accepts a supplier reference as a converter",()->{
          test().configure().usingConverter(new SupplierRef<Object>(() -> "a value") {});
          assertThat(test().dsl().convert().from(null).to(Object.class)).isEqualTo("a value");
        });

        it("accepts a consumer as a converter",()->{
          AtomicReference<String> converterArgument = new AtomicReference<>();
          test().configure().usingConverter(converterArgument::set);
          assertThat(test().dsl().convert().from("an object").to(NullType.class)).isNull();
          assertThat(converterArgument.get()).isEqualTo("an object");
        });

        it("accepts a consumer reference as a converter",()->{
          AtomicReference<String> converterArgument = new AtomicReference<>();
          test().configure().usingConverter(new ConsumerRef<String>(converterArgument::set){});
          assertThat(test().dsl().convert().from("an object").to(NullType.class)).isNull();
          assertThat(converterArgument.get()).isEqualTo("an object");
        });
      });



    });

  }
}