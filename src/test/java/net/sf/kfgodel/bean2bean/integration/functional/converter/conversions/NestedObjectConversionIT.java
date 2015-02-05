package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

/**
 * This class verifies that when converting nested instances bean2bean behaves as expected
 * Created by kfgodel on 04/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class NestedObjectConversionIT extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {
        it("can convert a self referencing object to dto");
        it("can convert a self referencing collection to a dto collection");
        it("can convert a self referencing object in a collection property to a dto");
        it("can convert a circular reference between objects a dtos");
    }
}
