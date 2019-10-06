package info.kfgodel.bean2bean.v4.impl.vector;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.v4.B2bTestContext;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 24/9/19 - 17:40
 */
@RunWith(JavaSpecRunner.class)
public class VectorTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a conversion vector", () -> {
      test().vector(() -> Vector.create(1, "2"));

      it("is equal to another vector with equal source and destination", () -> {
        assertThat(test().vector()).isEqualTo(Vector.create(1, "2"));
      });

      it("has same hashcode as other vector with same source and destination", () -> {
        assertThat(test().vector().hashCode()).isEqualTo(Vector.create(1, "2").hashCode());
      });

      it("is different from another vector with different source or target", () -> {
        assertThat(test().vector()).isNotEqualTo(Vector.create("1", "2"));
        assertThat(test().vector()).isNotEqualTo(Vector.create(1, 2));
        assertThat(test().vector()).isNotEqualTo(Vector.create("1", 2));
      });
    });

  }
}