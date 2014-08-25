package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that bean2bean converts lists as expected when used as converter
 * Created by kfgodel on 04/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class ListConversionIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        it("converts a list to an array");
        it("converts a list to a set");
        it("converts a list to a map");
        it("converts a typed list to a different typed list");
        it("converts an arraylist to a linkedlist");
        it("converts a linkedlist to an arraylist");
    }
}
