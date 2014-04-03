package integration.testng;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SimpleIntegrationTestIT {

	@BeforeClass
	public void setUp() {
		// code that will be invoked when this test is instantiated
	}

	@Test(groups = { "fast" })
	public void aFastTest() {
		System.out.println("IT Fast test");
	}

	@Test(groups = { "slow" })
	public void aSlowTest() {
		System.out.println("IT Slow test");
	}

}