package net.sf.kfgodel.bean2bean.integration.functional.mapper;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import net.sf.kfgodel.bean2bean.impl.B2bApiImpl;
import net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.AnnotatedTestDestination;
import net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.AnnotatedTestSource;
import net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.UnannotatedTestDestination;
import net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.UnannotatedTestSource;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type describe common test case scenarios for b2b as an annotation based object mapper
 *
 *   In order to move state from source object to destination object
 *   As a bean2bean api user
 *   I want to be able to map properties between objects using annotations
 *
 * Created by kfgodel on 16/08/14.
 */
@RunWith(JavaSpecRunner.class)
public class AnnotationBasedMapperIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        beforeEach(()->{
            context().b2b(() -> B2bApiImpl.create());
        });

        it("annotations can be used on the source object", ()->{
            //Given
            AnnotatedTestSource sourceObject = AnnotatedTestSource.create();

//        And an annotation mapped source object

            //When
            UnannotatedTestDestination destination = context().b2b().convert().from(sourceObject).toInstanceOf(UnannotatedTestDestination.class);

            //Then
            assertThat(destination.getDestinationProperty()).isEqualTo(sourceObject.getSourceProperty());
        });

        it("annotations can be used on the destination object", ()->{
            //Given
            UnannotatedTestSource sourceObject = UnannotatedTestSource.create();

//        And an annotation mapped destination object

            //When
            AnnotatedTestDestination destination = context().b2b().convert().from(sourceObject).toInstanceOf(AnnotatedTestDestination.class);

            //Then
            assertThat(destination.getDestinationProperty()).isEqualTo(sourceObject.getSourceProperty());
        });

        it("if used on both then...");

//        Scenario: Mapping with annotations on the source object
//        Given A default configured bean2bean type converter
//        And an annotation mapped source object
//        When I convert the source object to destination object
//        Then I should obtain the desired state on the destination
//
//        Scenario: Mapping with annotations on the destination object
//        Given A default configured bean2bean type converter
//        When I convert the source object to destination object
//        Then I should obtain the desired state on the destination




    }
}
