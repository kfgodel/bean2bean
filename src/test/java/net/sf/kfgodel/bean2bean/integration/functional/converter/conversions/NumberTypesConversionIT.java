package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class tests the conversion between numbers using bean2bean as converter.<br>
 *     Type reference from: http://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
 * Created by kfgodel on 01/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class NumberTypesConversionIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        it("converts byte to the other number types", ()->{
            //1b -Number-> 1b
            //   -Long-> 1L
            //etc
        });

        it("converts short to the other number types");
        it("converts int to the other number types");
        it("converts long to the other number types");
        it("converts biginteger to the other number types");
        it("converts float to the other number types");
        it("converts double to the other number types");
        it("converts bigDecimal to the other number types");
    }
}
