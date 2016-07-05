package net.sf.kfgodel.bean2bean.unit.longs;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import net.sf.kfgodel.bean2bean.api.exceptions.Bean2beanException;
import net.sf.kfgodel.bean2bean.impl.transfunctions.DeltaImpl;
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
    describe("a string to long transformer", () -> {
      context().transfunction(()-> DeltaImpl.create().fromString().toLong());
      
      it("transforms a string valur representing a long, to a long value",()->{
        Object result = context().transfunction().apply("3");
        assertThat(result).isEqualTo(3L);
      });

      itThrows(Bean2beanException.class, "when the string doesn't represent a long value",()->{
        context().transfunction().apply("invalid");
      }, (e)->{
        assertThat(e).hasMessage("Cannot convert the String [invalid] to Long: Unable to parse it");
      });
    });

  }
}