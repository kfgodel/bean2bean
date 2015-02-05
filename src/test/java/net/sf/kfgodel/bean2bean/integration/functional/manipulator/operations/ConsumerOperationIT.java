package net.sf.kfgodel.bean2bean.integration.functional.manipulator.operations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

/**
 * This class verifies that when used as setter b2b behaves correctly
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class ConsumerOperationIT extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {

        describe("a value can be consumed by", ()->{

            it("a field replacing its value");
            it("a setter method");
            it("any one arg method");
            it("any no arg method");
            it("any multiple arg method with the value as first compatible arg");
            it("a property sequence");
            it("a custom expression");
        });
    }
}
