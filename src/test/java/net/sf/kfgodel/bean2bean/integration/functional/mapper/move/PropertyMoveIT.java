package net.sf.kfgodel.bean2bean.integration.functional.mapper.move;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.AnnotatedTestSource;
import net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.UnannotatedTestDestination;
import net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.UnannotatedTestSource;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type defines the common test cases for moving properties between existing instances
 * Created by kfgodel on 16/08/14.
 */
@RunWith(JavaSpecRunner.class)
public class PropertyMoveIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        it("moving properties based on type annotations", ()->{
            //Given
            AnnotatedTestSource sourceObject = AnnotatedTestSource.create("Hello");
            UnannotatedTestDestination destination = UnannotatedTestDestination.create();

            //When
            context().b2b().map().propertiesFrom(sourceObject).to(destination);

            // Then
            assertThat(destination.getDestinationProperty()).isEqualTo("Hello");
        });

        it("moving properties based on programmatic mapping", ()->{
            // Given
            context().b2b().map().fromInstanceOf(UnannotatedTestSource.class)
                    .toInstanceOf(UnannotatedTestDestination.class).instructedAs((mappingContext) -> {
                mappingContext.fromProperty("sourceProperty").toProperty("destinationProperty");
            });
            UnannotatedTestSource sourceObject = UnannotatedTestSource.create("Hello");
            UnannotatedTestDestination destination = UnannotatedTestDestination.create();

            //When
            context().b2b().map().propertiesFrom(sourceObject).to(destination);

            //Then
            assertThat(destination.getDestinationProperty()).isEqualTo("Hello");

        });

    }
}
