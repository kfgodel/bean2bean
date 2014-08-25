package net.sf.kfgodel.bean2bean.integration.functional.manipulator.operations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that b2b can handle different mapping operations
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class MappingOperationIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        it("can be expressed in one custom expression ");
        it("can be expressed as a getter and setter operations");
        it("can be expressed as a getter, transform and setter operations");

   }
}
