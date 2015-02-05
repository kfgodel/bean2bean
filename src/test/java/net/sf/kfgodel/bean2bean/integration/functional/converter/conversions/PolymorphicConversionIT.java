package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

/**
 * This class verifies that when bean2bean is used for polymorphic conversion it behaves as expected
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class PolymorphicConversionIT extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {

        describe("no conversion is needed if", ()->{
            it("destination type is same as source type");
            it("destination type is the direct super class");
            it("destination type is an inherited super class");
            it("destination type is a direct interface");
            it("destination type is a inherited interface");
            /**
             * Verifies that an Integer[] instance can be used as Object[]
             */
            it("destination type is an array which element type is a super type of source array element type");
        });
    }
}
