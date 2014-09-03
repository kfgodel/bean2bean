package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.mappings;

import net.sf.kfgodel.bean2bean.api.annotations.CopyFrom;
import net.sf.kfgodel.bean2bean.api.annotations.CopyTo;

/**
 * This type serves as example of CopyTo annotation
 * Created by kfgodel on 03/09/14.
 */
public class CopyToExample {
    @CopyTo
    private String sameName;

    @CopyTo("otherProperty")
    private String singleProperty;

    @CopyTo("otherObject.otherProperty")
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

    public static CopyToExample create() {
        CopyToExample testObject = new CopyToExample();
        testObject.sameName = "Pepe";
        testObject.singleProperty = "Johnson";
        testObject.propertySequence = "Liberty";
        return testObject;
    }
}
