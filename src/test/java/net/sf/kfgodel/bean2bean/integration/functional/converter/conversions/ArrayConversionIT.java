package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that bean2bean converts arrays as expected when used as converter
 * Created by kfgodel on 04/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class ArrayConversionIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        it("can convert an array to a list");
        it("can convert an array to a set");
        it("can convert an array to a map");
        it("can convert an array to an array with different element type");
        it("can convert a primitive array to a boxed number array");
        it("can convert a boxed number array to a primitive array");
    }
}
