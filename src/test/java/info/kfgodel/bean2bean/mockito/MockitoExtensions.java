package info.kfgodel.bean2bean.mockito;

import org.mockito.Mockito;

/**
 * This class adds some commonly used methods to reduce mockito verbosity
 * Date: 23/9/19 - 21:55
 */
public class MockitoExtensions {

  /**
   * Creates a mock instance with the default smart nulls to track undefined references
   * @param expectedType The type of object to mock
   * @param <T> Expected instance type
   * @return THe mocked instance
   */
  public static <T> T mockear(Class<T> expectedType){
    return Mockito.mock(expectedType, Mockito.RETURNS_SMART_NULLS);
  }

  /**
   * This methods is an alieas for mocking instances but declares that is not relevant to the test, and for that reason
   * it should not affect the result
   * @param expectedType Class to indicate type of expected instance
   * @param <T> Type of expected result
   * @return The mocked instance
   */
  public static <T> T anIrrelevant(Class<T> expectedType) {
    return mockear(expectedType);
  }

}
