package net.sf.kfgodel.bean2bean.unit.longs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import net.sf.kfgodel.bean2bean.api.exceptions.FailedToConvertException;
import net.sf.kfgodel.bean2bean.impl.repos.primitive.PrimitiveTransfunctionRepoImpl;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class verifies the conversion of the naive string to long conversion
 *
 * Created by kfgodel on 05/07/16.
 */
@RunWith(JavaSpecRunner.class)
public class String2LongTransformerTest extends JavaSpec<B2bTestContext> {
  @Override
  public void define() {
    describe("a string to Long transformer", () -> {
      context().transfunction(()-> PrimitiveTransfunctionRepoImpl.create().fromString().toLong());
      
      it("transforms a string representing a numeric integer, to a Long object",()->{
        Long result = (Long) context().transfunction().apply("3");
        assertThat(result).isEqualTo(3L);
      });

      itThrows(FailedToConvertException.class, "when the string doesn't represent a numeric integer value",()->{
        context().transfunction().apply("invalid");
      }, (e)->{
        assertThat(e).hasMessage("Cannot convert the String [invalid] to Long: Unable to parse it");
      });
      
      itThrows(IllegalArgumentException.class, "when null is used as the input string", ()->{
        context().transfunction().apply(null);
      }, (e)->{
        assertThat(e).hasMessage("This transfuction doesn't support the String[null] as input. There's no default conversion to Long");
      });
    });

  }
}