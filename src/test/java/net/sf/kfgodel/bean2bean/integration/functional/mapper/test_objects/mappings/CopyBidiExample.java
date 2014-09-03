package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.mappings;

import net.sf.kfgodel.bean2bean.api.annotations.CopyBidi;
import net.sf.kfgodel.bean2bean.api.annotations.CopyTo;

/**
 * This type serves as example of CopyTo annotation
 * Created by kfgodel on 03/09/14.
 */
public class CopyBidiExample {
    @CopyBidi
    private String sameName;

    @CopyBidi("otherProperty")
    private String singleProperty;

    @CopyBidi("otherObject.otherProperty")
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

    public static CopyBidiExample create() {
        CopyBidiExample testObject = new CopyBidiExample();
        testObject.sameName = "same";
        testObject.singleProperty = "single";
        testObject.propertySequence = "sequence";
        return testObject;
    }
}
