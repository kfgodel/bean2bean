package info.kfgodel.bean2bean.v3.dsl.api;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.v3.dsl.impl.Dsl;
import info.kfgodel.bean2bean.v3.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.v3.other.references.ConsumerRef;
import info.kfgodel.bean2bean.v3.other.references.FunctionRef;
import info.kfgodel.bean2bean.v3.other.references.SupplierRef;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 20/02/19 - 22:35
 */
@RunWith(JavaSpecRunner.class)
public class ExplicitPredicateForConverterRegistrationTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a b2b dsl configuration", () -> {
      test().configure(()-> test().dsl().configure());
      test().dsl(Dsl::create);

      describe("scoping explicity using a predicate", () -> {
        describe("for lambda instances", () -> {
          it("accepts a function as converter", () -> {
            test().configure().scopingWith(this::acceptAnyInput).useConverter((in) -> in);
            Object result = test().dsl().convert().from("an object").to(getObjectType());
            assertThat(result).isEqualTo("an object");
          });

          it("accepts a bifunction that takes the dsl as second arg as a converter", () -> {
            test().configure().scopingWith(this::acceptAnyInput).useConverter((input, b2b)-> input);
            Object result = test().dsl().convert().from("an object").to(getObjectType());
            assertThat(result).isEqualTo("an object");
          });

          it("accepts a supplier as a converter",()->{
            test().configure().scopingWith(this::acceptAnyInput).useConverter(() -> "a value");
            Object result = test().dsl().convert().from(null).to(getObjectType());
            assertThat(result).isEqualTo("a value");
          });

          it("accepts a consumer as a converter",()->{
            AtomicReference<String> converterArgument = new AtomicReference<>();
            test().configure().scopingWith(this::acceptAnyInput).useConverter(converterArgument::set);
            assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
            assertThat(converterArgument.get()).isEqualTo("an object");
          });
        });
        describe("for lambda references", () -> {
          it("accepts a function as converter", () -> {
            test().configure().scopingWith(this::acceptAnyInput)
              .useConverter(new FunctionRef<Object, Object>((in) -> in) {});
            Object result = test().dsl().convert().from("an object").to(getObjectType());
            assertThat(result).isEqualTo("an object");
          });

          it("accepts a bifunction that takes the dsl as second arg as a converter", () -> {
            test().configure().scopingWith(this::acceptAnyInput)
              .useConverter(new BiFunctionRef<Object, Bean2beanTask, Object>((input, b2b)-> input) {});
            Object result = test().dsl().convert().from("an object").to(getObjectType());
            assertThat(result).isEqualTo("an object");
          });

          it("accepts a supplier as a converter",()->{
            test().configure().scopingWith(this::acceptAnyInput)
              .useConverter(new SupplierRef<Object>(() -> "a value") {});
            Object result = test().dsl().convert().from(null).to(getObjectType());
            assertThat(result).isEqualTo("a value");
          });

          it("accepts a consumer as a converter",()->{
            AtomicReference<String> converterArgument = new AtomicReference<>();
            test().configure().scopingWith(this::acceptAnyInput)
              .useConverter(new ConsumerRef<String>(converterArgument::set) {});
            assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
            assertThat(converterArgument.get()).isEqualTo("an object");
          });
        });
      });
    });
  }

  private Type getObjectType() {
    return Object.class;
  }

  private boolean acceptAnyInput(DomainVector conversionVector){
    return true;
  }
}