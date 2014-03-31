package unittesting;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "pretty", "html:target/cucumber-unit" }, monochrome = true, strict = true)
public class CucumberUnitTest {

}
