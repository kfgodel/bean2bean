package info.kfgodel.bean2bean.v3.dsl.api;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.v3.dsl.impl.Dsl;
import info.kfgodel.bean2bean.v3.other.references.BiFunctionRef;
import info.kfgodel.bean2bean.v3.other.references.ConsumerRef;
import info.kfgodel.bean2bean.v3.other.references.FunctionRef;
import info.kfgodel.bean2bean.v3.other.references.SupplierRef;
import info.kfgodel.reflect.references.TypeRef;
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

            it("can mix class and types accepts a function as converter", () -> {
              test().configure().scopingTo().accept(String.class).andProduce(CharSequence.class)
                .useConverter(new FunctionRef<String, CharSequence>((in) -> in) {});
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });
          });

        });
        describe("from type instances", () -> {
          describe("for lambda instances", () -> {
            it("accepts a function as converter", () -> {
              test().configure().scopingTo().accept(getStringType()).andProduce(getStringType()).useConverter((in) -> in);
              Object result = test().dsl().convert().from("an object").to(getStringType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(getStringType()).andProduce(getStringType()).useConverter((input, b2b)-> input);
              Object result = test().dsl().convert().from("an object").to(getStringType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(getNothingType()).andProduce(getStringType()).useConverter(() -> "a value");
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(getStringType());
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure().scopingTo().accept(getStringType()).andProduce(getNothingType())
                .useConverter(converterArgument::set);

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });
          describe("for lambda references", () -> {
            it("accepts a function as converter", () -> {
              test().configure().scopingTo().accept(getStringType()).andProduce(getStringType())
                .useConverter(new FunctionRef<Object, Object>((in) -> in) {});
              Object result = test().dsl().convert().from("an object").to(getStringType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(getStringType()).andProduce(getStringType())
                .useConverter(new BiFunctionRef<Object, Bean2beanTask, Object>((input, b2b)-> input) {});
              Object result = test().dsl().convert().from("an object").to(getStringType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(getNothingType()).andProduce(getStringType())
                .useConverter(new SupplierRef<Object>(() -> "a value") {});
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(getStringType());
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure().scopingTo().accept(getStringType()).andProduce(getNothingType())
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
              Object result = test().dsl().convert().from("an object").to(getStringType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Object>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter((input, b2b)-> input);
              Object result = test().dsl().convert().from("an object").to(getStringType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Nothing>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter(() -> "a value");
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(getStringType());
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
              Object result = test().dsl().convert().from("an object").to(getStringType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Object>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter(new BiFunctionRef<Object, Bean2beanTask, Object>((input, b2b)-> input) {});
              Object result = test().dsl().convert().from("an object").to(getStringType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Nothing>(){}).andProduce(new TypeRef<Object>(){})
                .useConverter(new SupplierRef<Object>(() -> "a value") {});
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(getStringType());
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
        describe("from class source to type target", () -> {
          describe("for lambda instances", () -> {
            it("accepts a function as converter", () -> {
              test().configure().scopingTo().accept(String.class).andProduce(getCharSequence())
                .useConverter((in)-> in);
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(String.class).andProduce(getCharSequence())
                .useConverter((input, b2b)-> input);
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(Nothing.class).andProduce(getCharSequence())
                .useConverter(() -> "a value");
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(CharSequence.class);
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure().scopingTo().accept(String.class).andProduce(getNothingType())
                .useConverter(converterArgument::set);

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });
          describe("for lambda references", () -> {
            it("accepts a function as converter", () -> {
              test().configure().scopingTo().accept(String.class).andProduce(getCharSequence())
                .useConverter(new FunctionRef<String, CharSequence>((in) -> in) {});
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(String.class).andProduce(getCharSequence())
                .useConverter(new BiFunctionRef<String, Bean2beanTask, CharSequence>((input, b2b)-> input) {});
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(Nothing.class).andProduce(getStringType())
                .useConverter(new SupplierRef<String>(() -> "a value") {});
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(String.class);
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure().scopingTo().accept(String.class).andProduce(getNothingType())
                .useConverter(new ConsumerRef<String>(converterArgument::set) {});

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });

        });
        describe("from type source to class target", () -> {
          describe("for lambda instances", () -> {
            it("accepts a function as converter", () -> {
              test().configure().scopingTo().accept(getStringType()).andProduce(CharSequence.class)
                .useConverter((in)-> in);
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(getStringType()).andProduce(CharSequence.class)
                .useConverter((input, b2b)-> input);
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(getNothingType()).andProduce(CharSequence.class)
                .useConverter(() -> "a value");
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(CharSequence.class);
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure().scopingTo().accept(getStringType()).andProduce(Nothing.class)
                .useConverter(converterArgument::set);

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });
          describe("for lambda references", () -> {
            it("accepts a function as converter", () -> {
              test().configure().scopingTo().accept(getStringType()).andProduce(CharSequence.class)
                .useConverter(new FunctionRef<String, CharSequence>((in) -> in) {});
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure().scopingTo().accept(getStringType()).andProduce(CharSequence.class)
                .useConverter(new BiFunctionRef<String, Bean2beanTask, CharSequence>((input, b2b)-> input) {});
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure().scopingTo().accept(getNothingType()).andProduce(CharSequence.class)
                .useConverter(new SupplierRef<String>(() -> "a value") {});
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(CharSequence.class);
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure().scopingTo().accept(getStringType()).andProduce(Nothing.class)
                .useConverter(new ConsumerRef<String>(converterArgument::set) {});

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });

        });
        describe("from typeRef source to type target", () -> {
          describe("for lambda instances", () -> {
            it("accepts a function as converter", () -> {
              test().configure()
                .scopingTo().accept(new TypeRef<String>(){}).andProduce(getStringType())
                .useConverter((in) -> in);
              Object result = test().dsl().convert().from("an object").to(getStringType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<String>(){}).andProduce(getStringType())
                .useConverter((input, b2b)-> input);
              Object result = test().dsl().convert().from("an object").to(getStringType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Nothing>(){}).andProduce(getStringType())
                .useConverter(() -> "a value");
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(getStringType());
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure()
                .scopingTo().accept(new TypeRef<String>(){}).andProduce(getNothingType())
                .useConverter(converterArgument::set);

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });
          describe("for lambda references", () -> {
            it("accepts a function as converter", () -> {
              test().configure()
                .scopingTo().accept(new TypeRef<String>(){}).andProduce(getStringType())
                .useConverter(new FunctionRef<String, Object>((in) -> in) {});
              Object result = test().dsl().convert().from("an object").to(getStringType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<String>(){}).andProduce(getStringType())
                .useConverter(new BiFunctionRef<String, Bean2beanTask, Object>((input, b2b)-> input) {});
              Object result = test().dsl().convert().from("an object").to(getStringType());
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure()
                .scopingTo().accept(new TypeRef<Nothing>(){}).andProduce(getStringType())
                .useConverter(new SupplierRef<Object>(() -> "a value") {});
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(getStringType());
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure()
                .scopingTo().accept(new TypeRef<String>(){}).andProduce(getNothingType())
                .useConverter(new ConsumerRef<String>(converterArgument::set) {});

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });
        });
        describe("from type source to typeRef target", () -> {
          describe("for lambda instances", () -> {
            it("accepts a function as converter", () -> {
              test().configure()
                .scopingTo().accept(getStringType()).andProduce(new TypeRef<CharSequence>(){})
                .useConverter((in) -> in);
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure()
                .scopingTo().accept(getStringType()).andProduce(new TypeRef<CharSequence>(){})
                .useConverter((input, b2b)-> input);
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure()
                .scopingTo().accept(getNothingType()).andProduce(new TypeRef<CharSequence>(){})
                .useConverter(() -> "a value");
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(CharSequence.class);
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure()
                .scopingTo().accept(getStringType()).andProduce(new TypeRef<Nothing>(){})
                .useConverter(converterArgument::set);

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });
          describe("for lambda references", () -> {
            it("accepts a function as converter", () -> {
              test().configure()
                .scopingTo().accept(getStringType()).andProduce(new TypeRef<CharSequence>(){})
                .useConverter(new FunctionRef<Object, Object>((in) -> in) {});
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a bifunction that takes the dsl as second arg as a converter",()->{
              test().configure()
                .scopingTo().accept(getStringType()).andProduce(new TypeRef<CharSequence>(){})
                .useConverter(new BiFunctionRef<Object, Bean2beanTask, Object>((input, b2b)-> input) {});
              Object result = test().dsl().convert().from("an object").to(CharSequence.class);
              assertThat(result).isEqualTo("an object");
            });

            it("accepts a supplier as a converter",()->{
              test().configure()
                .scopingTo().accept(getNothingType()).andProduce(new TypeRef<CharSequence>(){})
                .useConverter(new SupplierRef<Object>(() -> "a value") {});
              Object result = test().dsl().convert().from(Nothing.INSTANCE).to(CharSequence.class);
              assertThat(result).isEqualTo("a value");
            });

            it("accepts a consumer as a converter",()->{
              AtomicReference<String> converterArgument = new AtomicReference<>();

              test().configure()
                .scopingTo().accept(getStringType()).andProduce(new TypeRef<Nothing>(){})
                .useConverter(new ConsumerRef<String>(converterArgument::set) {});

              assertThat(test().dsl().convert().from("an object").to(Nothing.class)).isNull();
              assertThat(converterArgument.get()).isEqualTo("an object");
            });
          });
        });
      });
    });
  }

  private Type getCharSequence() {
    return CharSequence.class;
  }
  private Type getNothingType() {
    return Nothing.class;
  }
  private Type getStringType() {
    return String.class;
  }

}