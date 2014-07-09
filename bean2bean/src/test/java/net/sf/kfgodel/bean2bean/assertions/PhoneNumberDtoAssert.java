package net.sf.kfgodel.bean2bean.assertions;

import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PersonDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PhoneNumberDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.TypicalPhoneNumber;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.internal.Objects;
import org.assertj.core.util.VisibleForTesting;

/**
 * Created by kfgodel on 09/07/14.
 */
public class PhoneNumberDtoAssert extends AbstractAssert<PhoneNumberDtoAssert, PhoneNumberDto> {

    @VisibleForTesting
    Objects objects = Objects.instance();


    protected PhoneNumberDtoAssert(PhoneNumberDto actual) {
        super(actual, PhoneNumberDtoAssert.class);
    }

    public static PhoneNumberDtoAssert assertThat(PhoneNumberDto actual) {
        return new PhoneNumberDtoAssert(actual);
    }

    public PhoneNumberDtoAssert represents(TypicalPhoneNumber domainObject){
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual.getId()).describedAs(nestedName(PhoneNumberDto.id_FIELD)).isEqualTo(domainObject.getId());
        softly.assertThat(actual.getNumber()).describedAs(nestedName(PhoneNumberDto.number_FIELD)).isEqualTo(domainObject.getNumber());
        softly.assertAll();
        return myself;
    }

    private String nestedName(String propertyName) {
        return AssertDescriptions.getNestedDescription(info, actual, propertyName);
    }

}
