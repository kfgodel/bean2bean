package net.sf.kfgodel.bean2bean.integration.functional.converter.steps;

/**
 * This class serves as example for testing purposes
 * Created by kfgodel on 09/07/14.
 */
public class TypicalPhoneNumber {

    private Long id;

    private String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public static TypicalPhoneNumber create(Long id, String number) {
        TypicalPhoneNumber phoneNumber = new TypicalPhoneNumber();
        phoneNumber.id = id;
        phoneNumber.number = number;
        return phoneNumber;
    }
}
