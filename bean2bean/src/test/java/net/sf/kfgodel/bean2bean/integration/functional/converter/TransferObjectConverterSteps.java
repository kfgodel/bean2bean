/**
 * 10/04/2014 00:37:52 Copyright (C) 2014 Darío L. García
 * 
 * <a rel="license" href="http://creativecommons.org/licenses/by/3.0/"><img
 * alt="Creative Commons License" style="border-width:0"
 * src="http://i.creativecommons.org/l/by/3.0/88x31.png" /></a><br />
 * <span xmlns:dct="http://purl.org/dc/terms/" href="http://purl.org/dc/dcmitype/Text"
 * property="dct:title" rel="dct:type">Software</span> by <span
 * xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName">Darío García</span> is
 * licensed under a <a rel="license" href="http://creativecommons.org/licenses/by/3.0/">Creative
 * Commons Attribution 3.0 Unported License</a>.
 */
package net.sf.kfgodel.bean2bean.integration.functional.converter;

import com.google.common.collect.Lists;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.sf.kfgodel.bean2bean.api.B2bApi;
import net.sf.kfgodel.bean2bean.api.impl.B2bApiImpl;
import net.sf.kfgodel.bean2bean.assertions.B2bAssertions;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PersonDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.PhoneNumberDto;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.TypicalPerson;
import net.sf.kfgodel.bean2bean.integration.functional.converter.steps.TypicalPhoneNumber;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.sf.kfgodel.bean2bean.assertions.B2bAssertions.assertThat;

/**
 * Esta clase define los pasos para probar bean2bean como manipulador de TOs (conversor a y desde
 * TOs)
 * 
 * @author D. García
 */
public class TransferObjectConverterSteps {
    private static final Logger LOG = LoggerFactory.getLogger(TransferObjectConverterSteps.class);

    private B2bApi b2b;
    private TypicalPerson domainObject;
    private PersonDto toRepresentation;

    @Given("^A default configured bean2bean type converter$")
	public void a_default_configured_bean_bean_type_converter() throws Throwable {
        b2b = B2bApiImpl.create();
	}

	@Given("^an implicit property name mapper between classes$")
	public void a_configured_mapping_from_a_typical_domain_object_to_its_transfer_object() throws Throwable {
        b2b.configuration().usePropertyNamesAsDefaultMappings();
	}

    @Given("^a typical domain object instance$")
    public void a_domain_object_instance() throws Throwable {
        domainObject = TypicalPerson.create(1L,"Pepe", 23);
        domainObject.addPhoneNumber(TypicalPhoneNumber.create(2L, "+5491164312564"));
        domainObject.addPhoneNumber(TypicalPhoneNumber.create(3L, "+5491164312565"));
    }

    @When("^I convert the domain object to its TO representation$")
	public void i_convert_the_domain_object_to_its_TO_representation() throws Throwable {
        toRepresentation = b2b.convert().from(domainObject).to(PersonDto.class);
	}

	@Then("^I should obtain a TO object with the state of the domain object$")
	public void i_should_obtain_a_TO_object_with_the_state_of_the_domain_object() throws Throwable {
        assertThat(toRepresentation).represents(domainObject);
	}

    @Given("^a TO representation instance$")
    public void a_TO_representation_instance() throws Throwable{
        toRepresentation = PersonDto.create(21L, "UnNombre");
        toRepresentation.addNumber(PhoneNumberDto.create(22L, "asdasd"));
        toRepresentation.addNumber(PhoneNumberDto.create(23L, "asdasd asd"));
    }

	@When("^I convert the TO representation to the domain object$")
	public void i_convert_the_TO_representation_to_the_domain_object() throws Throwable {
        domainObject = b2b.convert().from(toRepresentation).to(TypicalPerson.class);
	}

	@Then("^I should obtain a domain object with the state from the TO$")
	public void i_should_obtain_a_domain_object_with_the_state_from_the_TO() throws Throwable {
        assertThat(domainObject).isARealizationOf(toRepresentation);
	}


}
