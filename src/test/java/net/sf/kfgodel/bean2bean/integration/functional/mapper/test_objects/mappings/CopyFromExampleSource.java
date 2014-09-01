package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects.mappings;

/**
 * This type serves for testing purposes
 * Created by kfgodel on 31/08/14.
 */
public class CopyFromExampleSource {

    private String sameName;

    private String otherProperty;

    private CopyFromExampleSource otherObject;

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

    public CopyFromExampleSource getOtherObject() {
        return otherObject;
    }

    public void setOtherObject(CopyFromExampleSource otherObject) {
        this.otherObject = otherObject;
    }

    public static CopyFromExampleSource create() {
        CopyFromExampleSource testObject = new CopyFromExampleSource();
        testObject.sameName = "Pepe";
        testObject.otherProperty = "Johnson";
        testObject.otherObject = CopyFromExampleSource.create();
        testObject.otherObject.otherProperty = "Liberty";
        return testObject;
    }

}
