package net.sf.kfgodel.bean2bean.integration.functional.converter.conversions;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that bean2bean behaves as expected for Dto conversions
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class DtoConversionIT extends JavaSpec<B2bContext>{
    @Override
    public void define() {

        it("allows conversion from plain domain object to dto");
        it("allows conversion from dto to plain domain object");

        it("allows conversion from nested domain object to nested dto");
        it("allows conversion from nested dto to nested domain object");
    }
}


