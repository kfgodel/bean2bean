package net.sf.kfgodel.bean2bean.integration.functional.mapper.expressions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that expressions in groovy are correctly interpreted as mappings
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class GroovyExpressionsIT extends JavaSpec<B2bContext> {

    @Override
    public void define() {

        it("can be used as getter");
        it("can be used as transformer");
        it("can be used as setter");

        it("can be used as the complete mapping");
    }
}
