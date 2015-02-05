package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

/**
 * This class verifies that bean2bean convertes maps as expected when used as converter
 * Created by kfgodel on 04/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class MapConversionIT extends JavaSpec<B2bTestContext>{
    @Override
    public void define() {

        it("converts a map to an array");
        it("converts a map to a list");
        it("converts a map to a set");
        it("converts one typed key map to other typed key map");
        it("converts one typed value map to other typed value map");
        it("converts hashmap to treemap");
        it("converts hasmap to a linkedhashmap");
        it("converts a treemap to a hashmap");
    }
}
