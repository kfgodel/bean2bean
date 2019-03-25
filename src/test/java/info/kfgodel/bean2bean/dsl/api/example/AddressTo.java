package info.kfgodel.bean2bean.dsl.api.example;

/**
 * Date: 24/03/19 - 20:51
 */
public class AddressTo {

  private String streetName;
  private Integer streetNumber;
  private String state;
  private String country;

  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public Integer getStreetNumber() {
    return streetNumber;
  }

  public void setStreetNumber(Integer streetNumber) {
    this.streetNumber = streetNumber;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}
