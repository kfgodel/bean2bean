package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that bean2bean as a number toInstanceOf string converter works as expected
 * Created by kfgodel on 01/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class NumberToStringConversionIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        it("converts byte to string");
        it("converts short to string");
        it("converts int to string");
        it("converts long to string");
        it("converts biginteger to string");
        it("converts float to string");
        it("converts double to string");
        it("converts bigDecimal to string");
    }
}
