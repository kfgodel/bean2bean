package net.sf.kfgodel.bean2bean.integration.functional.manipulator.accessors;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import org.junit.runner.RunWith;

/**
 * This class verifies that bean2bean can access values with any security modifier
 * Created by kfgodel on 06/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class AnyModifierAccessorIT extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {

        describe("fields", ()->{
            describe("with public modifier", ()->{
                it("can be getted");
                it("can be setted");
            });
            describe("with protected modifier", ()->{
                it("can be getted");
                it("can be setted");
            });
            describe("with private modifier", ()->{
                it("can be getted");
                it("can be setted");
            });
        });

        describe("methods", ()->{
            describe("with public modifier", ()->{
                it("can be getted");
                it("can be setted");
            });
            describe("with protected modifier", ()->{
                it("can be getted");
                it("can be setted");
            });
            describe("with private modifier", ()->{
                it("can be getted");
                it("can be setted");
            });
        });
    }
}
