package unittesting.junit;

public class Hello {
	private final String greeting;

	public Hello(final String greeting) {
		this.greeting = greeting;
	}

	public String sayHi() {
		return greeting + " World";
	}
}