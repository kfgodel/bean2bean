package info.kfgodel.bean2bean.dsl.api.example;

/**
 * Example class for testing purposes
 * Date: 24/03/19 - 20:35
 */
public class TestAddress {

  private String streetName;
  private int streetNumber;
  private String state;
  private String country;

  public String getStreetName() {
    return streetName;
  }

  public String getState() {
    return state;
  }

  public String getCountry() {
    return country;
  }

  public int getStreetNumber() {
    return streetNumber;
  }

  public static TestAddress create(String street, int number, String state, String country) {
    TestAddress address = new TestAddress();
    address.streetName = street;
    address.streetNumber = number;
    address.state = state;
    address.country = country;
    return address;
  }

}
