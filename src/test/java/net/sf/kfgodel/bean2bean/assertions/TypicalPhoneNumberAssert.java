package net.sf.kfgodel.bean2bean.assertions;

import net.sf.kfgodel.bean2bean.integration.functional.converter.test_objects.PhoneNumberDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.test_objects.TypicalPhoneNumber;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.SoftAssertions;

/**
 * This type extends the assertions for TypicalPhoneNumber
 * Created by kfgodel on 12/07/14.
 */
public class TypicalPhoneNumberAssert extends AbstractAssert<TypicalPhoneNumberAssert, TypicalPhoneNumber> {

    protected TypicalPhoneNumberAssert(TypicalPhoneNumber actual) {
        super(actual, TypicalPhoneNumberAssert.class);
    }

    public static TypicalPhoneNumberAssert assertThat(TypicalPhoneNumber actual) {
        return new TypicalPhoneNumberAssert(actual);
    }


    public TypicalPhoneNumberAssert isARealizationOf(PhoneNumberDto dto) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(actual.getId()).describedAs(nestedName(TypicalPhoneNumber.id_FIELD)).isEqualTo(dto.getId());
        softly.assertThat(actual.getNumber()).describedAs(nestedName(TypicalPhoneNumber.number_FIELD)).isEqualTo(dto.getNumber());
        softly.assertAll();
        return myself;
    }

    private String nestedName(String propertyName) {
        return AssertDescriptions.getNestedDescription(info, actual, propertyName);
    }
}
