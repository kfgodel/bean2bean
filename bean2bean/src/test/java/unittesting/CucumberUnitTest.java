package unittesting;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(format = { "pretty", "html:target/cucumber-unit" }, monochrome = true, strict = true)
public class CucumberUnitTest extends AbstractTestNGCucumberTests {

}
