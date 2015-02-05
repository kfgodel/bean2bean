package net.sf.kfgodel.bean2bean.integration.functional.manipulator.operations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

/**
 * This class verifies that when used as getter bean2bean behaves as expected
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class ProducerOperationIT extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {

        describe("a value can be produced from", ()->{
            it("a field");
            it("a getter method");
            it("any method with return value");
            it("a property sequence");
            it("a custom expression");
        });
    }
}
