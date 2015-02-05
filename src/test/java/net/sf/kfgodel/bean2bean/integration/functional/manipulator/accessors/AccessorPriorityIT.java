package net.sf.kfgodel.bean2bean.integration.functional.manipulator.accessors;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

/**
 * This class verifies that accessor are prioritized when conflicting
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class AccessorPriorityIT extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {

        it("getter is chosen over field");
        it("setter is chosen over field");
    }
}
