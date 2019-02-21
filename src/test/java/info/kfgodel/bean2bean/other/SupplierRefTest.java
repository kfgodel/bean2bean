package info.kfgodel.bean2bean.other;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.bean2bean.other.references.SupplierRef;
import org.junit.runner.RunWith;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 16/02/19 - 19:56
 */
@RunWith(JavaSpecRunner.class)
public class SupplierRefTest extends JavaSpec<TypeRefTestContext> {
  @Override
  public void define() {
    describe("a supplier reference", () -> {
      test().supplierRef(()-> new SupplierRef<String>(String::new) {});

      it("allows access to the output type argument",()->{
        assertThat(test().supplierRef().getOutputType()).isEqualTo(String.class);
      });

      it("allows access to the supplier",()->{
        Supplier<String> supplier = test().supplierRef().getSupplier();
        String result = supplier.get();
        assertThat(result).isEqualTo("");
      });

    });
  }
}