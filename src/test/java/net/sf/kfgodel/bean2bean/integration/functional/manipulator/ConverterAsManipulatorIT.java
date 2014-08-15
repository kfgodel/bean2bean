package net.sf.kfgodel.bean2bean.integration.functional.manipulator;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import net.sf.kfgodel.bean2bean.impl.B2bApiImpl;
import org.junit.runner.RunWith;

/**
 * This type defines integration test cases for using the converter as a bean manipulator
 * Created by kfgodel on 14/08/14.
 */
@RunWith(JavaSpecRunner.class)
public class ConverterAsManipulatorIT extends JavaSpec<B2bContext> {

    @Override
    public void define() {

        beforeEach(()->{
            // Given A default configured bean2bean type converter
            context().b2b(() -> B2bApiImpl.create());
        });

        it("object creation can be made as a type conversion", ()->{
            //And an object creator mapping configured
//            context().b2b().convert().from(Void.class).into()



            //    When I convert null to a non nullable destination type
            //    Then I should obtain a new object created for the expected type
            //    And the object creator should have been called

        });

        it("object disposal can be made as a conversion");


        it("allows disposal of an object");

//        Scenario: Object elimination as conversion
//        Given A default configured bean2bean type converter
//        And an object eliminator mapping configured
//        When I convert the object to Void type
//        Then I should obtain null as result
//        And the object eliminator should have been called


    }
}
