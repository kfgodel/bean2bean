package info.kfgodel.bean2bean.dsl.api;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.api.Bean2beanTask;
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
public class ExplicitTypesForConverterRegistrationTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a b2b dsl configuration", () -> {
      test().configure(()-> test().dsl().configure());
      test().dsl(Dsl::create);

      describe("scoping explicitly by type domains", () -> {
        describe("from class instances", () -> {
          describe("for lambda instances", () -> {
            it("accepts a function as converter", () -> {
              test().configure().scopingTo().accept(String.class).andProduce(CharSequence.class)
                .useConverter((in)-> in);
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(String.class).andProduce(CharSequence.class)
                .useConverter((input, b2b)-> input);
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(Nothing.class).andProduce(CharSequence.class)
                .useConverter(() -> "a value");
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(CharSequence.class);
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
              test().configure().scopingTo().accept(String.class).andProduce(CharSequence.class)
                .useConverter(new FunctionRef<String, CharSequence>((in) -> in) {});
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(String.class).andProduce(CharSequence.class)
                .useConverter(new BiFunctionRef<String, Bean2beanTask, CharSequence>((input, b2b)-> input) {});
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(Nothing.class).andProduce(String.class)
                .useConverter(new SupplierRef<String>(() -> "a value") {});
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(String.class);
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure().scopingTo().accept(String.class).andProduce(Nothing.class)
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
              Object result = test().dsl().convert().from("an object").to(getObjectType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType()).useConverter((input, b2b)-> input);
              Object result = test().dsl().convert().from("an object").to(getObjectType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType()).useConverter(() -> "a value");
              Object result = test().dsl().convert().from(null).to(getObjectType());
              assertThat(result).isEqualTo("a value");
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
              Object result = test().dsl().convert().from("an object").to(getObjectType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType())
                .useConverter(new BiFunctionRef<Object, Bean2beanTask, Object>((input, b2b)-> input) {});
              Object result = test().dsl().convert().from("an object").to(getObjectType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(getObjectType()).andProduce(getObjectType())
                .useConverter(new SupplierRef<Object>(() -> "a value") {});
              Object result = test().dsl().convert().from(null).to(getObjectType());
              assertThat(result).isEqualTo("a value");
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
              Object result = test().dsl().convert().from("an object").to(getObjectType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Object>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter((input, b2b)-> input);
              Object result = test().dsl().convert().from("an object").to(getObjectType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Nothing>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter(() -> "a value");
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(getObjectType());
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure()
                .scopingTo().accept(new TypeRef<String>(){}).andProduce(new TypeRef<Nothing>(){})
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
              Object result = test().dsl().convert().from("an object").to(getObjectType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Object>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter(new BiFunctionRef<Object, Bean2beanTask, Object>((input, b2b)-> input) {});
              Object result = test().dsl().convert().from("an object").to(getObjectType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Nothing>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter(new SupplierRef<Object>(() -> "a value") {});
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(getObjectType());
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure()
                .scopingTo().accept(new TypeRef<String>(){}).andProduce(new TypeRef<Nothing>(){})
                .useConverter(new ConsumerRef<String>(converterArgument::set) {});

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });


        });
      });
    });
  }

  private Type getObjectType() {
    return Object.class;
  }

}