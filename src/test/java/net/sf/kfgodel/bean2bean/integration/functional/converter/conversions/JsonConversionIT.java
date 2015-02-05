package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

/**
 * This class verifies that when using bean2bean as a Json converter it behaves properly
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class JsonConversionIT extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {

        it("converts a plain domain object to json string");
        it("converts a json string to plain domain object");

        it("converts an array to json string");
        it("converts a json string to an array");

        it("converts a map to json string");
        it("converts a json string to a map");

        it("converts a list to json string");
        it("converts a json string to a list");
    }
}

