package net.sf.kfgodel.bean2bean.integration.functional.mapper;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import net.sf.kfgodel.bean2bean.impl.B2bApiImpl;
import net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.UnannotatedTestDestination;
import net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.UnannotatedTestSource;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This type describe common test case scenarios for b2b as programmatic based object mapper
 *
 *   In order to move state from source object to destination object
 *   As a bean2bean api user
 *   I want to be able to map properties between objects using programmatic declarations
 *
 * Created by kfgodel on 16/08/14.
 */
@RunWith(JavaSpecRunner.class)
public class ProgrammaticBasedMapperIT extends JavaSpec<B2bContext> {
    @Override
    public void define() {

        beforeEach(()->{
            context().b2b(() -> B2bApiImpl.create());
        });

        it("allows mapping properties between two known types explicitly", ()-> {
            // Given
            context().b2b().map().from(UnannotatedTestSource.class).to(UnannotatedTestDestination.class).with((mappingContext)->{
                UnannotatedTestSource source = mappingContext.getSourceObject();
                UnannotatedTestDestination destination = mappingContext.getDestinationObject();

                String sourcePropertyValue = source.getSourceProperty();
                destination.setDestinationProperty(sourcePropertyValue);
            });

            //When
            UnannotatedTestSource sourceObject = UnannotatedTestSource.create("Hello");
            UnannotatedTestDestination destination = context().b2b().convert().from(sourceObject).toInstanceOf(UnannotatedTestDestination.class);

            //Then
            assertThat(destination.getDestinationProperty()).isEqualTo("Hello");
        });

        it("allows mapping from a known source type", ()->{
            // Given
            context().b2b().map().from(UnannotatedTestSource.class).to(Object.class).with((mappingContext)->{
                UnannotatedTestSource source = mappingContext.getSourceObject();
                Object destination = mappingContext.getDestinationObject();

                String sourcePropertyValue = source.getSourceProperty();
                mappingContext.b2b().manipulate().from(destination).property("destinationProperty")
                        .andSetItsValueTo(sourcePropertyValue);
            });

            //When
            UnannotatedTestSource sourceObject = UnannotatedTestSource.create("Hello");
            UnannotatedTestDestination destination = context().b2b().convert().from(sourceObject).toInstanceOf(UnannotatedTestDestination.class);

            //Then
            assertThat(destination.getDestinationProperty()).isEqualTo("Hello");
        });

        it("allows mapping to a known destination type", ()-> {
            // Given
            context().b2b().map().from(Object.class).to(UnannotatedTestDestination.class).with((mappingContext)->{
                Object source = mappingContext.getSourceObject();
                UnannotatedTestDestination destination = mappingContext.getDestinationObject();

                String sourcePropertyValue = mappingContext.b2b().manipulate().from(source).property("sourceProperty").andGetItsValue();
                destination.setDestinationProperty(sourcePropertyValue);
            });

            //When
            UnannotatedTestSource sourceObject = UnannotatedTestSource.create("Hello");
            UnannotatedTestDestination destination = context().b2b().convert().from(sourceObject).toInstanceOf(UnannotatedTestDestination.class);

            //Then
            assertThat(destination.getDestinationProperty()).isEqualTo("Hello");
        });

        it("allows mapping between unknown types", ()->{
            // Given
            context().b2b().map().from(Object.class).to(Object.class).with((mappingContext)->{
                Object source = mappingContext.getSourceObject();
                Object destination = mappingContext.getDestinationObject();

                String sourcePropertyValue = mappingContext.b2b().manipulate().from(source).property("sourceProperty").andGetItsValue();
                mappingContext.b2b().manipulate().from(destination).property("destinationProperty");
            });

            //When
            UnannotatedTestSource sourceObject = UnannotatedTestSource.create("Hello");
            UnannotatedTestDestination destination = context().b2b().convert().from(sourceObject).toInstanceOf(UnannotatedTestDestination.class);

            //Then
            assertThat(destination.getDestinationProperty()).isEqualTo("Hello");
        });
    }
}
