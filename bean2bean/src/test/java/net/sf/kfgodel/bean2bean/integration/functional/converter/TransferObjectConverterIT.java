package net.sf.kfgodel.bean2bean.integration.functional.converter;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.Variable;
import net.sf.kfgodel.bean2bean.api.B2bApi;
import net.sf.kfgodel.bean2bean.api.impl.B2bApiImpl;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PersonDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.TypicalPerson;
import org.junit.runner.RunWith;

import static net.sf.kfgodel.bean2bean.assertions.B2bAssertions.assertThat;

/**
 * This type defines the most common test cases for bean2bean as a transfer object converter.<br>
 *
 *  In order to abstract the internal state of a system
 *  As a bean2bean api user
 *  I want to represent a system state as a transfer objects
 *
 * Created by kfgodel on 12/07/14.
 */
@RunWith(JavaSpecRunner.class)
public class TransferObjectConverterIT extends JavaSpec {



    @Override
    public void define() {

        Variable<B2bApi> b2b = Variable.create();

        beforeEach(()->{
            b2b.set(B2bApiImpl.create());
            b2b.get().configuration().usePropertyNamesAsDefaultMappings();
        });

        /**
         *     Given A default configured bean2bean type converter
         *      And an implicit property name mapper between classes
         *      And a typical domain object instance
         *     When I convert the domain object toInstanceOf its TO representation
         *     Then I should obtain a TO object with the state of the domain object
         */
        it("can convert a domain object to Dto using implicit mappings", ()->{
            //Given
            TypicalPerson domainObject = TypicalPerson.createWithTestState();

            //When
            PersonDto toRepresentation = b2b.get().convert().from(domainObject).toInstanceOf(PersonDto.class);

            //Then
            assertThat(toRepresentation).represents(domainObject);
        });


        /**
         *   Given A default configured bean2bean type converter
         *     And an implicit property name mapper between classes
         *     And a TO representation instance
         *   When I convert the TO representation toInstanceOf the domain object
         *   Then I should obtain a domain object with the state from the TO
         */
        it("can convert Dto to domain object using implicit mappings", ()->{
            //Given
            PersonDto toRepresentation = PersonDto.createWithTestState();

            //When
            TypicalPerson domainObject = b2b.get().convert().from(toRepresentation).toInstanceOf(TypicalPerson.class);

            //Then
            assertThat(domainObject).isARealizationOf(toRepresentation);

        });

    }
}
