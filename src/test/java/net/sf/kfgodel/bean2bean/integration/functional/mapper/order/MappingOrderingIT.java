package net.sf.kfgodel.bean2bean.integration.functional.mapper.order;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

/**
 * This class verifies that order can be defined for mapping operations
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class MappingOrderingIT extends JavaSpec<B2bTestContext>{
    @Override
    public void define() {

        describe("with annotation mappings", ()->{
            it("defaults to field declaration order");
            it("can be defined with numeric indices");
        });


        describe("with programmatic mappings", ()->{
            it("defaults to mapping declaration order");
            it("can be defined with numeric indices");
            it("can be defined relatively");
        });

    }
}
