package net.sf.kfgodel.bean2bean.assertions;

import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PersonDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PhoneNumberDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.TypicalPerson;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.TypicalPhoneNumber;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.internal.Iterables;
import org.assertj.core.internal.Objects;
import org.assertj.core.util.VisibleForTesting;

import java.util.List;

/**
 * This type defines custom assertions for person dtos tests
 * Created by kfgodel on 09/07/14.
 */
public class PersonDtoAssert extends AbstractAssert<PersonDtoAssert, PersonDto> {

    @VisibleForTesting
    Objects objects = Objects.instance();

    @VisibleForTesting
    Iterables iterables = Iterables.instance();


    // 2 - Write a constructor toInstanceOf build your assertion class with the object you want make assertions on.
    public PersonDtoAssert(PersonDto actual) {
        super(actual, PersonDtoAssert.class);
    }

    // 3 - A fluent entry point toInstanceOf your specific assertion class, use it with static import.
    public static PersonDtoAssert assertThat(PersonDto actual) {
        return new PersonDtoAssert(actual);
    }

    public PersonDtoAssert represents(TypicalPerson domainObject){
        isNotNull();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual.getId()).describedAs(nestedName(PersonDto.id_FIELD)).isEqualTo(domainObject.getId());
        softly.assertThat(actual.getName()).describedAs(nestedName(PersonDto.name_FIELD)).isEqualTo(domainObject.getName());

        List<PhoneNumberDto> dtoNumbers = actual.getPhoneNumbers();
        List<TypicalPhoneNumber> domainNumbers = domainObject.getPhoneNumbers();
        softly.assertThat(dtoNumbers).describedAs(nestedName(PersonDto.phoneNumbers_FIELD)).hasSameSizeAs(domainNumbers);
        softly.assertAll();

        for (int i = 0; i < dtoNumbers.size(); i++) {
            PhoneNumberDto numberDto = dtoNumbers.get(i);
            TypicalPhoneNumber domainNumber = domainNumbers.get(i);
            B2bAssertions.assertThat(numberDto).describedAs(nestedName(PersonDto.phoneNumbers_FIELD) + "[" + i + "]").represents(domainNumber);
        }
        return myself;
    }

    private String nestedName(String propertyName) {
        return AssertDescriptions.getNestedDescription(info, actual, propertyName);
    }

}
