package net.sf.kfgodel.bean2bean.integration.functional.mapper.expressions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

/**
 * This class verifies that b2b provides means toInstanceOf express mappings for map/reduce
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class NAryExpressionsIT extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {

        it("allows mapping object properties to list positions");
        it("allows mapping list values to object properties");

        it("allows mapping object properties to map keys and values");
        it("allows mapping map keys and values to object properties");
    }
}
