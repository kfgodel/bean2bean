package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that bean2bean converts Sets as expected when used as converter
 * Created by kfgodel on 04/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class SetConversionIT extends JavaSpec<B2bContext>{
    @Override
    public void define() {

        it("can convert a set to an array");
        it("can convert a set to a list");
        it("can convert a set to a map");
        it("can convert a typed set to a different typed set");
        it("can convert a hashset to a linkedhashset");
        it("can convert a hashset to a treeset");
        it("can convert a treeset to a hashset");
    }
}
