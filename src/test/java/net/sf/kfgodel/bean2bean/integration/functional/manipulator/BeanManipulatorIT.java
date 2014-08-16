package net.sf.kfgodel.bean2bean.integration.functional.manipulator;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import net.sf.kfgodel.bean2bean.B2bContext;
import net.sf.kfgodel.bean2bean.impl.B2bApiImpl;
import net.sf.kfgodel.bean2bean.integration.functional.converter.test_objects.TypicalPerson;
import org.junit.runner.RunWith;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 *  This test describes common manipulater tests cases that serve as integration tests
 *
 *      In order to change part of the state of a system
 *      As a bean2bean api user
 *      I want to be able to manipulate objects and properties

 * Created by kfgodel on 14/08/14.
 */
@RunWith(JavaSpecRunner.class)
public class BeanManipulatorIT extends JavaSpec<B2bContext> {

    @Override
    public void define() {

        beforeEach(()->{
            context().b2b(() -> B2bApiImpl.create());
        });


        it("allows creation of an object", ()->{
            // Given a string creator configured
            Supplier<String> mockedCreator = mock(Supplier.class);
            context().b2b().manipulate().creationOf(String.class).with(mockedCreator);
            when(mockedCreator.get()).thenReturn("Hello!");

            // When
            String created = context().b2b().manipulate().createInstanceOf(String.class);

            // Then
            assertThat(created).isEqualTo("Hello!");
            verify(mockedCreator).get();
        });

        it("allows disposal of an object", ()->{
            // Given a string disposer configured
            Consumer<String> mockedDisposer = mock(Consumer.class);
            context().b2b().manipulate().disposalOf(String.class).with(mockedDisposer);

            // When
            String disposable = "delete me please!";
            context().b2b().manipulate().dispose(disposable);

            // Then it should have been called
            verify(mockedDisposer).accept(disposable);
        });

        it("allows getting the property value of an object", ()->{
            // Given
            TypicalPerson pepe = TypicalPerson.createWithTestState();

            // When
            String nameValue = context().b2b().manipulate().from(pepe).property("name").andGetItsValue();

            // Then
            assertThat(nameValue).isEqualTo("Pepe");
        });

        it("allows setting the property value of an object",()->{
            // Given
            TypicalPerson pepe = TypicalPerson.createWithTestState();

            // When
            context().b2b().manipulate().from(pepe).property("name").andSetItsValueTo("Juan");

            // Then
            assertThat(pepe.getName()).isEqualTo("Juan");
        });
    }
}
