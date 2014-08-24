package net.sf.kfgodel.bean2bean.integration.functional.mapper.annotations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that when using annotation on a dto the properties are correctly mapped
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class MappingAnnotationsIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        describe("with CopyFrom annotation", ()->{

            it("allows creation of destination instance");


            it("allows population of destination instance");

        });

        describe("with CopyTo annotation", ()->{

            it("allows creation of destination instance");

            it("allows population of destination instance");
        });


        describe("with CopyFromAndTo annotation", ()->{
            it("allows creation of destination instance");

            it("allows population of destination instance");
        });

    }
}
