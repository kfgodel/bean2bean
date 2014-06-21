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
package integration.functional;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Esta clase define los pasos para probar bean2bean como manipulador de TOs (conversor a y desde
 * TOs)
 * 
 * @author D. García
 */
public class TransferObjectManipulatorSteps {

	@Given("^A default configured bean(\\d+)bean type converter$")
	public void a_default_configured_bean_bean_type_converter(final int arg1) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Given("^a configured mapping from a typical domain object to its transfer object$")
	public void a_configured_mapping_from_a_typical_domain_object_to_its_transfer_object() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^I convert the domain object to its TO representation$")
	public void i_convert_the_domain_object_to_its_TO_representation() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^I should obtain a TO object with the state of the domain object$")
	public void i_should_obtain_a_TO_object_with_the_state_of_the_domain_object() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Given("^a configured mapping to a typical domain object from its transfer object$")
	public void a_configured_mapping_to_a_typical_domain_object_from_its_transfer_object() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^I convert the TO representation to the domain object$")
	public void i_convert_the_TO_representation_to_the_domain_object() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^I should obtain a domain object with the state from the TO$")
	public void i_should_obtain_a_domain_object_with_the_state_from_the_TO() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^I convert the circular reference domain object to its TO representation$")
	public void i_convert_the_circular_reference_domain_object_to_its_TO_representation() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^I should obtain a TO object with a circular reference too$")
	public void i_should_obtain_a_TO_object_with_a_circular_reference_too() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

}
