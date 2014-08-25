package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that b2b can be used toInstanceOf convert objects N toInstanceOf 1, and 1 toInstanceOf N
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class NAryObjectConversionIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        it("can convert zero to one element");
        it("can convert one to zero elements");

        it("can convert zero to multiple element");
        it("can convert multiple to zero elements");

        /**
         * Map() del map/reduce
         */
        it("can convert one to multiple element");
        /**
         * Reduce() del map/reduce
         */
        it("can convert multiple to one elements");

        it("can convert n to m elements");
    }
}
