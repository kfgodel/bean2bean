package info.kfgodel.bean2bean.v3.core.impl.registry;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.v3.core.impl.registry.domains.NamedDomain;
import info.kfgodel.bean2bean.v3.dsl.api.B2bTestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 17/02/19 - 16:12
 */
@RunWith(JavaSpecRunner.class)
public class NamedDomainTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a named domain", () -> {
      test().domain(() -> NamedDomain.create("a name"));

      it("has a name that identifies it", () -> {
        assertThat(test().domain().getName()).isEqualTo("a name");
      });

      it("has a hashcode based on its name", () -> {
        assertThat(test().domain().hashCode()).isEqualTo(NamedDomain.create("a name").hashCode());
      });

      it("is equal to other domain with same name", () -> {
        assertThat(test().domain()).isEqualTo(NamedDomain.create("a name"));
      });

      it("is different from a domain with different name", () -> {
        assertThat(test().domain()).isNotEqualTo(NamedDomain.create("other name"));
      });
    });

  }
}