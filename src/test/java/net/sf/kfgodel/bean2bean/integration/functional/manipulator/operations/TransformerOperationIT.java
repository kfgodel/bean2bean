package net.sf.kfgodel.bean2bean.integration.functional.manipulator.operations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that b2b behaves correctly when used as transformer
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class TransformerOperationIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        it("is automatically handled as conversion by default");
        it("can be processed by one arg with result method");
        it("can be done with a custom expression");
    }
}
