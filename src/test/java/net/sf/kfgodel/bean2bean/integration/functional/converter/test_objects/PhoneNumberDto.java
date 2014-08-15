package net.sf.kfgodel.bean2bean.integration.functional.converter.test_objects;

/**
 * This class represents a example DTo for phone numbers
 * Created by kfgodel on 09/07/14.
 */
public class PhoneNumberDto {
    private Long id;
    public static final String id_FIELD = "id";
    private String number;
    public static final String number_FIELD = "number";


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

    public static PhoneNumberDto create(Long id, String number){
        PhoneNumberDto dto = new PhoneNumberDto();
        dto.id = id;
        dto.number = number;
        return dto;
    }
}
