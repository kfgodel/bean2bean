package net.sf.kfgodel.bean2bean.assertions;

import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PersonDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PhoneNumberDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.TypicalPerson;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.TypicalPhoneNumber;
import org.assertj.core.api.Assertions;

/**
 * This type extends the assertions from assertJ. As indicated in: http://joel-costigliola.github.io/assertj/assertj-core-custom-assertions.html
 *
 * Created by kfgodel on 09/07/14.
 */
public class B2bAssertions extends Assertions {

    /**
     * Starts an assertion construct for PersonDto objects
     * @param actual Dto toInstanceOf evaluate
     * @return The partial assertion object
     */
    public static PersonDtoAssert assertThat(PersonDto actual) {
        return PersonDtoAssert.assertThat(actual);
    }

    /**
     * Starts an assertion construct for PhoneNumberDto objects
     * @param actual Dto toInstanceOf evaluate
     * @return The partial assertion object
     */
    public static PhoneNumberDtoAssert assertThat(PhoneNumberDto actual) {
        return PhoneNumberDtoAssert.assertThat(actual);
    }

    /**
     * Starts an assertion construct for TypicalPerson objects
     * @param actual The domain object toInstanceOf test
     * @return The partial assertion
     */
    public static TypicalPersonAssert assertThat(TypicalPerson actual) {
        return TypicalPersonAssert.assertThat(actual);
    }

    /**
     * Starts an assertion construct for TypicalPhoneNumber objects
     * @param actual The domain object toInstanceOf assert
     * @return The partial assertion
     */
    public static TypicalPhoneNumberAssert assertThat(TypicalPhoneNumber actual) {
        return TypicalPhoneNumberAssert.assertThat(actual);
    }

}
