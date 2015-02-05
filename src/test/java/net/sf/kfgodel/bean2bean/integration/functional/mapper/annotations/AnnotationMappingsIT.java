package net.sf.kfgodel.bean2bean.integration.functional.mapper.annotations;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import net.sf.kfgodel.bean2bean.impl.B2bApiImpl;
import net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.mappings.*;
import org.junit.runner.RunWith;

import static net.sf.kfgodel.bean2bean.assertions.B2bAssertions.assertThat;

/**
 * This class verifies that annotation mappings offer the expected features
 * Created by kfgodel on 09/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class AnnotationMappingsIT  extends JavaSpec<B2bTestContext> {
    @Override
    public void define() {

        beforeEach(()->{
            context().b2b(() -> B2bApiImpl.create());
        });

        describe("with CopyFrom annotation", ()->{
            describe("if no property name indicated", ()->{
                it("value is taken from annotated property and assigned to destination with same name", ()->{
                    // Given
                    CopyFromExampleSource sourceObject = CopyFromExampleSource.create();
                    // When
                    CopyFromExample converted = context().b2b().convert().from(sourceObject).toInstanceOf(CopyFromExample.class);
                    // Then
                    assertThat(converted.getSameName()).isEqualTo(sourceObject.getSameName());
                });
            });
            describe("using native interpreter", () -> {
                it("a single property can be indicated as source", ()->{
                    // Given
                    CopyFromExampleSource sourceObject = CopyFromExampleSource.create();
                    // When
                    CopyFromExample converted = context().b2b().convert().from(sourceObject).toInstanceOf(CopyFromExample.class);
                    // Then
                    assertThat(converted.getSingleProperty()).isEqualTo(sourceObject.getOtherProperty());
                });
                it("a property sequence can be indicated as source", ()->{
                    // Given
                    CopyFromExampleSource sourceObject = CopyFromExampleSource.create();
                    // When
                    CopyFromExample converted = context().b2b().convert().from(sourceObject).toInstanceOf(CopyFromExample.class);
                    // Then
                    assertThat(converted.getPropertySequence()).isEqualTo(sourceObject.getOtherObject().getOtherProperty());
                });
            });
        });

        describe("with MappedAsDestination annotation", ()->{
            describe("using custom expression", ()->{
                it("as getter", ()->{
                    // Given
                    MappedAsDestinationExampleSource sourceObject = MappedAsDestinationExampleSource.create();
                    // When
                    MappedAsDestinationExample converted = context().b2b().convert().from(sourceObject).toInstanceOf(MappedAsDestinationExample.class);
                    // Then
                    assertThat(converted.getCustomGetter()).isEqualTo("true");
                });
                it("as setter", ()->{
                    // Given
                    MappedAsDestinationExampleSource sourceObject = MappedAsDestinationExampleSource.create();
                    // When
                    MappedAsDestinationExample converted = context().b2b().convert().from(sourceObject).toInstanceOf(MappedAsDestinationExample.class);
                    // Then
                    assertThat(converted.getDerivatedProperty()).isEqualTo("Picnic");
                });
                it("as transformer", ()->{
                    // Given
                    MappedAsDestinationExampleSource sourceObject = MappedAsDestinationExampleSource.create();
                    // When
                    MappedAsDestinationExample converted = context().b2b().convert().from(sourceObject).toInstanceOf(MappedAsDestinationExample.class);
                    // Then
                    assertThat(converted.getCustomTransformer()).isEqualTo("1");
                });
            });
        });


        describe("with CopyTo annotation", ()->{
            describe("if no property name indicated", ()->{
                it("value is taken from property with same name, and assigned to the annotated property", ()->{
                    // Given
                    CopyToExample sourceObject = CopyToExample.create();
                    // When
                    CopyToExampleDestination converted = context().b2b().convert().from(sourceObject).toInstanceOf(CopyToExampleDestination.class);
                    // Then
                    assertThat(converted.getSameName()).isEqualTo(sourceObject.getSameName());
                });
            });

            describe("using native interpreter", () -> {
                it("a single property can be indicated as destination", () -> {
                    // Given
                    CopyToExample sourceObject = CopyToExample.create();
                    // When
                    CopyToExampleDestination converted = context().b2b().convert().from(sourceObject).toInstanceOf(CopyToExampleDestination.class);
                    // Then
                    assertThat(converted.getOtherProperty()).isEqualTo(sourceObject.getSingleProperty());
                });
                it("a property sequence can be indicated as destination", () -> {
                    // Given
                    CopyToExample sourceObject = CopyToExample.create();
                    // When
                    CopyToExampleDestination converted = context().b2b().convert().from(sourceObject).toInstanceOf(CopyToExampleDestination.class);
                    // Then
                    assertThat(converted.getOtherObject().getOtherProperty()).isEqualTo(sourceObject.getPropertySequence());
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

        describe("with CopyBidi annotation", ()->{
            describe("if no property name indicated", ()->{
                describe("when copied from annotated source", ()->{
                    it("value is taken from annotated property and assigned to destination with same name", ()->{
                        // Given
                        CopyBidiExample sourceObject = CopyBidiExample.create();
                        // When
                        CopyToExampleDestination converted = context().b2b().convert().from(sourceObject).toInstanceOf(CopyToExampleDestination.class);
                        // Then
                        assertThat(converted.getSameName()).isEqualTo(sourceObject.getSameName());
                    });
                    describe("using native interpreter", () -> {
                        it("a single property can be indicated as destination", () -> {
                            // Given
                            CopyBidiExample sourceObject = CopyBidiExample.create();
                            // When
                            CopyToExampleDestination converted = context().b2b().convert().from(sourceObject).toInstanceOf(CopyToExampleDestination.class);
                            // Then
                            assertThat(converted.getOtherProperty()).isEqualTo(sourceObject.getSingleProperty());
                        });
                        it("a property sequence can be indicated as destination", () -> {
                            // Given
                            CopyBidiExample sourceObject = CopyBidiExample.create();
                            // When
                            CopyToExampleDestination converted = context().b2b().convert().from(sourceObject).toInstanceOf(CopyToExampleDestination.class);
                            // Then
                            assertThat(converted.getOtherObject().getOtherProperty()).isEqualTo(sourceObject.getPropertySequence());
                        });
                    });
                });
                describe("when copied to annotated destination", ()->{
                    it("value is taken from property with same name, and assigned to the annotated property", ()->{
                        // Given
                        CopyFromExampleSource sourceObject = CopyFromExampleSource.create();
                        // When
                        CopyBidiExample converted = context().b2b().convert().from(sourceObject).toInstanceOf(CopyBidiExample.class);
                        // Then
                        assertThat(converted.getSameName()).isEqualTo(sourceObject.getSameName());
                    });

                    describe("using native interpreter", () -> {
                        it("a single property can be indicated as source", ()->{
                            // Given
                            CopyFromExampleSource sourceObject = CopyFromExampleSource.create();
                            // When
                            CopyBidiExample converted = context().b2b().convert().from(sourceObject).toInstanceOf(CopyBidiExample.class);
                            // Then
                            assertThat(converted.getSingleProperty()).isEqualTo(sourceObject.getOtherProperty());
                        });
                        it("a property sequence can be indicated as source", ()->{
                            // Given
                            CopyFromExampleSource sourceObject = CopyFromExampleSource.create();
                            // When
                            CopyBidiExample converted = context().b2b().convert().from(sourceObject).toInstanceOf(CopyBidiExample.class);
                            // Then
                            assertThat(converted.getPropertySequence()).isEqualTo(sourceObject.getOtherObject().getOtherProperty());
                        });
                    });
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
