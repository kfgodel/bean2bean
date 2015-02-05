package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

/**
 * This class verifies that bean2beas as an enum converter works as expected
 * Created by kfgodel on 03/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class EnumAndPrimitivesConverterIT extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {

        it("converts enums to string");
        it("converts strings to enum");

        it("converts enum to number");
        it("converts number to enum");

        /**
         * Custom dto with id and description
         */
        it("converts enum to dto");
        it("converts dto to enum");


    }
}
