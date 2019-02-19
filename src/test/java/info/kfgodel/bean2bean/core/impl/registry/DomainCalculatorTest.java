package info.kfgodel.bean2bean.core.impl.registry;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.core.api.registry.Domain;
import info.kfgodel.bean2bean.core.impl.registry.domains.DomainCalculator;
import info.kfgodel.bean2bean.dsl.api.B2bTestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Date: 17/02/19 - 15:43
 */
@RunWith(JavaSpecRunner.class)
public class DomainCalculatorTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a domain calculator", () -> {
      test().calculator(DomainCalculator::create);

      describe("for a type", () -> {
        test().domain(() -> test().calculator().forType(Object.class));

        it("calculates the domain that a type represents", () -> {
          assertThat(test().domain()).isNotNull();
        });

        it("it uses the type name as the domain name", () -> {
          assertThat(test().domain().getName()).isEqualTo("java.lang.Object");
        });
      });

      describe("for an object", () -> {
        test().domain(()-> test().calculator().forObject("an object"));

        it("calculates the domain based on its class",()->{
          assertThat(test().domain().getName()).isEqualTo("java.lang.String");
        });

        it("uses NullType for a null value",()->{
          Domain nullDomain = test().calculator().forObject(null);
          assertThat(nullDomain.getName()).isEqualTo("javax.lang.model.type.NullType");
        });
      });


    });

  }
}