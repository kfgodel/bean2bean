package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that bean2bean as a string toInstanceOf number converter works as expected
 * Created by kfgodel on 01/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class StringToNumbersConversionIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        it("converts a string to byte");
        it("converts a string to short");
        it("converts a string to int");
        it("converts a string to long");
        it("converts a string to biginteger");
        it("converts a string to float");
        it("converts a string to double");
        it("converts a string to bigDecimal");
    }
}
