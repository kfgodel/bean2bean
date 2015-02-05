package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

/**
 * This class tests that bean2bean as converter uses the specified decimal format
 * Created by kfgodel on 01/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class DecimalToStringFormatIT extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {

        it("uses default locale format by default");
        it("should use german format if specified");
        it("should use US format if specified");
        it("should use custom format if specified");
    }
}
