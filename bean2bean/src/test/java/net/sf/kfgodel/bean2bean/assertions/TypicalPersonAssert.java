package net.sf.kfgodel.bean2bean.assertions;

import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PersonDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PhoneNumberDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.TypicalPerson;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.TypicalPhoneNumber;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.SoftAssertions;

import java.util.List;

/**
 * This class adds custom assertion toInstanceOf test conversion
 * Created by kfgodel on 12/07/14.
 */
public class TypicalPersonAssert extends AbstractAssert<TypicalPersonAssert, TypicalPerson> {

    protected TypicalPersonAssert(TypicalPerson actual) {
        super(actual, TypicalPersonAssert.class);
    }

    public static TypicalPersonAssert assertThat(TypicalPerson actual) {
        return new TypicalPersonAssert(actual);
    }

    public TypicalPersonAssert isARealizationOf(PersonDto dto) {
        isNotNull();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual.getId()).describedAs(nestedName(TypicalPerson.id_FIELD)).isEqualTo(dto.getId());
        softly.assertThat(actual.getName()).describedAs(nestedName(TypicalPerson.name_FIELD)).isEqualTo(dto.getName());

        List<TypicalPhoneNumber> dtoNumbers = actual.getPhoneNumbers();
        List<PhoneNumberDto> domainNumbers = dto.getPhoneNumbers();
        softly.assertThat(dtoNumbers).describedAs(nestedName(PersonDto.phoneNumbers_FIELD)).hasSameSizeAs(domainNumbers);
        softly.assertAll();

        for (int i = 0; i < dtoNumbers.size(); i++) {
            TypicalPhoneNumber domainNumber = dtoNumbers.get(i);
            PhoneNumberDto numberDto = domainNumbers.get(i);
            B2bAssertions.assertThat(domainNumber).describedAs(nestedName(TypicalPerson.phoneNumbers_FIELD) + "[" + i + "]").isARealizationOf(numberDto);
        }
        return myself;
    }

    private String nestedName(String propertyName) {
        return AssertDescriptions.getNestedDescription(info, actual, propertyName);
    }
}
