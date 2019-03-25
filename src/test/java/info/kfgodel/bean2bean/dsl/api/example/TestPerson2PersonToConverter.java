package info.kfgodel.bean2bean.dsl.api.example;

import info.kfgodel.bean2bean.core.api.Bean2beanTask;
import info.kfgodel.bean2bean.dsl.api.B2bDsl;
import info.kfgodel.bean2bean.other.references.TypeRef;

import java.util.List;
import java.util.function.BiFunction;

/**
 * This class serves as an example of manually tailored converter
 * Date: 24/03/19 - 20:57
 */
public class TestPerson2PersonToConverter implements BiFunction<TestPerson, Bean2beanTask, PersonTo> {

  @Override
  public PersonTo apply(TestPerson source, Bean2beanTask task) {
    B2bDsl dsl = task.getDsl();
    PersonTo personTo = dsl.generate().anInstanceOf(PersonTo.class);
    personTo.setName(source.getName());
    personTo.setBirthday(dsl.convert().from(source.getBirthday()).to(String.class));
    personTo.setAddresses(dsl.convert().from(source.getAddresses()).to(new TypeRef<List<AddressTo>>() {}));
    return personTo;
  }

  public static TestPerson2PersonToConverter create() {
    TestPerson2PersonToConverter converter = new TestPerson2PersonToConverter();
    return converter;
  }
}
