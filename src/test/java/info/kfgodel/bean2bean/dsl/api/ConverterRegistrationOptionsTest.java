package info.kfgodel.bean2bean.dsl.api;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.core.api.registry.DomainVector;
import info.kfgodel.bean2bean.dsl.impl.Dsl;
import info.kfgodel.bean2bean.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.other.references.ConsumerRef;
import info.kfgodel.bean2bean.other.references.FunctionRef;
import info.kfgodel.bean2bean.other.references.SupplierRef;
import info.kfgodel.bean2bean.other.references.TypeRef;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 20/02/19 - 22:35
 */
@RunWith(JavaSpecRunner.class)
public class ConverterRegistrationOptionsTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a b2b dsl configuration", () -> {
      test().configure(()-> test().dsl().configure());
      test().dsl(Dsl::create);

      describe("scoping implicitly by the input and output types", () -> {
        describe("for lambda instances", () -> {

          it("accepts a function as converter", () -> {
            test().configure().useConverter((in) -> in);
            Object result = test().dsl().convert().from("an object").to(getObjectType());
            assertThat(result).isEqualTo("an object");
          });

          it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
            test().configure().useConverter((input, b2b)-> input);
            Object result = test().dsl().convert().from("an object").to(getObjectType());
            assertThat(result).isEqualTo("an object");
          });

          it("accepts a supplier as a converter",()->{
            test().configure().useConverter(() -> "a value");
            Object result = test().dsl().convert().from(Nothing.INSTANCE).to(getObjectType());
            assertThat(result).isEqualTo("a value");
          });
          it("accepts a consumer as a converter",()->{
            AtomicReference<String> converterArgument = new AtomicReference<>();
            test().configure().useConverter(converterArgument::set);
            assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
            assertThat(converterArgument.get()).isEqualTo("an object");
          });

        });
        describe("for lambda references", () -> {
          it("accepts a function reference as converter", () -> {
            test().configure().useConverter(new FunctionRef<Object, Object>((in) -> in) {});
            Object result = test().dsl().convert().from("an object").to(getObjectType());
            assertThat(result).isEqualTo("an object");
          });

          it("accepts a bifunction reference that takes the dsl as second arg as a converter",()->{
            test().configure().useConverter(new BiFunctionRef<Object, Bean2beanTask, Object>((input, b2b)-> input) {});
            Object result = test().dsl().convert().from("an object").to(getObjectType());
            assertThat(result).isEqualTo("an object");
          });

          it("accepts a supplier reference as a converter",()->{
            test().configure().useConverter(new SupplierRef<Object>(() -> "a value") {});
            Object result = test().dsl().convert().from(Nothing.INSTANCE).to(getObjectType());
            assertThat(result).isEqualTo("a value");
          });

          it("accepts a consumer reference as a converter",()->{
            AtomicReference<String> converterArgument = new AtomicReference<>();
            test().configure().useConverter(new ConsumerRef<String>(converterArgument::set){});
            assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
            assertThat(converterArgument.get()).isEqualTo("an object");
          });
        });
      });

      describe("scoping explicitly by type domains", () -> {
        describe("from class instances", () -> {
          describe("for lambda instances", () -> {
            it("accepts a function as converter", () -> {
              test().configure().scopingTo().accept(String.class).andProduce(CharSequence.class)
                .useConverter((in)-> in);
              Object result = test().dsl().convert().from("an object").to(getObjectType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(String.class).andProduce(CharSequence.class)
                .useConverter((input, b2b)-> input);
              Object result = test().dsl().convert().from("an object").to(getObjectType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(Nothing.class).andProduce(getObjectType())
                .useConverter(() -> "a value");
              Object result = test().dsl().convert().from(null).to(getObjectType());
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure().scopingTo().accept(String.class).andProduce(Nothing.class)
                .useConverter(converterArgument::set);

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });
          describe("for lambda references", () -> {
            it("accepts a function as converter", () -> {
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType())
                .useConverter(new FunctionRef<Object, Object>((in) -> in) {});
              Object result = test().dsl().convert().from("an object").to(getObjectType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType())
                .useConverter(new BiFunctionRef<Object, Bean2beanTask, Object>((input, b2b)-> input) {});
              assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType())
                .useConverter(new SupplierRef<Object>(() -> "a value") {});
              assertThat(test().dsl().convert().from(null).to(getObjectType())).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType())
                .useConverter(new ConsumerRef<String>(converterArgument::set) {});

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });

        });
        describe("from type instances", () -> {
          describe("for lambda instances", () -> {
            it("accepts a function as converter", () -> {
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType()).useConverter((in) -> in);
              assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType()).useConverter((input, b2b)-> input);
              assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType()).useConverter(() -> "a value");
              assertThat(test().dsl().convert().from(null).to(getObjectType())).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType())
                .useConverter(converterArgument::set);

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });
          describe("for lambda references", () -> {
            it("accepts a function as converter", () -> {
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType())
                .useConverter(new FunctionRef<Object, Object>((in) -> in) {});
              assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType())
                .useConverter(new BiFunctionRef<Object, Bean2beanTask, Object>((input, b2b)-> input) {});
              assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType())
                .useConverter(new SupplierRef<Object>(() -> "a value") {});
              assertThat(test().dsl().convert().from(null).to(getObjectType())).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType())
                .useConverter(new ConsumerRef<String>(converterArgument::set) {});

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });

        });
        describe("from type references", () -> {
          describe("for lambda instances", () -> {
            it("accepts a function as converter", () -> {
              test().configure()
                .scopingTo().accept(new TypeRef<Object>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter((in) -> in);
              assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Object>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter((input, b2b)-> input);
              assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Object>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter(() -> "a value");
              assertThat(test().dsl().convert().from(null).to(getObjectType())).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure()
                .scopingTo().accept(new TypeRef<Object>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter(converterArgument::set);

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });
          describe("for lambda references", () -> {
            it("accepts a function as converter", () -> {
              test().configure()
                .scopingTo().accept(new TypeRef<Object>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter(new FunctionRef<Object, Object>((in) -> in) {});
              assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Object>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter(new BiFunctionRef<Object, Bean2beanTask, Object>((input, b2b)-> input) {});
              assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Object>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter(new SupplierRef<Object>(() -> "a value") {});
              assertThat(test().dsl().convert().from(null).to(getObjectType())).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure()
                .scopingTo().accept(new TypeRef<Object>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter(new ConsumerRef<String>(converterArgument::set) {});

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });


        });
      });

      describe("scoping explicity using a predicate", () -> {
        describe("for lambda instances", () -> {
          it("accepts a function as converter", () -> {
            test().configure().scopingWith(this::acceptAnyInput).useConverter((in) -> in);
            assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
          });

          it("accepts a bifunction that takes the dsl as second arg as a converter", () -> {
            test().configure().scopingWith(this::acceptAnyInput).useConverter((input, b2b)-> input);
            assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
          });

          it("accepts a supplier as a converter",()->{
            test().configure().scopingWith(this::acceptAnyInput).useConverter(() -> "a value");
            assertThat(test().dsl().convert().from(null).to(getObjectType())).isEqualTo("a value");
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
            assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
          });

          it("accepts a bifunction that takes the dsl as second arg as a converter", () -> {
            test().configure().scopingWith(this::acceptAnyInput)
              .useConverter(new BiFunctionRef<Object, Bean2beanTask, Object>((input, b2b)-> input) {});
            assertThat(test().dsl().convert().from("an object").to(getObjectType())).isEqualTo("an object");
          });

          it("accepts a supplier as a converter",()->{
            test().configure().scopingWith(this::acceptAnyInput)
              .useConverter(new SupplierRef<Object>(() -> "a value") {});
            assertThat(test().dsl().convert().from(null).to(getObjectType())).isEqualTo("a value");
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