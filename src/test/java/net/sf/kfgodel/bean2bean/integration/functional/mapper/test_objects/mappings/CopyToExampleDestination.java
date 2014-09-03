package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.mappings;

/**
 * This type is for testing CopyTo annotation
 * Created by kfgodel on 03/09/14.
 */
public class CopyToExampleDestination {

    private String sameName;

    private String otherProperty;

    private CopyToExampleDestination otherObject;

    public String getSameName() {
        return sameName;
    }

    public void setSameName(String sameName) {
        this.sameName = sameName;
    }

    public String getOtherProperty() {
        return otherProperty;
    }

    public void setOtherProperty(String otherProperty) {
        this.otherProperty = otherProperty;
    }

    public CopyToExampleDestination getOtherObject() {
        return otherObject;
    }

    public void setOtherObject(CopyToExampleDestination otherObject) {
        this.otherObject = otherObject;
    }
}
