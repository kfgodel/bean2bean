/**
 * 31/03/2014 20:16:37 Copyright (C) 2014 Darío L. García
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
package integration;

import org.testng.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 
 * @author D. García
 */
public class HolaMundoSteps {

	private String palabraParaMundo;
	private String expresionArmada;

	@Given("^En espaniol mundo se dice \"([^\"]*)\"$")
	public void En_español_mundo_se_dice(final String arg1) throws Throwable {
		this.palabraParaMundo = arg1;
	}

	@When("^Quiero decir Hello world!$")
	public void Quiero_decir_Hello_world() throws Throwable {
		this.expresionArmada = "Hola " + palabraParaMundo;
	}

	@Then("^Deberia responder con \"([^\"]*)\"$")
	public void Debería_responder_con(final String expresionEsperada) throws Throwable {
		Assert.assertEquals(expresionArmada, expresionEsperada);
	}

}
