package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verfies that bean2bean used as converter with null values behaves as expected
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class NullConversionIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {
        it("generates null when converting null to String");
        it("generates the string 'null' when converting null to a non null String");
        it("generates null when converting null to a object");
    }
}
