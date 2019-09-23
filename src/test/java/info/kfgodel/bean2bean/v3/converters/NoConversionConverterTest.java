package info.kfgodel.bean2bean.v3.converters;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.v3.core.api.exceptions.ConversionException;
import info.kfgodel.bean2bean.v3.dsl.impl.Dsl;
import info.kfgodel.bean2bean.v3.other.references.TypeRef;
import org.junit.runner.RunWith;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 09/03/19 - 12:26
 */
@RunWith(JavaSpecRunner.class)
public class NoConversionConverterTest extends JavaSpec<ConverterTestContext> {
  @Override
  public void define() {
    describe("a polymorphic converter", () -> {
      test().dsl(Dsl::create);

      describe("scoped by domain vector", () -> {
        beforeEach(()->{
          test().dsl().configure().useConverter(NoConversionConverter.create());
        });

        it("returns the source object when a target of the same type is expected", () -> {
          String source = "hey";
          String result = test().dsl().convert().from(source).to(String.class);
          assertThat(result).isSameAs(source);
        });

        it("returns the source object when a supertype is expected",()->{
          String source = "hey";
          CharSequence result = test().dsl().convert().from(source).to(CharSequence.class);
          assertThat(result).isSameAs(source);
        });

        itThrows(ConversionException.class, "when a unrelated type is expected", ()->{
          test().dsl().convert().from("1").to(Integer.class);
        }, e->{
          assertThat(e).hasMessage("Source \"1\" ∈ {java.lang.String} is not assignable to {java.lang.Integer}");
        });

        it("returns null when null is used as source regardless of the target type",()->{
          String result = test().dsl().convert().from(null).to(String.class);
          assertThat(result).isNull();
        });

        it("returns the source object when a parameterized but assignable type is expected",()->{
          ArrayList<String> source = new ArrayList<>();
          List<String> result = test().dsl().convert().from(source).to(new TypeRef<List<String>>() {});
          assertThat(result).isSameAs(source);
        });

        it("returns the source object when an unbound variable type is expected",()->{
          // An unbound type variable is equivalent to Object
          String source = "an object";
          String result = test().dsl().convert().from(source).to(getUnboundTypeVariable());
          assertThat(result).isSameAs(source);
        });

        it("returns the source object when a matching bound variable type is expected",()->{
          // An unbound type variable is equivalent to Object
          String source = "an object";
          String result = test().dsl().convert().from(source).to(getSingleStringBondedTypeVariable());
          assertThat(result).isSameAs(source);
        });

        itThrows(ConversionException.class, "when a non matching bound variable type is expected", ()->{
          test().dsl().convert().from("1").to(getSingleNumberBondedTypeVariable());
        }, e->{
          assertThat(e).hasMessage("Source \"1\" ∈ {java.lang.String} is not assignable to {E} ⊇ E extends class java.lang.Number");
        });

        itThrows(ConversionException.class, "when a matching bound variable type is expected but is not the first bound", ()->{
          Iterable source = new ArrayList();
          test().dsl().convert().from(source).to(getMultiUpperBondedTypeVariable());
        }, e->{
          assertThat(e).hasMessage("Source [] ∈ {java.util.ArrayList} is not assignable to {E} ⊇ E extends class java.lang.String, interface java.lang.Iterable");
        });
      });

      describe("scoped by predicate", () -> {
        beforeEach(()->{
          test().dsl().configure().scopingWith(NoConversionConverter::shouldBeUsed).useConverter(NoConversionConverter.create());
        });

        it("returns the source object when a target of the same type is expected", () -> {
          String source = "hey";
          String result = test().dsl().convert().from(source).to(String.class);
          assertThat(result).isSameAs(source);
        });

        it("returns the source object when a supertype is expected",()->{
          String source = "hey";
          CharSequence result = test().dsl().convert().from(source).to(CharSequence.class);
          assertThat(result).isSameAs(source);
        });

        itThrows(ConversionException.class, "when a unrelated type is expected", ()->{
          test().dsl().convert().from("1").to(Integer.class);
        }, e->{
          assertThat(e).hasMessage("No converter found from \"1\" ∈ {java.lang.String} to {java.lang.Integer}");
        });

        it("returns null when null is used as source regardless of the target type",()->{
          String result = test().dsl().convert().from(null).to(String.class);
          assertThat(result).isNull();
        });

        it("returns the source object when a parameterized but assignable type is expected",()->{
          ArrayList<String> source = new ArrayList<>();
          List<String> result = test().dsl().convert().from(source).to(new TypeRef<List<String>>() {});
          assertThat(result).isSameAs(source);
        });

        it("returns the source object when an unbound variable type is expected",()->{
          // An unbound type variable is equivalent to Object
          String source = "an object";
          String result = test().dsl().convert().from(source).to(getUnboundTypeVariable());
          assertThat(result).isSameAs(source);
        });

        it("returns the source object when a matching bound variable type is expected",()->{
          // An unbound type variable is equivalent to Object
          String source = "an object";
          String result = test().dsl().convert().from(source).to(getSingleStringBondedTypeVariable());
          assertThat(result).isSameAs(source);
        });

        itThrows(ConversionException.class, "when a non matching bound variable type is expected", ()->{
          test().dsl().convert().from("1").to(getSingleNumberBondedTypeVariable());
        }, e->{
          assertThat(e).hasMessage("No converter found from \"1\" ∈ {java.lang.String} to {E} ⊇ E extends class java.lang.Number");
        });

        itThrows(ConversionException.class, "when a matching bound variable type is expected but is not the first bound", ()->{
          Iterable source = new ArrayList();
          test().dsl().convert().from(source).to(getMultiUpperBondedTypeVariable());
        }, e->{
          assertThat(e).hasMessage("No converter found from [] ∈ {java.util.ArrayList} to {E} ⊇ E extends class java.lang.String, interface java.lang.Iterable");
        });

      });


    });
  }

  private <E> Type getUnboundTypeVariable() {
    return new TypeRef<E>() {}.getReference();
  }

  private <E extends String> Type getSingleStringBondedTypeVariable() {
    return new TypeRef<E>() {}.getReference();
  }

  private <E extends Number> Type getSingleNumberBondedTypeVariable() {
    return new TypeRef<E>() {}.getReference();
  }

  private <E extends String & Iterable> Type getMultiUpperBondedTypeVariable() {
    return new TypeRef<E>() {}.getReference();
  }

}