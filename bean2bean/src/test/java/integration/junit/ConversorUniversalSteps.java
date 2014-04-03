/**
 * 31/03/2014 23:39:25 Copyright (C) 2014 Darío L. García
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
package integration.junit;

import org.testng.Assert;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * 
 * @author D. García
 */
public class ConversorUniversalSteps {

	private String convertido;

	@Given("^Un conversor ya setupeado$")
	public void un_conversor_ya_setupeado() throws Throwable {
	}

	@Given("^mapeo de TO definido$")
	public void mapeo_de_TO_definido() throws Throwable {

	}

	@When("^Convierto el numero entero (-?\\d+) en String$")
	public void convierto_el_numero_entero_en_String(final int numeroIngresado) throws Throwable {
		this.convertido = "" + numeroIngresado;
	}

	@When("^Convierto el numero decimal (-?\\d*\\.?\\d*) en String$")
	public void convierto_el_numero_decimal_en_String(final double numeroIngresado) throws Throwable {
		this.convertido = "" + numeroIngresado;
	}

	@When("^Convierto el objeto tipico en TO$")
	public void convierto_el_objeto_tipico_en_TO() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@When("^Convierto el la lista de objetos tipicos en array de Tos$")
	public void convierto_el_la_lista_de_objetos_tipicos_en_array_de_Tos() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^Deberia obtener el valor \"(.*?)\"$")
	public void deberia_obtener_el_valor(final String textoEsperado) throws Throwable {
		Assert.assertEquals(textoEsperado, convertido);
	}

	@Then("^Deberia obtener el TO con los valores populados$")
	public void deberia_obtener_el_TO_con_los_valores_populados() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

	@Then("^Deberia obtener el array de TOs con los valores populados por cada elemento$")
	public void deberia_obtener_el_array_de_TOs_con_los_valores_populados_por_cada_elemento() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		throw new PendingException();
	}

}
