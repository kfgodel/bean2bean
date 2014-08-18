package net.sf.kfgodel.bean2bean.integration.functional.mapper.test_objects;

/**
 * This type serves as a test object
 * Created by kfgodel on 16/08/14.
 */
public class UnannotatedTestSource {

    private String sourceProperty;

    public String getSourceProperty() {
        return sourceProperty;
    }

    public void setSourceProperty(String sourceProperty) {
        this.sourceProperty = sourceProperty;
    }

    public static UnannotatedTestSource create(String sourceProperty) {
        UnannotatedTestSource source = new UnannotatedTestSource();
        source.sourceProperty =sourceProperty;
        return source;
    }

}
