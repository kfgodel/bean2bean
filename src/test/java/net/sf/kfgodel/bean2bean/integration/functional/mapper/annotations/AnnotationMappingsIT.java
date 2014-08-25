package net.sf.kfgodel.bean2bean.integration.functional.mapper.annotations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This class verifies that annotation mappings offer the expected features
 * Created by kfgodel on 09/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class AnnotationMappingsIT  extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        describe("with CopyFrom annotation", ()->{
            describe("if no property name indicated", ()->{
                it("value is taken from annotated property and assigned to destination with same name");
            });

            describe("using custom expression", ()->{
                it("as getter");
                it("as setter");
                it("as transformer");
                it("as the complete mapping");
            });
            it("allows explicit indication of destination type");
        });

        describe("with CopyTo annotation", ()->{
            describe("if no property name indicated", ()->{
                it("value is taken from property with same name, and assigned to the annotated property");
            });
            describe("using custom expression", ()->{
                it("as getter");
                it("as setter");
                it("as transformer");
                it("as the complete mapping");
            });
            it("allows explicit indication of destination type");
        });

        describe("with CopyFromAndTo annotation", ()->{
            describe("if no property name indicated", ()->{
                describe("when copied from annotated source", ()->{
                    it("value is taken from annotated property and assigned to destination with same name");
                });
                describe("when copied to annotated destination", ()->{
                    it("value is taken from property with same name, and assigned to the annotated property");
                });
            });
            describe("using custom expression", ()->{
                it("as getter");
                it("as setter");
                it("as transformer");
                it("as the complete mapping");
            });
            it("allows explicit indication of destination type");
        });

        describe("with the 3 annotations", ()->{
            it("allows indication of interpreter as text");
            it("allows extension annotations");
            it("allows indication of null treatment in property sequences");
        });

    }
}
