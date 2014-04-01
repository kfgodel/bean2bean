package unittesting;

import org.testng.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class HelloStepdefs {
	private Hello hello;
	private String hi;

	@Given("^I have a hello app with \"([^\"]*)\"$")
	public void I_have_a_hello_app_with(final String greeting) {
		hello = new Hello(greeting);
	}

	@When("^I ask it to say hi$")
	public void I_ask_it_to_say_hi() {
		hi = hello.sayHi();
	}

	@Then("^it should answer with \"([^\"]*)\"$")
	public void it_should_answer_with(final String expectedHi) {
		Assert.assertEquals(expectedHi, hi);
	}
}