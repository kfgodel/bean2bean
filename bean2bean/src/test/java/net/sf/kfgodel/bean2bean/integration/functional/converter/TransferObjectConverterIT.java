package net.sf.kfgodel.bean2bean.integration.functional.converter;

import net.sf.kfgodel.bean2bean.api.B2bApi;
import net.sf.kfgodel.bean2bean.api.impl.B2bApiImpl;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PersonDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.TypicalPerson;
import org.junit.Before;
import org.junit.Test;

import static net.sf.kfgodel.bean2bean.assertions.B2bAssertions.assertThat;

/**
 * This type defines the most common test cases for bean2bean as a transfer object converter
 * Created by kfgodel on 12/07/14.
 */
public class TransferObjectConverterIT {


    private B2bApi b2b;

    @Before
    public void createB2bWithImplicitMappings(){
        b2b = B2bApiImpl.create();
        b2b.configuration().usePropertyNamesAsDefaultMappings();
    }

    /**
     *     Given A default configured bean2bean type converter
     *      And an implicit property name mapper between classes
     *      And a typical domain object instance
     *     When I convert the domain object toInstanceOf its TO representation
     *     Then I should obtain a TO object with the state of the domain object
     */
    @Test
    public void itShouldConvertADomainObjectToDtoUsingImplicitMappings(){
        //Given
        TypicalPerson domainObject = TypicalPerson.createWithTestState();

        //When
        PersonDto toRepresentation = b2b.convert().from(domainObject).toInstanceOf(PersonDto.class);

        //Then
        assertThat(toRepresentation).represents(domainObject);
    }

    /**
     *   Given A default configured bean2bean type converter
     *     And an implicit property name mapper between classes
     *     And a TO representation instance
     *   When I convert the TO representation toInstanceOf the domain object
     *   Then I should obtain a domain object with the state from the TO
     */
    @Test
    public void itShouldConvertDtoToDomainObjectUsingImplicitMappings() {
        //Given
        PersonDto toRepresentation = PersonDto.createWithTestState();

        //When
        TypicalPerson domainObject = b2b.convert().from(toRepresentation).toInstanceOf(TypicalPerson.class);

        //Then
        assertThat(domainObject).isARealizationOf(toRepresentation);
    }
}
