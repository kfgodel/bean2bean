package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.mappings;

import net.sf.kfgodel.bean2bean.api.annotations.CopyFrom;

/**
 * This type serves as an example of CopyFrom mappings
 * Created by kfgodel on 31/08/14.
 */
public class CopyFromExample {

    @CopyFrom
    private String sameName;

    @CopyFrom("otherProperty")
    private String singleProperty;

    @CopyFrom("otherObject.otherProperty")
    private String propertySequence;

    public String getSameName() {
        return sameName;
    }

    public void setSameName(String sameName) {
        this.sameName = sameName;
    }

    public String getSingleProperty() {
        return singleProperty;
    }

    public void setSingleProperty(String singleProperty) {
        this.singleProperty = singleProperty;
    }

    public String getPropertySequence() {
        return propertySequence;
    }

    public void setPropertySequence(String propertySequence) {
        this.propertySequence = propertySequence;
    }
}
