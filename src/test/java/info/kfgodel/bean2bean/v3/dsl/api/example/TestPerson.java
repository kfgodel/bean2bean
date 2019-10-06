package info.kfgodel.bean2bean.v3.dsl.api.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Example class for testing purposes
 * Date: 24/03/19 - 20:33
 */
public class TestPerson {

  private String name;
  private LocalDate birthday;
  private List<TestAddress> addresses;

  public String getName() {
    return name;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public List<TestAddress> getAddresses() {
    if (addresses == null) {
      addresses = new ArrayList<>();
    }
    return addresses;
  }

  public TestPerson with(TestAddress address){
    this.getAddresses().add(address);
    return this;
  }

  public static TestPerson create(String name, LocalDate birthday) {
    TestPerson person = new TestPerson();
    person.name = name;
    person.birthday = birthday;
    return person;
  }

  @Override
  public String toString() {
    return "TestPerson{" +
      "name='" + name + '\'' +
      '}';
  }
}
