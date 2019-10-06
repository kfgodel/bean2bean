package info.kfgodel.bean2bean.v3.dsl.api.example;

import info.kfgodel.bean2bean.v3.core.api.Bean2beanTask;

import java.util.function.BiFunction;

/**
 * This class serves as an example of manually tailored converter
 * Date: 24/03/19 - 20:58
 */
public class TestAddress2AddressToConverter implements BiFunction<TestAddress, Bean2beanTask, AddressTo> {

  @Override
  public AddressTo apply(TestAddress source, Bean2beanTask task) {
    AddressTo addressTo = task.getDsl().generate().anInstanceOf(AddressTo.class);
    addressTo.setCountry(source.getCountry());
    addressTo.setState(source.getState());
    addressTo.setStreet(source.getStreetName());
    addressTo.setNumber(source.getStreetNumber());
    return addressTo;
  }

  public static TestAddress2AddressToConverter create() {
    TestAddress2AddressToConverter converter = new TestAddress2AddressToConverter();
    return converter;
  }

}
