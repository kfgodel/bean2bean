package net.sf.kfgodel.bean2bean.integration.functional.manipulator.accessors;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.runner.RunWith;

/**
 * This class verifies that bean2bean behaves correctly when null instances are found on accessors
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class NullInPropertySequenceIT extends JavaSpec<B2bContext> {

    @Override
    public void define() {

        describe("when producing a getter result", () -> {

            describe("if null found before last property", ()->{
                it("can produce null as result");
                it("can create missing instances");
                it("can throw an exception");
            });

            describe("if found on last property", ()->{
                it("can produce null as result");
                it("can create the missing instance");
                it("can throw an exception");
            });
        });

        describe("when consuming as setter arg", ()-> {

            describe("if null found before last property", () -> {
                it("can stop consumption");
                it("can create missing instances");
                it("can throw an exception");
            });

            describe("if found on last property", () -> {
                it("can replace null with value");
            });
        });

    }

}
