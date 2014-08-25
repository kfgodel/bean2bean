package net.sf.kfgodel.bean2bean.integration.functional.manipulator.creators;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that bean2bean can be used as instance creator
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class CreatorsIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        it("niladic constructor is used by default");

        it("exception is thrown if niladic constructor is not present and no custom creator");

        it("custom creator can be defined for a type hierarchy");
    }
}
