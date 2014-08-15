package net.sf.kfgodel.bean2bean.integration.functional.converter.test_objects;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a typical DTO example for testing purposes
 * Created by kfgodel on 09/07/14.
 */
public class PersonDto {

    private Long id;
    public static final String id_FIELD = "id";

    private String name;
    public static final String name_FIELD = "name";

    private List<PhoneNumberDto> phoneNumbers;
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

    public List<PhoneNumberDto> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<PhoneNumberDto> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public static PersonDto create(Long id, String name){
        PersonDto dto = new PersonDto();
        dto.id = id;
        dto.name = name;
        dto.phoneNumbers = new ArrayList<>();
        return dto;
    }

    public void addNumber(PhoneNumberDto numberDto) {
        this.phoneNumbers.add(numberDto);
    }

    public static PersonDto createWithTestState() {
        PersonDto toRepresentation = PersonDto.create(21L, "UnNombre");
        toRepresentation.addNumber(PhoneNumberDto.create(22L, "asdasd"));
        toRepresentation.addNumber(PhoneNumberDto.create(23L, "asdasd asd"));
        return toRepresentation;
    }
}
