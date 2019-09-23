package info.kfgodel.bean2bean.v3.dsl.api.example;

import java.util.List;

/**
 * This class represents a transfor object of the {@link TestPerson}
 * Date: 24/03/19 - 20:45
 */
public class PersonTo {

  private String name;
  private String birthday;
  private List<AddressTo> addresses;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public List<AddressTo> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<AddressTo> addresses) {
    this.addresses = addresses;
  }
}
