package net.sf.kfgodel.bean2bean.integration.functional.converter.test_objects;

import java.util.ArrayList;
import java.util.List;

/**
 * This class serves as an example domain object for testing purposes
 * Created by kfgodel on 09/07/14.
 */
public class TypicalPerson {

    private Long id;
    public static final String id_FIELD = "id";


    private String name;
    public static final String name_FIELD = "name";

    private Integer age;
    public static final String age_FIELD = "age";


    private List<TypicalPhoneNumber> phoneNumbers;
    public static final String phoneNumbers_FIELD = "phoneNumbers";


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<TypicalPhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<TypicalPhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public static TypicalPerson create(Long id, String name, Integer age) {
        TypicalPerson person = new TypicalPerson();
        person.id = id;
        person.age = age;
        person.name = name;
        person.phoneNumbers = new ArrayList<>();
        return person;
    }

    public void addPhoneNumber(TypicalPhoneNumber phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
    }

    public static TypicalPerson createWithTestState() {
        TypicalPerson domainObject = TypicalPerson.create(1L, "Pepe", 23);
        domainObject.addPhoneNumber(TypicalPhoneNumber.create(2L, "+5491164312564"));
        domainObject.addPhoneNumber(TypicalPhoneNumber.create(3L, "+5491164312565"));
        return domainObject;
    }

}
