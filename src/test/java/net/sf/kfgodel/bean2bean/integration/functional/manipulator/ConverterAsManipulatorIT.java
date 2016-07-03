package net.sf.kfgodel.bean2bean.integration.functional.manipulator;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bTestContext;
import net.sf.kfgodel.bean2bean.impl.B2bApiImpl;
import net.sf.kfgodel.bean2bean.integration.functional.converter.test_objects.TypicalPerson;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.sf.kfgodel.bean2bean.assertions.B2bAssertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * This type defines integration test cases for using the converter as a bean manipulator
 * Created by kfgodel on 14/08/14.
 */
@RunWith(JavaSpecRunner.class)
public class ConverterAsManipulatorIT extends JavaSpec<B2bTestContext> {

    @Override
    public void define() {


        beforeEach(()->{
            context().b2b(() -> B2bApiImpl.create());
        });

        it("creation can be expressed as a conversion from void", ()->{
            //Given
            Supplier<TypicalPerson> mockedCreator = mock(Supplier.class);
            Mockito.when(mockedCreator.get()).thenReturn(TypicalPerson.create(1L, "John", 22));

            //And an object creator mapping configured

            //When
            TypicalPerson createdPerson = context().b2b().convert().from(void.class).toInstanceOf(TypicalPerson.class);

            // Then
            assertThat(createdPerson).isNotNull();
            assertThat(createdPerson.getName()).isEqualTo("John");
            verify(mockedCreator).get();
        });

        it("disposal can be expressed as a conversion to void", ()->{
            //Given
            Consumer<TypicalPerson> mockedCreator = mock(Consumer.class);
            TypicalPerson pepe = TypicalPerson.createWithTestState();

//        And an object eliminator mapping configured

            //When
            context().b2b().convert().from(pepe).to(void.class);

            // Then
            verify(mockedCreator).accept(pepe);
        });

    }
}
